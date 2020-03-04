package test;

import entry.LogRecord;
import interceptor.CounterInterceptor;
import interceptor.ExceptionCheckInterceptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import parser.ExceptionLogParser;
import result.StatisticResult;

/**
 * Created by JasonFitch on 9/12/2019.
 */
public class RandomAccessParserTest {

    public static void main(String[] args) throws IOException {

        ExceptionLogParser logParser = new ExceptionLogParser();

        String logFileName = "./test.log";
        File file = new File(logFileName);
        System.out.println(file.getCanonicalPath());
        logParser.addLogFile(file);

        ExceptionCheckInterceptor exceptionCheckInterceptor = new ExceptionCheckInterceptor();
        exceptionCheckInterceptor.setResult(new StatisticResult());
        logParser.setHandler(exceptionCheckInterceptor);
        logParser.setHandler(new CounterInterceptor());

        logParser.parse();

        File temp = new File(logFileName + "-exception.log");
        if (temp.exists()) {
            if (!temp.delete())
                throw new IOException();
        }
        if (!temp.createNewFile()) {
            throw new IOException();
        }

        PrintStream printStream = new PrintStream(new FileOutputStream(temp));

        StatisticResult result = (StatisticResult) exceptionCheckInterceptor.getResult();
        if (result != null) {
            for (LogRecord logRecord : result.getExceptionList()) {
                printStream.print(logRecord);
                printStream.println();
                System.out.println(logRecord);
                System.out.println();

            }
        }

    }

}
