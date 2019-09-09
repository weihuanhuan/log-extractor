package test;

import entry.LogRecord;
import interceptor.ExceptStatisticInter;
import interceptor.CounterInterceptor;
import parser.BESLogParser;
import parser.LogParser;
import result.StatisticResult;
import util.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class ParserTest {

    public static void main(String[] args) throws IOException {

        String logFileName = "server-1.log";
        LogParser logParser = new BESLogParser(Constants.DEFAULT_LOG_ENCODING);

//        String logFileName = "weblogic.log";
//        LogParser logParser = new WebLogicLogParser(Constants.DEFAULT_LOG_ENCODING);

        logParser.addLogFile(new File(logFileName));

        ExceptStatisticInter exceptStatisticInter = new ExceptStatisticInter();
        exceptStatisticInter.setResult(new StatisticResult());
        logParser.setHandler(exceptStatisticInter);
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

        StatisticResult result = (StatisticResult) exceptStatisticInter.getResult();
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
