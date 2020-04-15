package executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskThreadPool extends ThreadPoolExecutor {

    TaskManager taskManager;

    public TaskThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    @Override
    public Future<?> submit(Runnable task) {
        Future<?> submit = super.submit(task);
        taskManager.taskSubmitted(task, this);
        return submit;
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        taskManager.taskStarted(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        taskManager.taskCompleted(r, t, this);
    }

    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
}
