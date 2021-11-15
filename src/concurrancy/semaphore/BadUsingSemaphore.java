package concurrancy.semaphore;

import concurrancy.SemaphoreExample;

import java.util.concurrent.Semaphore;

public class BadUsingSemaphore {
    public static void main(String[] args) throws InterruptedException {
        Example.example();
    }
}
class Example{
    public static void example() throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);
        Thread badThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {

                    }
                    throw new RuntimeException("Exception happened");
                }

            }
        });
        badThread.start();
        Thread.sleep(1000);

        Thread goodThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Good Thread waits....");
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {

                }
            }
        });
        goodThread.start();
        badThread.join();
        goodThread.join();

        System.out.println("Exiting Program");
    }
}
