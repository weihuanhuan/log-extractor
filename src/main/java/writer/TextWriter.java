package writer;

import entry.LogRecord;
import result.Result;
import result.StatisticResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class TextWriter {

    public static void write(List<Result> results) throws IOException {

        for (Result result : results) {
            StatisticResult realResult = (StatisticResult) result;

            String fileNamePath = realResult.getFileNamePath();

            //处理在前的文件，如果存在就删除并新建，否则直接新建
            File temp = new File(fileNamePath + "-exception.log");
            String tempCanonicalPath = temp.getCanonicalPath();
            if (temp.exists()) {
                if (!temp.delete()) {
                    throw new IOException("delete exception info file " + tempCanonicalPath + " failed!");
                }
            }
            if (!temp.createNewFile()) {
                throw new IOException("create exception info file " + tempCanonicalPath + " failed!");
            } else {
                System.out.println("create exception info file " + tempCanonicalPath + " success!");
            }

            //将查找见的异常信息写入到文件
            PrintStream printStream = new PrintStream(new FileOutputStream(temp));
            for (LogRecord logRecord : realResult.getExceptionList()) {
                printStream.print(logRecord);
                printStream.println();
            }
        }

    }

}
