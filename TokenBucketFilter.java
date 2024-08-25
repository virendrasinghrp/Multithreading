import java.util.HashSet;
import java.util.Set;

// This is an actual interview question asked at Uber and Oracle.
// Imagine you have a bucket that gets filled with tokens at the rate of 1 token per second. The bucket can hold a maximum of N tokens.
// Implement a thread-safe class that lets threads get a token when one is available. If no token is available, then the token-requesting threads should block.
// The class should expose an API called `getToken()` that various threads can call to get a token
class Scratch {
    public static void main(String[] args) throws InterruptedException {
        TokenBucketFilter.runTestMaxToken(3);
    }
}

class TokenBucketFilter{
    private int MAX_TOKENS;
    private long lastRequestTime = System.currentTimeMillis();
    long possibleTokens = 0;

    public TokenBucketFilter(int MAX_TOKENS) {
        this.MAX_TOKENS = MAX_TOKENS;
    }

    synchronized void getToken() throws InterruptedException {
        possibleTokens += (System.currentTimeMillis() - lastRequestTime)/1000;

        if(possibleTokens > MAX_TOKENS)
            possibleTokens = MAX_TOKENS;

        if (possibleTokens == 0)
            Thread.sleep(1000);
        else
            possibleTokens--;

        lastRequestTime = System.currentTimeMillis();

        System.out.println("Granting " + Thread.currentThread().getName()
                + " token at: " + System.currentTimeMillis());
    }

    public static void runTestMaxToken(int maxBucketSize) throws InterruptedException {
        Set<Thread> allThreads = new HashSet<>();
        final TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(maxBucketSize);

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
