package concurrancy.semaphore;

import java.util.concurrent.Semaphore;

public class GoodUsingSemaphore {
    public static void main(String[] args) throws InterruptedException {
        GoodExample.example();
    }
}
class GoodExample{
    public static void example() throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);

        Thread badThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        semaphore.acquire();
                        try {
                            throw new RuntimeException("");
                        } catch (RuntimeException e) {
                            return;
                        } finally {
                            System.out.println("BadThread releaseing semaphore");
                            semaphore.release();
                        }
                    } catch (InterruptedException e) {

                    }
                }
            }
        });

        badThread.start();
        Thread.sleep(1000);

        Thread goodThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Good Thread waits patiently...");
                try{
                    semaphore.acquire();
                    System.out.println("Good Thread acquired");
                } catch (InterruptedException e) {

                }
            }
        });
        goodThread.start();
        badThread.join();
        badThread.start();
        badThread.join();
        System.out.println("Exiting Program");
    }
}
