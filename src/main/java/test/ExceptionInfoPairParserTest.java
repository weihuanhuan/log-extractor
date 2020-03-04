package test;

import interceptor.CounterInterceptor;
import interceptor.ExceptionInfoPairInterceptor;
import interceptor.Interceptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import parser.BESLogParser;
import parser.LogParser;
import parser.WebLogicLogParser2;
import result.ExceptionInfoPair;
import result.Result;
import result.StatisticResult;
import util.Constants;
import writer.FileUtils;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class ExceptionInfoPairParserTest {

    public static void main(String[] args) throws IOException {

        String logFileName = "BesServer1.log";
        LogParser logParser = new WebLogicLogParser2("utf-8");

        String absolutePath = new File(logFileName).getAbsolutePath();
        logParser.addLogFile(new File(absolutePath));

        int matchLength = Integer.parseInt(Constants.DEFAULT_MATCH_LENGTH);
        StatisticResult statisticResult = new StatisticResult();
        statisticResult.setFileNamePath(absolutePath);

        Interceptor interceptor = new ExceptionInfoPairInterceptor(matchLength, statisticResult);
        logParser.setHandler(interceptor);
        logParser.setHandler(new CounterInterceptor());

        logParser.parse();

        StatisticResult result = (StatisticResult) interceptor.getResult();

        List<Result> results = new ArrayList<>();
        results.add(result);
        FileUtils.writeResults(results, "unused-dir", matchLength);

        //JF 编码问题如何处理？
        //FileUtils.writeResults(results, "unused-dir", Constants.DEFAULT_LOG_ENCODING, matchLength);

        printStatistic(result, absolutePath);

    }

    public static void printStatistic(StatisticResult result, String absolutePath) throws IOException {
        if (result == null) {
            return;
        }

        File temp = new File(absolutePath + "-statistic.log");
        if (temp.exists()) {
            if (!temp.delete())
                throw new IOException();
        }
        if (!temp.createNewFile()) {
            throw new IOException();
        }

        PrintStream printStream = new PrintStream(new FileOutputStream(temp));
        for (Map.Entry<ExceptionInfoPair, Integer> item : result.getAccumulator().getSortedDataList()) {
            printStream.print(item);
            printStream.println();
        }
    }

}

