package concurrancy.readwritelock;

public class ReadWriteLock {
    public static void main(String[] args) throws InterruptedException {
        final ReadWriteLockDemo readWriteLock = new ReadWriteLockDemo();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Attempting to acquire write lock in t1 : "+System.currentTimeMillis());
                    readWriteLock.acquireWriteLock();
                    System.out.println("Write lock acquired t1 ");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("Attempting to acquire write lock in t2 : "+System.currentTimeMillis());
                    readWriteLock.releaseReadLock();
                    System.out.println("Write lock acquired t2 ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread tReader1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    readWriteLock.acquireReadLock();
                    System.out.println("Read lock acquired : ");
                } catch (InterruptedException e) {

                }
            }
        });

        Thread tReader2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Read lock about to release :  ");
                readWriteLock.releaseReadLock();
                System.out.println("Read lock released : ");
            }
        });

        tReader1.start();
        t1.start();
        Thread.sleep(3000);
        tReader2.start();
        Thread.sleep(1000);
        t2.start();
        tReader1.join();
        tReader2.join();
        t2.join();
        t1.join();
    }

}
class ReadWriteLockDemo{
    boolean isWriteLocked = false;
    int reader = 0;

    public synchronized void acquireReadLock() throws InterruptedException {
        while (isWriteLocked) {
            wait();
        }
        reader++;
    }

    public synchronized void releaseReadLock() {
        reader--;
        notify();
    }

    public synchronized void acquireWriteLock() throws InterruptedException {
        while (reader != 0 || isWriteLocked) {
            wait();
        }
        isWriteLocked = true;
    }

    public synchronized void releaseWriteLock() {
        isWriteLocked = false;
        notify();
    }
}
