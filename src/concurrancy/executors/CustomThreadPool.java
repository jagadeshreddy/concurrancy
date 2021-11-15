package concurrancy.executors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

@SuppressWarnings("unused")
public class CustomThreadPool {
    private final int poolSize;
    private final WorkerThread[] workers;
    private final LinkedBlockingQueue<Runnable> queue;

    public CustomThreadPool(int poolSize) {
        this.poolSize = poolSize;
        workers = new WorkerThread[poolSize];
        queue = new LinkedBlockingQueue<Runnable>();
        for(int i = 0; i < poolSize; i++){
            workers[i] = new WorkerThread();
            workers[i].start();
        }
    }

    public void execute(Runnable task){
        synchronized (queue){
            queue.add(task);
            queue.notify();
        }
    }
    class WorkerThread extends Thread{
        @Override
        public void run() {
            Runnable task;
            while (true){
                synchronized(queue){
                    while (queue.isEmpty()){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting");
                        }
                    }
                    task = (Runnable) queue.poll();
                }
                try {
                    task.run();
                } catch (Exception e) {
                    System.out.println("Thread pool is interrupted due to an issue : ");
                }
            }
        }
    }
    public void shutdown(){
        System.out.println("Shutting down thread pool");
        for(int i = 0; i < poolSize; i++){
            workers[i] = null;
        }
    }
}


