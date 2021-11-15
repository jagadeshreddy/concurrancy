package concurrancy;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) throws InterruptedException {
        SemaphoreSignalMissing.example();
    }
}

class SemaphoreSignalMissing {
    public static void example() throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);
        Thread signaller = new Thread(new Runnable() {
            @Override
            public void run() {
                semaphore.release();
                System.out.println("Sent Signal");
            }
        });

        Thread waiter = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    semaphore.acquire();
                    System.out.println("Received Signal");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        signaller.start();
        signaller.join();
        Thread.sleep(3000);
        waiter.start();
        waiter.join();

    }
}
