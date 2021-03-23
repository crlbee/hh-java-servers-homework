import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private AtomicInteger counter;

    public Counter() {
        counter = new AtomicInteger();
    }

    public int get() {
        return counter.get();
    }

    public void increment(){
        counter.incrementAndGet();
    }

    public void decrementByValue(int value){
        counter.addAndGet(-value);
    }

    public void reset(){
        counter.set(0);
    }
}
