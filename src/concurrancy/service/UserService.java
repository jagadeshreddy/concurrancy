package concurrancy.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserService {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);
  //  private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // having this may cause in-consistance.

    public static void main(String[] args) throws InterruptedException {

      /*  new Thread(() -> {
            String birthDate = new UserService().birthDate(100);
            System.out.println(birthDate);
        }).start();

        new Thread(() -> {
            String birthDate = new UserService().birthDate(3000);
            System.out.println(birthDate);
        }).start();*/

        /*for(int i = 0; i < 10; i++){
            int id = 1;
            new Thread(()->{
                String birthDate = new UserService().birthDate(id);
                System.out.println(birthDate);
            }).start();
        }
        Thread.sleep(1000);*/
        for (int i = 0; i < 10; i++) {
            int id = 1;
            threadPool.submit(() -> {
                String birthDate = new UserService().birthDate(id);
                System.out.println(birthDate);
            });
        }
        Thread.sleep(1000);

    }

    private String birthDate(int userId) {
        Date birthDate = birthDateFormDb(userId);
        SimpleDateFormat df = ThreadSafeFormatter.df.get();
        return df.format(birthDate);
    }

    private Date birthDateFormDb(int userId) {
        Date date = new Date();
        return date;
    }
}

class ThreadSafeFormatter{
    public static ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }

        @Override
        public SimpleDateFormat get() {
            return super.get();
        }
    };
    public static ThreadLocal<SimpleDateFormat> df = ThreadLocal.withInitial(()-> new SimpleDateFormat("yyy-MM-dd"));

}
