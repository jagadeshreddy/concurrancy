package concurrancy;

public class IncorrectSynchronization {
    public static void main(String[] args) throws InterruptedException {
        Demo.runExample();
    }
}
class Demo{
    Boolean flag = new Boolean(true);
    public void example() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (flag) {
                    try {
                        while (flag) {
                            System.out.println("First Thread about to sleep");
                            Thread.sleep(5000);
                            System.out.println("Woke Up and about to wait()");
                            flag.wait();
                        }
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                /*flag = false;*/
                System.out.println("Doing Nothing in thread 2");
                System.out.println("Boolean assignment done. ");
            }
        });
        t1.start();
        Thread.sleep(1000);
        t2.start();
        t1.join();
        t2.join();

    }
    public static void runExample() throws InterruptedException{
       Demo demo = new Demo();
       demo.example();
    }

}
