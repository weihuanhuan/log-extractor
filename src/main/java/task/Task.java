package task;

public abstract class Task<T> implements Runnable {

    protected String fileCanonicalPath;

    public static ThreadLocal<Runnable> runnableThreadLocal = new ThreadLocal<>();

    public Task(String fileCanonicalPath) {
        this.fileCanonicalPath = fileCanonicalPath;
    }

    abstract T getResult();

    public String getName() {
        return fileCanonicalPath;
    }

    @Override
    public void run() {
        Runnable runnable = runnableThreadLocal.get();
        if (runnable == null) {
            runnableThreadLocal.set(this);
        }
        doRun();
    }

    protected abstract void doRun();

    @Override
    public String toString() {
        return String.format("%12s %s", this.getClass().getSimpleName(), fileCanonicalPath);
    }
}
