package interceptor;

import entry.LogRecord;

public class CounterInterceptor extends AbstractInterceptor {

    public int counter = 0;

    @Override
    public void invoke(LogRecord logRecord) {
        System.out.println(++counter);

        if (this.next != null) {
            this.next.invoke(logRecord);
        }
    }
}
