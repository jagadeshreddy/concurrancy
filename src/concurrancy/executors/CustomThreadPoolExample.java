package concurrancy.executors;

import java.util.Date;

public class CustomThreadPoolExample {
    static class Task implements Runnable{
        private String name;
        public Task(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {
         System.out.println("Executing : "+name+", current seconds : "+new Date().getSeconds());
        }
    }
    public static void main(String[] args) {
        CustomThreadPool customThreadPool = new CustomThreadPool(2);
        for(int i = 0; i <= 5; i++){
            Task task = new Task("Task "+i);
            System.out.println("Created : "+task.getName());
            customThreadPool.execute(task);
            customThreadPool.shutdown();
        }
    }
}
