package executor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import task.AnalysisTask;
import task.MergeTask;
import task.WriteTask;

import static task.Task.runnableThreadLocal;

public class TaskManager {

    private final int matchLength;
    private final boolean captureExcelBool;
    private final String outDir;

    private List<File> allResult = new ArrayList<>();

    public TaskManager(int matchLength, boolean captureExcelBool, String outDir) {
        this.matchLength = matchLength;
        this.captureExcelBool = captureExcelBool;
        this.outDir = outDir;
    }

    public void taskSubmitted(Runnable runnable, ThreadPoolExecutor executor) {
        System.out.println("submitted for " + runnable.toString());
    }

    public void taskRejected(Runnable runnable, ThreadPoolExecutor executor) {
        System.out.println("rejected  for " + runnable.toString());
        try {
            TimeUnit.SECONDS.sleep(3);
            executor.submit(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void taskStarted(Thread t, Runnable r) {
//        System.out.println(t.getName() + " processing for " + r.toString());
    }

    public void taskCompleted(Runnable runnable, Throwable t, ThreadPoolExecutor executor) {
        if (t != null) {
            System.out.println(t);
            return;
        }

        Runnable myrunnable = runnableThreadLocal.get();
        String name = Thread.currentThread().getName();
        System.out.println("processed for " + myrunnable.toString() + " with thread " + name);
        runnableThreadLocal.remove();

        if (myrunnable instanceof AnalysisTask) {
            AnalysisTask task = (AnalysisTask) myrunnable;
            WriteTask writeTask = new WriteTask(task.getName(), task.getResult(), matchLength, captureExcelBool);
            executor.submit(writeTask);
            return;
        } else if (myrunnable instanceof WriteTask) {
            WriteTask task = (WriteTask) myrunnable;
            allResult.addAll(task.getResult());
        } else if (myrunnable instanceof MergeTask) {
            MergeTask task = (MergeTask) myrunnable;
            File result = task.getResult();
            System.out.println("merged excel: " + result);
            return;
        }

        boolean empty = executor.getQueue().isEmpty();
        int activeCount = executor.getActiveCount();
        if (empty && (activeCount - 1) == 0) {
            MergeTask mergeTask = new MergeTask("all excel", allResult, outDir);
            executor.submit(mergeTask);
            //last task
            executor.shutdown();
            //不能在这里调用，因为这个线程是 执行 task 的线程，他本身不能等待管理他的 ThreadPoolExecutor 结束。
            //executor.awaitTermination(4,TimeUnit.SECONDS);
        }
    }
}
