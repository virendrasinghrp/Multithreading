import java.util.HashSet;
import java.util.Set;

class Main {
    public static void main(String[] args) throws InterruptedException {
        Set<Thread> allThreads = new HashSet<>();
        TokenBucketFilter tokenBucketFilter = TokenBucketFilterFactory.makeTokenBucketFilter(3);

        Thread.sleep(5000);

        for(int i=0 ; i< 10 ; i++) {
            Thread thread = new Thread(() -> {
                try {
                    tokenBucketFilter.getToken();
                } catch (InterruptedException e) {
                    System.out.println("We got a problem...");
                }
            });
            thread.setName("Thread_" + (i+1));
            allThreads.add(thread);
        }

        for (Thread t: allThreads) { t.start();}

        for (Thread t: allThreads) { t.join();}
    }
}

interface TokenBucketFilter{
    void getToken() throws InterruptedException;
}

class TokenBucketFilterFactory {
    public static MultiThreadTokenBucketFilter makeTokenBucketFilter(int maxTokens) {
        MultiThreadTokenBucketFilter tokenBucketFilter = new MultiThreadTokenBucketFilter(maxTokens);
        Thread daemonThread = new Thread(() -> {
            tokenBucketFilter.daemonThread();
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
        return tokenBucketFilter;

    }

    static class MultiThreadTokenBucketFilter implements TokenBucketFilter {
        private final int MAX_TOKENS;
        private long possibleTokens = 0;
        private final int ONE_SECOND = 1000;

        private MultiThreadTokenBucketFilter(int maxTokens) {
            this.MAX_TOKENS = maxTokens;
        }

        private void daemonThread() {
            while(true) {
                synchronized (this) {
                    if(possibleTokens < MAX_TOKENS) {
                        possibleTokens++;
                    }
                    this.notify();
                }
                try{
                    Thread.sleep(ONE_SECOND);
                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred......");
                }
            }
        }

        @Override
        public void getToken() throws InterruptedException {
            System.out.println("Inside getToken(), possibleTokens: " + possibleTokens);
            synchronized (this) {
                while(possibleTokens == 0) {
                    this.wait();
                }
                possibleTokens--;
            }

            System.out.println("Granting " + Thread.currentThread().getName()
                    + " token at: " + System.currentTimeMillis());
        }
    }
}