package interview.java8;

/*
Write a single Java 8 Stream pipeline to produce the following result:

    For each user,

    group transactions by category,

    sum the total amount per category,

    but ONLY include transactions from the latest day in the dataset.
 */

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Transaction {
    String userId;
    String category;
    double amount;
    long timestamp; // epoch millis

    public Transaction(String userId, String category, double amount, long timestamp) {
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "userId='" + userId + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
public class Interview {
    public static void main(String[] args) {
        Transaction t1 = new Transaction("USER1", "UPI",   30000, 20230503);
        Transaction t2 = new Transaction("USER1", "DEBIT", 10000, 20230503);
        Transaction t3 = new Transaction("USER2", "DEBIT", 20000, 20230503);
        Transaction t4 = new Transaction("USER1", "UPI",   30000, 20230503);

        List<Transaction> list = Arrays.asList(t1, t2, t3, t4);

        Map<String, Map<String, Double>> result =
                list.stream()
                        .collect(Collectors.groupingBy(
                                Transaction::getUserId,
                                Collectors.groupingBy(
                                        Transaction::getCategory,
                                        Collectors.summingDouble(Transaction::getAmount)
                                )
                        ));

        System.out.println(result);

    }
}
