class RaceCondition {
    static int counter = 0;
    public static void main(String[] args) {

        Counter counter = new Counter();

        Thread thread1 = new Thread(() -> {
            for (int i = 0 ; i < 1000 ; i++){
                counter.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0 ; i < 1000 ; i++){
                counter.increment();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Final counter value is: " + counter.getCount());

    }
}

class Counter{
    int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
