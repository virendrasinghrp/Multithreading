import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class Scratch {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<Integer> callableWithoutExecutorService = new CallableWithoutExecutorService();
        FutureTask<Integer> futureTask = new FutureTask<>(callableWithoutExecutorService);
        Thread thread = new Thread(futureTask);
        thread.start();

        thread.join();
        Integer response = futureTask.get();
        System.out.println("Output value is: " + response);
    }
}

class CallableWithoutExecutorService implements Callable {
    @Override
    public Integer call() throws Exception {
        Integer num = (int) (Math.random()*10);
        System.out.println("Thread going to sleep now...");
        Thread.sleep(5000);
        System.out.println("Thread waking up.");
        return num;
    }
}