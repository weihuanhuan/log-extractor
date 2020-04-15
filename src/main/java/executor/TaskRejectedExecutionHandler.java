package executor;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (executor instanceof TaskThreadPool) {
            TaskThreadPool threadPool = (TaskThreadPool) executor;
            TaskManager taskManager = threadPool.getTaskManager();
            if (taskManager != null) {
                taskManager.taskRejected(r, executor);
            }
        } else {
            throw new RejectedExecutionException();
        }
    }
}
