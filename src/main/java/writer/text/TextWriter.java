package writer.text;

import entry.LogRecord;
import result.Result;
import result.StatisticResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import util.FileUtils;

public class TextWriter {

    public static void write(Result result, File outDir) throws IOException {

        StatisticResult realResult = (StatisticResult) result;

        //如果存在，删除旧的text文件
        File textFile = new File(outDir, "exception-info.log");
        if (textFile.exists()) {
            FileUtils.deleteFile(textFile);
        }

        //将查找见的异常信息写入到文件
        PrintStream printStream = new PrintStream(new FileOutputStream(textFile));
        for (LogRecord logRecord : realResult.getExceptionList()) {
            printStream.print(logRecord);
            printStream.println();
        }
        System.out.println("statistics text file: " + textFile.getCanonicalPath());
    }

}