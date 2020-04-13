package bootstrap;

import analyzer.Analyzer;
import analyzer.BESAnalyzer;
import analyzer.BESAnalyzer95;
import analyzer.ExceptionAnalyzer;
import analyzer.WebLogicAnalyzer;
import analyzer.WebLogicAnalyzer2;

import java.io.File;
import java.util.ArrayList;

import result.Result;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import util.Constants;
import util.ReaderUtils;
import util.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import util.ResultUtils;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class LogAnalyzerMain {

    public static void main(String[] args) throws Exception {

        //程序入口
        LogAnalyzerMain logAnalyzerMain = new LogAnalyzerMain();
        Options options = logAnalyzerMain.setOptions();

        //关于日志格式
        // bes
        // 标准bes日志格式  ####| time | level | module | thread | detail |####

        // bes95
        // 标准bes日志格式  ##| time | level | module | thread | detail |##

        // weblogic
        // weblogic日志格式  < time > < level > < module > < code > < info > detail

        // weblogic2
        // weblogic2日志格式 ####< time > < level > < module > < app > < file > < thread > < source > < blank1 > < blank2 > < timestamp > < code > < detail >

        //执行一次分析
        logAnalyzerMain.processOnce(options, args);

    }

    public void processOnce(Options options, String[] args) throws Exception {
        //参数验证
        CommandLine commandLine = parserArgs(options, args);
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
        List<String> logFiles = getAllLogFileRecursion(logDirs);

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
        printArgs(logType, logFiles, logEncoding, outDir
                , matchLengthInt, excludeRegex, compressDigitalLengthInt, captureExcelBool);

        System.out.println("Begin Analyze...");
        //执行分析
        List<Result> results = invokeAnalyzer(logType, logFiles, logEncoding, matchLengthInt, compressDigitalLengthInt);

        System.out.println("Analyze Finished...");

        //打印分析结果
        printResult(results, outDir, matchLengthInt, captureExcelBool);
    }

    public static List<String> getAllLogFileRecursion(List<String> logDirs) {
        List<String> logFiles = new ArrayList<>();
        for (String dir : logDirs) {
            File temp = new File(dir);
            if (!temp.exists()) {
                System.out.println("File or directory [ " + dir + " ] does not exist!");
                continue;
            }

            if (temp.isFile()) {
                FileUtils.addFile(temp, logFiles);
            } else {
                FileUtils.recursionDir(temp, logFiles);
            }
        }
        return logFiles;
    }

    //打印统计结果
    private void printResult(List<Result> results, String outDir, int matchLengthInt, boolean captureExcelBool) throws IOException {
        ResultUtils.writeResults(results, outDir, matchLengthInt, captureExcelBool);
    }

    //按照类型调用对应的解析器
    private List<Result> invokeAnalyzer(String logType, List<String> logFileList, String logEncoding, int matchLength, int compressDigitalLength) throws Exception {

        Analyzer analyzer;
        if (logType.equalsIgnoreCase("bes")) {
            analyzer = new BESAnalyzer(logFileList, logEncoding, matchLength, compressDigitalLength);
        } else if (logType.equalsIgnoreCase("bes95")) {
            analyzer = new BESAnalyzer95(logFileList, logEncoding, matchLength, compressDigitalLength);
        } else if (logType.equalsIgnoreCase("weblogic")) {
            analyzer = new WebLogicAnalyzer(logFileList, logEncoding, matchLength, compressDigitalLength);
        } else if (logType.equalsIgnoreCase("weblogic2")) {
            analyzer = new WebLogicAnalyzer2(logFileList, logEncoding, matchLength, compressDigitalLength);
        } else if (logType.equalsIgnoreCase("exception")) {
            analyzer = new ExceptionAnalyzer(logFileList, logEncoding, matchLength, compressDigitalLength);
        } else {
            throw new Exception("Invalid log type, please check it !");
        }

        return analyzer.analyze();
    }

    //处理可执行文件的入参
    public CommandLine parserArgs(Options options, String[] args) {

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("bootstrap.LogAnalyzerMain", options);
            System.exit(1);
        }
        return cmd;
    }


    //设置解析器所用的参数
    public Options setOptions() {
        Options options = new Options();

        Option type = new Option("t", CommandOptions.LOG_TYPE, true,
                "Optional, String, log file type, currently supports bes, bes95, weblogic, weblogic2 and exception, default <" + Constants.DEFAULT_LOG_TYPE + "> .");
        type.setRequired(false);
        options.addOption(type);

        Option input = new Option("f", CommandOptions.LOG_FILES, true,
                "Required, String, input files, comma separated list of input files or dirs.");
        input.setRequired(true);
        options.addOption(input);

        Option encoding = new Option("e", CommandOptions.LOG_ENCODING, true,
                "Optional, ,String, log file encoding, default <" + Constants.DEFAULT_LOG_ENCODING + "> .");
        encoding.setRequired(false);
        options.addOption(encoding);

        Option output = new Option("d", CommandOptions.MERGED_DIR, true,
                "Optional, String, merged result output dir, default <" + Constants.DEFAULT_MERGED_DIR + "> .");
        output.setRequired(false);
        options.addOption(output);

        Option length = new Option("n", CommandOptions.MATCH_LENGTH, true,
                "Optional, Integer, character length that matching the same exception, default <" + Constants.DEFAULT_MATCH_LENGTH + "> .");
        length.setRequired(false);
        options.addOption(length);

        Option exclude = new Option("x", CommandOptions.EXCLUDE_REGEX, true,
                "Optional, String, exclude regular expressions for parsing log file name, default <" + Constants.DEFAULT_EXCLUDE_REGEX + "> .");
        length.setRequired(false);
        options.addOption(exclude);

        Option compress = new Option("c", CommandOptions.COMPRESS_LENGTH, true,
                "Optional, Integer, compressed digital length, to avoid scattered statistical results dut to different id info, default <" + Constants.DEFAULT_COMPRESS_DIGITAL_LENGTH + "> .");
        length.setRequired(false);
        options.addOption(compress);

        Option capture = new Option("s", CommandOptions.CAPTURE_EXCEL, true,
                "Optional, Boolean, capture excel info a picture, default <" + Constants.DEFAULT_CAPTURE_EXCEL + "> .");
        length.setRequired(false);
        options.addOption(capture);

        return options;
    }

    //打印参数信息
    public void printArgs(String logType, List<String> logFileList, String logEncoding, String outDir,
                          int matchLength, String excludeRegex, int compressDigitalLengthInt, boolean captureExcelBool) {
        System.out.println("user.dir    : " + System.getProperty("user.dir"));

        System.out.println();
        System.out.println("Analyzer runtime arguments info:");
        System.out.println("log-type       : " + logType);
        System.out.println("log-encoding   : " + logEncoding);
        System.out.println("match-length   : " + matchLength);
        System.out.println("exclude-regex  : " + excludeRegex);
        System.out.println("compress-length: " + compressDigitalLengthInt);
        System.out.println("capture-excel  : " + captureExcelBool);

        System.out.println();
        System.out.println("Target files be processed:");
        for (String f : logFileList) {
            Path file = Paths.get(f);
            System.out.println(file.normalize().toAbsolutePath());
        }

        System.out.println();
        System.out.println("Statistic merged result file path:");
        Path ourDir = Paths.get(outDir);
        System.out.println(ourDir.normalize().toAbsolutePath());

        System.out.println();
    }

}
