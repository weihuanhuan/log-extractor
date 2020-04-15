package executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskThreadFactory implements ThreadFactory {

    private String prefix;

    private AtomicInteger counter = new AtomicInteger(0);

    public TaskThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, getThreadName());
        thread.setDaemon(false);
        return thread;
    }

    private String getThreadName() {
        return prefix + "-" + counter.incrementAndGet();
    }
}
