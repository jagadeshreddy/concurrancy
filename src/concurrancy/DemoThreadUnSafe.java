package concurrancy;

import java.util.Random;

public class DemoThreadUnSafe {
    static Random random = new Random(System.currentTimeMillis());
    public static void main(String[] args) throws InterruptedException {

        ThreadUnSafeCounter threadUnSafeCounter = new ThreadUnSafeCounter();
        Thread increment = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10; i++){
                    threadUnSafeCounter.increment();
                    System.out.println("Increment Thread : ");
                    threadUnSafeCounter.printCounterVariable();
                    DemoThreadUnSafe.sleepRandom();
                }
            }
        });

        Thread decrement = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10; i++){
                    threadUnSafeCounter.decrement();
                    System.out.println("Decrement Thread : ");
                    threadUnSafeCounter.printCounterVariable();
                    DemoThreadUnSafe.sleepRandom();
                }
            }
        });

        increment.start();
        decrement.start();

        increment.join();
        decrement.join();

        threadUnSafeCounter.printCounterVariable();

    }
    public static void sleepRandom(){
        try{
            Thread.sleep(random.nextInt(2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadUnSafeCounter{
    int counter;
    public void increment(){
        counter++;
    }
    public void decrement(){
        counter--;
    }
    public void printCounterVariable(){
        System.out.println("Counter value : "+counter);
    }
}
