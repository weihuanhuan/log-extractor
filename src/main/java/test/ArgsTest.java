package test;

import bootstrap.CommandOptions;
import bootstrap.LogAnalyzerMain;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import util.Constants;
import util.FileUtils;
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
        String logType = commandLine.getOptionValue(CommandOptions.LOG_TYPE);
        if (ReaderUtils.isBlank(logType)) {
            logType = Constants.DEFAULT_LOG_TYPE;
        }

        String logEncoding = commandLine.getOptionValue(CommandOptions.LOG_ENCODING);
        if (ReaderUtils.isBlank(logEncoding)) {
            logEncoding = Constants.DEFAULT_LOG_ENCODING;
        }
        String outDir = commandLine.getOptionValue(CommandOptions.MERGED_DIR);
        if (ReaderUtils.isBlank(outDir)) {
            outDir = Constants.DEFAULT_MERGED_DIR;
        }
        String matchLength = commandLine.getOptionValue(CommandOptions.MATCH_LENGTH);
        if (ReaderUtils.isBlank(matchLength)) {
            matchLength = Constants.DEFAULT_MATCH_LENGTH;
        }
        int matchLengthInt = Integer.parseInt(matchLength);
        if (matchLengthInt < 0) {
            matchLengthInt = Integer.parseInt(Constants.DEFAULT_MATCH_LENGTH);
        }
        String excludeRegex = commandLine.getOptionValue(CommandOptions.EXCLUDE_REGEX);
        if (ReaderUtils.isBlank(excludeRegex)) {
            excludeRegex = Constants.DEFAULT_EXCLUDE_REGEX;
        }

        String compressDigitalLength = commandLine.getOptionValue(CommandOptions.COMPRESS_LENGTH);
        if (ReaderUtils.isBlank(compressDigitalLength)) {
            compressDigitalLength = Constants.DEFAULT_COMPRESS_DIGITAL_LENGTH;
        }
        int compressDigitalLengthInt = Integer.parseInt(compressDigitalLength);
        if (compressDigitalLengthInt < 0) {
            compressDigitalLengthInt = Integer.parseInt(Constants.DEFAULT_COMPRESS_DIGITAL_LENGTH);
        }

        String captureExcel = commandLine.getOptionValue(CommandOptions.CAPTURE_EXCEL);
        if (ReaderUtils.isBlank(captureExcel)) {
            captureExcel = Constants.DEFAULT_CAPTURE_EXCEL;
        }
        boolean captureExcelBool = Boolean.parseBoolean(captureExcel);

        //处理日志文件所在的目录, 寻找可能要处理的日志文件
        List<String> logDirs = Arrays.asList(commandLine.getOptionValue(CommandOptions.LOG_FILES).split(","));
        List<String> logFiles = logAnalyzerMain.getAllLogFileRecursion(logDirs);

        //过滤访问日志 通过日志文件的名字正则
        if (!ReaderUtils.isBlank(excludeRegex)) {
            FileUtils.filterByFileName(logFiles, excludeRegex);
        }

        //过滤结果目录 通过结果目录的名字正则
        FileUtils.filterByDirName(logFiles, Constants.DEFAULT_RESULT_DIR_SUFFIX_REGEX);

        if (logFiles.isEmpty()) {
            System.out.println("Not found the file to be processed!");
            return;
        }

        //打印参数
        logAnalyzerMain.printArgs(logType, logFiles, logEncoding, outDir
                , matchLengthInt, excludeRegex, compressDigitalLengthInt, captureExcelBool);

    }
}
