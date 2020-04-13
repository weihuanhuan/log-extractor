package writer.text;

import entry.LogRecord;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import result.Result;
import result.StatisticResult;

public class TextWriter {

    public static String TEXT_SUFFIX = ".txt";

    public static void write(Result result, File textFile) throws IOException {
        StatisticResult realResult = (StatisticResult) result;

        //将查找见的异常信息写入到文件
        PrintStream printStream = new PrintStream(new FileOutputStream(textFile));
        for (LogRecord logRecord : realResult.getExceptionList()) {
            printStream.print(logRecord);
            printStream.println();
        }
    }

}