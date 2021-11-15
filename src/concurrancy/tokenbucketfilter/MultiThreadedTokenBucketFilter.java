package concurrancy.tokenbucketfilter;

import java.util.HashSet;
import java.util.Set;

public class MultiThreadedTokenBucketFilter {
    public static void main(String[] args) throws InterruptedException {
        Set<Thread> allThreads  = new HashSet<>();
        final Demo demo = new Demo(1);
        for(int i = 0; i < 10; i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        demo.getToken();
                    } catch (InterruptedException e) {

                    }
                }
            });
            thread.setName("Thread : "+(i + 1));
            allThreads.add(thread);
        }
        for(Thread thread : allThreads){
            thread.start();
        }
        for (Thread thread : allThreads){
            thread.join();
        }
    }
}

class Demo {
    private long possibleTokens = 0;
    private final int MAX_TOKENS;
    private final int ONE_SECOND = 1000;

    Demo(int max_tokens) {
        MAX_TOKENS = max_tokens;

    }

    private void demonThread() {
        while (true){
            synchronized (this) {
                if (this.MAX_TOKENS > possibleTokens) {
                    possibleTokens++;
                }
                this.notify();
            }
            try {
                Thread.sleep(ONE_SECOND);
            } catch (InterruptedException e) {

            }
        }
    }

    void getToken() throws InterruptedException{
        synchronized (this){
            while (possibleTokens == 0){
                this.wait();
            }
            possibleTokens--;
        }
        System.out.println("Granting "+Thread.currentThread().getName());
    }


}

