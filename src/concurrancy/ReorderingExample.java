package concurrancy;

public class ReorderingExample {
    public static void main(String[] args) throws InterruptedException {
        new DemoOrder().reorderTest();
    }
}

class DemoOrder {
    private int ping = 0, pong = 0, foo = 0, bar = 0;
    public void reorderTest() throws InterruptedException{
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                foo = 1;
                ping = bar;
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                bar = 1;
                pong = foo;
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(ping+" "+pong);
    }
}