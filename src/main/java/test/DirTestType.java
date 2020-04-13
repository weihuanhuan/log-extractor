package test;

import bootstrap.LogAnalyzerMain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.cli.Options;
import util.Constants;

public class DirTestType {

    public static void main(String[] args) throws Exception {

        //程序入口
        LogAnalyzerMain logAnalyzerMain = new LogAnalyzerMain();
        Options options = logAnalyzerMain.setOptions();

        //测试参数
        String mergedDir = Constants.DEFAULT_MERGED_DIR + "/" + "nonexistence";
        String[] args5 = new String[]{"-t", "bes", "-f", "./0906/web", "-d", mergedDir};

        List<String> commonOptions = new ArrayList<>();
        commonOptions.add("-e");
        commonOptions.add("GB2312");

        List<String[]> argsList = new LinkedList<>();
        argsList.add(args5);

        for (String[] arg : argsList) {
            List<String> finalOptions = new ArrayList<>();
            finalOptions.addAll(Arrays.asList(arg));
            finalOptions.addAll(commonOptions);

            System.out.println("############################################################################");
            logAnalyzerMain.processOnce(options, finalOptions.toArray(new String[0]));
        }

    }

}
