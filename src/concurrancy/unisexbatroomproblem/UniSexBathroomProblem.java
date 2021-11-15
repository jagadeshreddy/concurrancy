package concurrancy.unisexbatroomproblem;

import java.util.concurrent.Semaphore;

public class UniSexBathroomProblem {
    public static void main(String[] args) throws InterruptedException {
        UniSexBathroomProblemDemo.runTests();
    }
}

class UniSexBathroomProblemDemo {
    Gender inUseBy = Gender.NONE;
    int empsInBathroom = 0;
    Semaphore maxEmps = new Semaphore(3);

    void useBathroom(String name) throws InterruptedException {
        System.out.println("\n" + name + " Using Bathroom. Current Employees in Bathroom : " + empsInBathroom);
        Thread.sleep(3000);
        System.out.println("\n" + name + " Done Using bathroom");
    }

    void maleUseBathroom(String name) throws InterruptedException {
        synchronized (this) {
            while (inUseBy.equals(Gender.FEMALE)) {
                this.wait();
            }
            maxEmps.acquire();
            empsInBathroom++;
            inUseBy = Gender.MALE;
        }
        useBathroom(name);
        maxEmps.release();
        synchronized (this) {
            empsInBathroom--;
            if (empsInBathroom == 0) {
                inUseBy = Gender.NONE;
            }
            this.notifyAll();
        }
    }

    void femaleUseBathroom(String name) throws InterruptedException {
        synchronized (this) {
            while (inUseBy.equals(Gender.MALE)) {
                wait();
            }
            maxEmps.acquire();

            inUseBy = Gender.FEMALE;
        }
        useBathroom(name);
        empsInBathroom++;
        maxEmps.release();
        synchronized (this) {
            empsInBathroom--;
            if (empsInBathroom == 0) {
                inUseBy = Gender.NONE;
            }
            this.notifyAll();
        }
    }

    public static void runTests() throws InterruptedException {
        final UniSexBathroomProblemDemo uniSexBathroomProblemDemo = new UniSexBathroomProblemDemo();


        Thread female1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    uniSexBathroomProblemDemo.femaleUseBathroom("janaki");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread female2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    uniSexBathroomProblemDemo.femaleUseBathroom("pavani");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread female3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    uniSexBathroomProblemDemo.femaleUseBathroom("roja");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread male1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    uniSexBathroomProblemDemo.maleUseBathroom("jy");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread female4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    uniSexBathroomProblemDemo.femaleUseBathroom("srija");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        female1.start();
        female2.start();
        female3.start();
        male1.start();
        female4.start();
        female1.join();
        female2.join();
        female3.join();
        female4.join();


    }


}

enum Gender {
    MALE, FEMALE, NONE
}
