/**
 * This is an actual interview question asked at Netflix.
 * Imagine we have an Executor class that performs some useful task
 * asynchronously via the method asynchronousExecution() . In addition the
 * method accepts a callback object which implements the Callback
 * interface. the object’s done() gets invoked when the asynchronous
 * execution is done. The definition for the involved classes is below:
 *
 * public class Executor {}
 * public interface Callback {}
 *
 * The main thread exits before the asynchronous execution is
 * completed.
 * Your task is to make the execution synchronous without changing the
 * original classes (imagine, you are given the binaries and not the source
 * code) so that main thread waits till asynchronous execution is complete.
 * In other words, the highlighted line {System.out.println("main thread exiting...");}
 * only executes once the asynchronous task is complete
 * */

class Demonstration {
    public static void main( String args[] ) throws Exception{
//        Executor executor = new Executor();
        SynchronousExecutor executor = new SynchronousExecutor();

        executor.asynchronousExecution(() -> {
            System.out.println("I am done");
        });
        System.out.println("main thread exiting...");
    }
}
interface Callback {
    public void done();
}

class SynchronousExecutor extends Executor {
    @Override
    public void asynchronousExecution(Callback callback) throws Exception {
        Object signal = new Object();
        final boolean[] isDone = new boolean[1];
        Callback cb = new Callback() {
            @Override
            public void done() {
                callback.done();
                synchronized (signal) {
                    signal.notify();
                    isDone[0] = true;
                }
            }
        };
        // Call the asynchronous executor
        super.asynchronousExecution(cb);
        synchronized (signal) {
            while (!isDone[0]) {
                signal.wait();
            }
        }
    }
}

class Executor {
    public void asynchronousExecution(Callback callback) throws Exception {
        Thread t = new Thread(() -> {
            // Do some useful work
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
            }
            callback.done();
        });
        t.start();
    }
}
/**
 * Follow-up Question: Is the method asynchronousExecution() thread-safe?
 *
 * The way we have constructed the logic, all the variables in the overridden
 * method will be created on the thread-stack for each thread therefore the
 * method is threadsafe and multiple threads can execute it in parallel
 * */