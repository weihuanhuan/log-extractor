package executor;

import task.Task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TaskExecutor {

    private TaskThreadPool executor;

    public TaskExecutor() {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(1024);
        ThreadFactory threadFactory = getThreadFactory();
        int processors = Runtime.getRuntime().availableProcessors();
        int coreNum = processors - 1;
        if (coreNum < 1) {
            coreNum = 1;
        }
        executor = new TaskThreadPool(coreNum, processors, 3, TimeUnit.SECONDS, blockingQueue, threadFactory);
    }

    public void init(int matchLength, boolean captureExcelBool, String outDir) {
        executor.setTaskManager(new TaskManager(matchLength, captureExcelBool, outDir));
        executor.setRejectedExecutionHandler(new TaskRejectedExecutionHandler());
        executor.allowCoreThreadTimeOut(true);
        executor.prestartAllCoreThreads();
    }

    public void submit(Task task) {
        executor.submit(task);
    }

    public void waitFinish() throws TimeoutException {
        waitFinish(TimeUnit.HOURS.toMillis(3));
    }

    public void waitFinish(long timeout) throws TimeoutException {
        long start = System.currentTimeMillis();
        while (!executor.isTerminated()) {
            try {
                long current = System.currentTimeMillis();
                if (current - start > timeout) {
                    throw new TimeoutException("Parse Timeout!");
                }
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ThreadFactory getThreadFactory() {
        ThreadFactory threadFactory = new TaskThreadFactory("log-analyse");
        return threadFactory;
    }
}
