import java.util.concurrent.Semaphore;

class Scratch {
    static Semaphore semaphore = new Semaphore(3);
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("Thread 1 acquired semaphore.");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            semaphore.release();
            System.out.println("Thread 1 release semaphore.");
        });

        Thread t2 = new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("Thread 2 acquired semaphore.");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            semaphore.release();
            System.out.println("Thread 2 release semaphore.");
        });

        Thread t3 = new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("Thread 3 acquired semaphore.");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            semaphore.release();
            System.out.println("Thread 3 release semaphore.");
        });

        Thread t4 = new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("Thread 4 acquired semaphore.");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            semaphore.release();
            System.out.println("Thread 4 release semaphore.");
        });

        Thread t5 = new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("Thread 5 acquired semaphore.");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            semaphore.release();
            System.out.println("Thread 5 release semaphore.");
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

    }
}