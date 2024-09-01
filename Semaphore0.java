import java.util.concurrent.Semaphore;

class Scratch {
    /**
     *     Difference between Semaphore initialized with 1 and 0
     *
     *     public static Semaphore semOne = new Semaphore(1);
     *
     *     public static Semaphore semZero = new Semaphore(0);
     *
     *     For semZero all acquire() calls will block and tryAcquire() calls
     *     will return false, until you do a release()
     *
     *     For semOne the first acquire() calls will succeed and the rest
     *     will block until the first one releases.
     */


    public static void main(String[] args) {
        /**
         * Available permits: 0
         * Available permits: 1
         * Available permits: 2
         * Available permits: 3
         * */
        Semaphore semaphore = new Semaphore(0);
        System.out.println("Available permits: " + semaphore.availablePermits());
        semaphore.release();
        System.out.println("Available permits: " + semaphore.availablePermits());
        semaphore.release();
        System.out.println("Available permits: " + semaphore.availablePermits());
        semaphore.release();
        System.out.println("Available permits: " + semaphore.availablePermits());


        /**
         * Available permits: -2
         * Available permits: -1
         * Available permits: 0
         * Available permits: 1
         * */
        Semaphore negativeSemaphore = new Semaphore(-2);
        System.out.println("Available permits: " + negativeSemaphore.availablePermits());
        negativeSemaphore.release();
        System.out.println("Available permits: " + negativeSemaphore.availablePermits());
        negativeSemaphore.release();
        System.out.println("Available permits: " + negativeSemaphore.availablePermits());
        negativeSemaphore.release();
        System.out.println("Available permits: " + negativeSemaphore.availablePermits());
    }
}