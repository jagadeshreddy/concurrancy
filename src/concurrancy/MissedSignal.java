package concurrancy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MissedSignal {
    public static void main(String[] args) throws InterruptedException {
        DemoMissedSignal.example();
    }
}

class DemoMissedSignal {
    public static void example() throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        Thread signaller = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();

                System.out.println("Sent Signal");

                condition.signal();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        });
        Thread waiter = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    condition.await();
                    System.out.println("Received signal");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        signaller.start();
        signaller.join();
        Thread.sleep(1000);
        waiter.start();
        waiter.join();
        System.out.println("System Exiting...");
    }


}
