import java.util.concurrent.Semaphore;

class Scratch {
    public static void main( String args[] ) throws InterruptedException {
        UnisexBathroom.runTest();
    }
}
class UnisexBathroom {
    static String WOMEN = "women";
    static String MEN = "men";
    static String NONE = "none";
    String inUseBy = NONE;
    int empsInBathroom = 0;
    Semaphore maxEmps = new Semaphore(3);
    void useBathroom(String name) throws InterruptedException {
        System.out.println("\n" + name + " using bathroom. Current employees in bathroom = " + empsInBathroom);
                Thread.sleep(3000);
        System.out.println("\n" + name + " done using bathroom " + System.currentTimeMillis() );
    }
    void maleUseBathroom(String name) throws InterruptedException {
        synchronized (this) {
            while (inUseBy.equals(WOMEN)) {
                this.wait();
            }
            maxEmps.acquire();
            empsInBathroom++;
            inUseBy = MEN;
        }
        useBathroom(name);
        maxEmps.release();
        synchronized (this) {
            empsInBathroom--;
            if (empsInBathroom == 0) inUseBy = NONE;
            this.notifyAll();
        }
    }
    void femaleUseBathroom(String name) throws InterruptedException {
        synchronized (this) {
            while (inUseBy.equals(MEN)) {
                this.wait();
            }
            maxEmps.acquire();
            empsInBathroom++;
            inUseBy = WOMEN;
        }
        useBathroom(name);
        maxEmps.release();
        synchronized (this) {
            empsInBathroom--;
            if (empsInBathroom == 0) inUseBy = NONE;
            this.notifyAll();
        }
    }

    public static void runTest() throws InterruptedException {
        final UnisexBathroom unisexBathroom = new UnisexBathroom();
        Thread female1 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.femaleUseBathroom("Lisa");
                } catch (InterruptedException ie) {
                }
            }
        });
        Thread male1 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("John");
                } catch (InterruptedException ie) {
                }
            }
        });
        Thread male2 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("Bob");
                } catch (InterruptedException ie) {
                }
            }
        });
        Thread male3 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("Anil");
                } catch (InterruptedException ie) {
                }
            }
        });
        Thread male4 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("Wentao");
                } catch (InterruptedException ie) {
                }
            }
        });
        female1.start();
        male1.start();
        male2.start();
        male3.start();
        male4.start();

        female1.join();
        male1.join();
        male2.join();
        male3.join();
        male4.join();
    }
}