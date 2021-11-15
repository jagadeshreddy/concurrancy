package concurrancy;

public class Barrier {
    public static void main(String[] args) throws InterruptedException {
        BarrierDemo.runTest();
    }
}

class BarrierDemo {
    int count = 0, released  = 0;
    int totalThreads;

    BarrierDemo(int totalThreads) {
        this.totalThreads = totalThreads;
    }

    public synchronized void await() throws InterruptedException {
        count++;

        System.out.println(Thread.currentThread().getName() + " count : " + count);

        if (count == totalThreads) {
            notifyAll();
            released = totalThreads;
        } else {
            while (count < totalThreads) {
                wait();
            }
        }
        released--;
        if (released == 0) count = 0;
    }

    public static void runTest() throws InterruptedException {
        final BarrierDemo barrier = new BarrierDemo(3);

        Thread p1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Thread 1");
                    barrier.await();
                    System.out.println("Thread 1");
                    barrier.await();
                    System.out.println("Thread 1");
                    barrier.await();
                } catch (Exception e) {

                }
            }
        });

        Thread p2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    System.out.println("Thread 2");
                    barrier.await();
                    Thread.sleep(500);
                    System.out.println("Thread 2");
                    barrier.await();
                    Thread.sleep(500);
                    System.out.println("Thread 2");
                    barrier.await();
                    Thread.sleep(500);
                    barrier.await();
                } catch (Exception e) {

                }
            }
        });

        Thread p3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    System.out.println("Thread 3");
                    barrier.await();
                    Thread.sleep(500);
                    System.out.println("Thread 3");
                    barrier.await();
                    Thread.sleep(500);
                    System.out.println("Thread 3");
                    barrier.await();
                    Thread.sleep(500);
                    barrier.await();
                } catch (Exception e) {

                }
            }
        });

        p1.setName("P1");
        p2.setName("P2");
        p3.setName("P3");

        p1.start();
        p2.start();
        p3.start();

        p1.join();
        p2.join();
        p3.join();


    }
}
