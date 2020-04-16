package bootstrap;

import analyzer.Analyzer;
import analyzer.BESAnalyzer;
import analyzer.BESAnalyzer95;
import analyzer.ExceptionAnalyzer;
import analyzer.WebLogicAnalyzer;
import analyzer.WebLogicAnalyzer2;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import util.Constants;
import util.FileUtils;
import util.CommandLineHelper;
import util.ReaderUtils;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class LogAnalyzerMain {

    public static void main(String[] args) throws Exception {
        //程序入口
        LogAnalyzerMain logAnalyzerMain = new LogAnalyzerMain();

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
        logAnalyzerMain.processOnce(args);
    }

    public void processOnce(String[] args) throws Exception {
        //参数验证
        LogCommandLineRuntime logCommandLineRuntime = CommandLineHelper.buildCommandLineRuntime(args);

        //处理日志文件所在的目录, 寻找可能要处理的日志文件
        List<String> logFiles = getHandleLogFile(logCommandLineRuntime);
        if (logFiles.isEmpty()) {
            System.out.println("Not found the file to be processed!");
            return;
        }

        //打印参数
        logCommandLineRuntime.printRuntimeArgs();

        //执行分析
        System.out.println("Begin Analyze...");
        invokeAnalyzer(logCommandLineRuntime);
        System.out.println("Finished Analyze...");
    }

    public static List<String> getHandleLogFile(LogCommandLineRuntime lineRuntime) {
        //处理日志文件所在的目录, 寻找可能要处理的日志文件
        List<String> allLogFiles = getAllLogFileRecursion(lineRuntime.getLogFiles());

        //过滤访问日志 通过日志文件的名字正则
        String excludeRegex = lineRuntime.getExcludeRegex();
        if (!ReaderUtils.isBlank(excludeRegex)) {
            FileUtils.filterByFileName(allLogFiles, excludeRegex);
        }

        //过滤结果目录 通过结果目录的名字正则
        FileUtils.filterByDirName(allLogFiles, Constants.DEFAULT_RESULT_DIR_SUFFIX_REGEX);

        return allLogFiles;
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

    //按照类型调用对应的解析器
    private void invokeAnalyzer(LogCommandLineRuntime lineRuntime) throws Exception {
        String logType = lineRuntime.getLogType();

        Analyzer analyzer;
        if (logType.equalsIgnoreCase("bes")) {
            analyzer = new BESAnalyzer(lineRuntime);
        } else if (logType.equalsIgnoreCase("bes95")) {
            analyzer = new BESAnalyzer95(lineRuntime);
        } else if (logType.equalsIgnoreCase("weblogic")) {
            analyzer = new WebLogicAnalyzer(lineRuntime);
        } else if (logType.equalsIgnoreCase("weblogic2")) {
            analyzer = new WebLogicAnalyzer2(lineRuntime);
        } else if (logType.equalsIgnoreCase("exception")) {
            analyzer = new ExceptionAnalyzer(lineRuntime);
        } else {
            throw new Exception("Invalid log type, please check it !");
        }

        analyzer.analyze();
    }

}
