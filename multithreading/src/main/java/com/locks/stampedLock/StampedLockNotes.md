# StampedLock in Java

`StampedLock` was introduced in Java 8 as an alternative to `ReentrantReadWriteLock`. It provides a mechanism for controlling access to a shared resource using three modes: **Writing**, **Reading**, and **Optimistic Reading**.

## Key Features

1.  **Not Reentrant**: Unlike `ReentrantLock` or `ReentrantReadWriteLock`, `StampedLock` is **not reentrant**. If a thread holds a lock and tries to acquire it again, it will deadlock itself.
2.  **Stamps**: Every lock acquisition method returns a `long` value called a "stamp". This stamp represents the state of the lock and is used to release the lock or convert it.
3.  **Performance**: It is generally faster than `ReentrantReadWriteLock`, especially under high contention, because it uses more efficient internal mechanisms.

## Three Modes of Operation

### 1. Writing (Exclusive Lock)
*   **Method**: `writeLock()`
*   **Behavior**: Similar to a standard exclusive lock. Only one thread can hold the write lock at a time. No other thread can read or write while this lock is held.
*   **Use Case**: When you need to modify the shared state safely.

### 2. Reading (Pessimistic Read Lock)
*   **Method**: `readLock()`
*   **Behavior**: Similar to a standard shared lock. Multiple threads can hold the read lock simultaneously, but no thread can acquire the write lock.
*   **Use Case**: When you need to read the shared state and ensure it doesn't change while you are reading.

### 3. Optimistic Reading
*   **Method**: `tryOptimisticRead()`
*   **Behavior**: This is the most unique feature. It does **not** acquire a lock. Instead, it returns a stamp. You proceed with your read operation assuming no one is writing. After reading, you call `validate(stamp)` to check if a write occurred during your read.
    *   If `validate` returns `true`: Your read was consistent.
    *   If `validate` returns `false`: A write occurred. You must retry, usually by falling back to a pessimistic `readLock()`.
*   **Use Case**: Extremely useful when reads are very frequent and writes are rare. It avoids the overhead of full locking.

---

## Real-World Scenario: High-Frequency GPS Location Tracker

Imagine a system that tracks the GPS coordinates (latitude, longitude) of a moving vehicle.

*   **The Writer (GPS Sensor)**: The GPS sensor updates the coordinates periodically (e.g., every second). This is a **write** operation.
*   **The Readers (Display, Navigation, Logger)**: Multiple components (dashboard display, navigation system, data logger) need to read the current location constantly (e.g., 60 times a second) to render the UI smoothly.

### Why StampedLock?

*   **High Read/Write Ratio**: The location is read hundreds of times for every single update.
*   **Optimistic Read Benefit**: Most of the time, when a component reads the location, no update is happening. Using a full `readLock` every time would cause unnecessary contention and slow down the system.
*   **Optimistic Strategy**:
    1.  The UI tries an **optimistic read**. It grabs the coordinates.
    2.  It checks `validate()`.
    3.  99% of the time, it succeeds and renders the frame immediately with zero locking overhead.
    4.  If an update *did* happen exactly at that moment (validation fails), the UI simply retries, perhaps grabbing a full `readLock` this time to be safe.

### Code Example Concept

```java
class Point {
    private double x, y;
    private final StampedLock sl = new StampedLock();

    // Writer: Updates the position
    void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    // Reader: Calculates distance from origin
    double distanceFromOrigin() {
        // 1. Try Optimistic Read
        long stamp = sl.tryOptimisticRead();
        double currentX = x;
        double currentY = y;

        // 2. Validate
        if (!sl.validate(stamp)) {
            // 3. Fallback to Pessimistic Read if validation failed
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
```
