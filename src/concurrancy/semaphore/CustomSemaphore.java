package concurrancy.semaphore;

public class CustomSemaphore {
    public static void main(String[] args) throws InterruptedException {
        final CountingSemaphore countingSemaphore = new CountingSemaphore(1);


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        countingSemaphore.acquire();
                        System.out.println("Ping : "+i);
                    } catch (InterruptedException e) {
                        System.out.println("Exception Occured while acquiring...");
                    }
                }
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        countingSemaphore.release();
                        System.out.println("Pong : "+i);
                    } catch (InterruptedException e) {
                        System.out.println("Exception Occured while releasing...");
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();


    }
}

class CountingSemaphore {
    int usedPermits = 0;
    int maxCount = 0;

    public CountingSemaphore(int count) {
        this.maxCount = count;
    }

    public synchronized void acquire() throws InterruptedException {
        if (usedPermits == maxCount) {
            wait();
        }
        usedPermits++;
        notify();
    }

    public synchronized void release() throws InterruptedException {
        if (usedPermits == 0) {
            wait();
        }
        usedPermits--;
        notify();
    }
}
