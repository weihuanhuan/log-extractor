package test;

import bootstrap.LogAnalyzerMain;
import bootstrap.LogCommandLineRuntime;
import util.CommandLineHelper;
import java.util.List;

/**
 * Created by JasonFitch on 9/9/2019.
 */
public class ArgsTest {

    public static void main(String[] args) {

        //程序入口
        LogAnalyzerMain logAnalyzerMain = new LogAnalyzerMain();

        //测试参数
        //guanli
        args = new String[]{"-f", "./0906/guanli/server.log", "-n", "100"};

        //参数验证
        LogCommandLineRuntime logCommandLineRuntime = CommandLineHelper.buildCommandLineRuntime(args);

        //处理日志文件所在的目录, 寻找可能要处理的日志文件
        List<String> logFiles = logAnalyzerMain.getHandleLogFile(logCommandLineRuntime);
        if (logFiles.isEmpty()) {
            System.out.println("Not found the file to be processed!");
            return;
        }

        //打印参数
        logCommandLineRuntime.printRuntimeArgs();
    }
}
