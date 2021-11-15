package concurrancy.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void threadAllocations() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executorService.execute(()->{
                System.out.println("i : "+ finalI);
            });
        }
        Thread.sleep(1000);
        executorService.shutdown();

    }

    public static void main(String[] args) throws InterruptedException {
        threadAllocations();
    }
}
