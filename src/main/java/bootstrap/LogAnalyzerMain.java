package bootstrap;

import analyzer.Analyzer;
import analyzer.BESAnalyzer;
import analyzer.ExceptionAnalyzer;
import analyzer.WebLogicAnalyzer;
import analyzer.WebLogicAnalyzer2;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import result.Result;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import util.Constants;
import util.Utils;
import writer.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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

        // weblogic
        // weblogic日志格式  < time > < level > < module > < code > < info > detail

        // weblogic2
        // weblogic2日志格式 < time > < level > < module > < app > < file > < thread > < source > < blank1 > < blank2 > < timestamp > < code > < detail >

        //执行一次分析
        logAnalyzerMain.processOnce(options, args);

    }

    public void processOnce(Options options, String[] args) throws Exception {
        //参数验证
        CommandLine commandLine = parserArgs(options, args);
        String logType = commandLine.getOptionValue("log-type");
        if (Utils.isBlank(logType)) {
            logType = Constants.DEFAULT_LOG_TYPE;
        }

        String logEncoding = commandLine.getOptionValue("log-encoding");
        if (Utils.isBlank(logEncoding)) {
            logEncoding = Constants.DEFAULT_LOG_ENCODING;
        }
        String outDir = commandLine.getOptionValue("out-dir");
        if (Utils.isBlank(outDir)) {
            outDir = Constants.DEFAULT_OUT_DIR;
        }
        String matchLength = commandLine.getOptionValue("match-length");
        if (Utils.isBlank(matchLength)) {
            matchLength = Constants.DEFAULT_MATCH_LENGTH;
        }
        int matchLengthInt = Integer.parseInt(matchLength);
        if (matchLengthInt < 0) {
            matchLengthInt = Integer.parseInt(Constants.DEFAULT_MATCH_LENGTH);
        } else {
            matchLengthInt += 9;
        }

        //处理日志文件所在的目录
        List<String> logDirs = Arrays.asList(commandLine.getOptionValue("log-files").split(","));
        List<String> logFiles = new ArrayList<>();
        for (String dir : logDirs) {

            if (!Paths.get(dir).toFile().exists()) {
                System.out.println("File or directory [ " + dir + " ] does not exist!");
            } else if (Paths.get(dir).toFile().isDirectory()) {
                List<File> fs = Arrays.asList(Paths.get(dir).toFile().listFiles());
                Collections.sort(fs, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                for (File f : fs) {
                    if (f.isFile()) {
                        String path = f.getPath();
                        if (!logFiles.contains(path)) {
                            logFiles.add(path);
                        }
                    }
                }

            } else if (Paths.get(dir).toFile().isFile()) {
                if (!logFiles.contains(dir)) {
                    logFiles.add(dir);
                }
            }
        }

        if (logFiles.isEmpty()) {
            System.out.println("Not found the file to be processed!");
            return;
        }

        //打印参数        
        printArgs(logType, logFiles, logEncoding, outDir, matchLengthInt);

        System.out.println("Begin Analyze...");
        //执行分析
        List<Result> results = invokeAnalyzer(logType, logFiles, logEncoding, matchLengthInt);

        System.out.println("Analyze Finished...");

        //打印分析结果
        printResult(results, outDir, matchLengthInt);
    }

    //打印统计结果
    private void printResult(List<Result> results, String outDir, int matchLengthInt) throws IOException {
        FileUtils.writeResults(results, outDir, matchLengthInt);
    }

    //按照类型调用对应的解析器
    private List<Result> invokeAnalyzer(String logType, List<String> logFileList, String logEncoding, int matchLength) throws Exception {

        Analyzer analyzer;
        if (logType.equalsIgnoreCase("bes")) {
            analyzer = new BESAnalyzer(logFileList, logEncoding, matchLength);
        } else if (logType.equalsIgnoreCase("weblogic")) {
            analyzer = new WebLogicAnalyzer(logFileList, logEncoding, matchLength);
        } else if (logType.equalsIgnoreCase("weblogic2")) {
            analyzer = new WebLogicAnalyzer2(logFileList, logEncoding, matchLength);
        } else if (logType.equalsIgnoreCase("exception")) {
            analyzer = new ExceptionAnalyzer(logFileList, logEncoding, matchLength);
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

        Option type = new Option("t", "log-type", true,
                "optional, log file type, currently supports bes and weblogic weblogic2 logs and exception, default <" + Constants.DEFAULT_LOG_TYPE + "> .");
        type.setRequired(false);
        options.addOption(type);

        Option input = new Option("f", "log-files", true,
                "input files, comma separated list of input files or dirs.");
        input.setRequired(true);
        options.addOption(input);

        Option encoding = new Option("e", "log-encoding", true,
                "optional, log file encoding, default <" + Constants.DEFAULT_LOG_ENCODING + "> .");
        encoding.setRequired(false);
        options.addOption(encoding);

//        Option output = new Option("d", "out-dir", true,
//                "optional, result output dir, default <" + Constants.DEFAULT_OUT_DIR + "> .");
//        output.setRequired(false);
//        options.addOption(output);

        Option length = new Option("n", "match-length", true,
                "optional, character length that matching the same exception, default <" + Constants.DEFAULT_MATCH_LENGTH + "> .");
        length.setRequired(false);
        options.addOption(length);

        return options;
    }

    //打印参数信息
    public void printArgs(String logType, List<String> logFileList, String logEncoding, String outDir, int matchLength) {
        System.out.println("user.dir    : " + System.getProperty("user.dir"));

        System.out.println();
        System.out.println("Analyzer runtime arguments info:");
        System.out.println("log-type    : " + logType);
        System.out.println("log-encoding: " + logEncoding);
        if (matchLength == Integer.valueOf(Constants.DEFAULT_MATCH_LENGTH)) {
            System.out.println("match-length: " + (matchLength));
        } else {
            System.out.println("match-length: " + (matchLength - 9));
        }

        System.out.println();
        System.out.println("Target files be processed:");
        for (String f : logFileList) {
            Path file = Paths.get(f);
            System.out.println(file.normalize().toAbsolutePath());
        }

//        System.out.println();
//        System.out.println("Statistic result file path:");
//        Path ourDir = Paths.get(outDir);
//        System.out.println(ourDir.normalize().toAbsolutePath());

        System.out.println();
    }

}
