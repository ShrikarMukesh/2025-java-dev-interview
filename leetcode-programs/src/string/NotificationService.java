package string;

abstract class demo
{
    public int a;
    demo()
    {
        a = 10;
    }

    abstract public void set();

    abstract public void get();

}

class NotificationService extends demo
{

    public void set(int a)
    {
        this.a = a;
    }

    @Override
    public void set() {

    }

    final public void get()
    {
        System.out.println("a = " + a);
    }

    public static void main(String[] args)
    {
        NotificationService obj = new NotificationService();
        obj.set(20);
        obj.get();
    }
}

