package concurrancy.service;

public class StoringDataUsingThreadLocale implements Runnable{

    ThreadLocal<Context> userContext = new ThreadLocal<>();

    @Override
    public void run() {
        System.out.println("");
    }
}

