package test;

import bootstrap.LogAnalyzerMain;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import util.Constants;
import util.ReaderUtils;

/**
 * Created by JasonFitch on 9/9/2019.
 */
public class ArgsTest {

    public static void main(String[] args) {

        //程序入口
        LogAnalyzerMain logAnalyzerMain = new LogAnalyzerMain();
        Options options = logAnalyzerMain.setOptions();

        //测试参数
        //guanli
        args = new String[]{"-f", "./0906/guanli/server.log", "-n", "100"};

        //参数验证
        CommandLine commandLine = logAnalyzerMain.parserArgs(options, args);
        String logType = commandLine.getOptionValue("log-type");
        if (ReaderUtils.isBlank(logType)) {
            logType = Constants.DEFAULT_LOG_TYPE;
        }

        logType = commandLine.getOptionValue("log-type");
        System.out.println("logType======"+logType);

        List<String> logFileList = Arrays.asList(commandLine.getOptionValue("log-files").split(","));
        String logEncoding = commandLine.getOptionValue("log-encoding");
        if (ReaderUtils.isBlank(logEncoding)) {
            logEncoding = Constants.DEFAULT_LOG_ENCODING;
        }
        String outDir = commandLine.getOptionValue("out-dir");
        if (ReaderUtils.isBlank(outDir)) {
            outDir = Constants.DEFAULT_OUT_DIR;
        }
        String matchLength = commandLine.getOptionValue("match-length");
        if (ReaderUtils.isBlank(matchLength)) {
            matchLength = Constants.DEFAULT_MATCH_LENGTH;
        }
        int matchLengthInt = Integer.parseInt(matchLength);
        if (matchLengthInt < 0) {
            matchLengthInt = Integer.parseInt(Constants.DEFAULT_MATCH_LENGTH);
        }

        //打印参数
        logAnalyzerMain.printArgs(logType, logFileList, logEncoding, outDir, matchLengthInt);

    }
}
