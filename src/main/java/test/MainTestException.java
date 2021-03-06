package test;

import bootstrap.LogAnalyzerMain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainTestException {

    public static void main(String[] args) throws Exception {

        //程序入口
        LogAnalyzerMain logAnalyzerMain = new LogAnalyzerMain();

//        测试参数
//        guanli
        String[] args1 = new String[]{"-f", "./0906/guanli/server.log"};
        String[] args2 = new String[]{"-f", "0906/guanli/CrmBkg01.log"};
//
        //kefu
        String[] args3 = new String[]{"-f", "./0906/kefu/server.log"};
        String[] args4 = new String[]{"-f", "0906/kefu/ncrmcs0301_10.32.216.101_CrmCS03.out.20190906"};
//
        //web
        String[] args5 = new String[]{"-f", "./0906/web/CrmMaster1.log"};
        String[] args6 = new String[]{"-f", "./0906/web/crmslave2.log"};
        String[] args7 = new String[]{"-f", "0906/web/ncrmweb0501_10.32.216.121_CrmMaster01.out.20190906"};
        String[] args8 = new String[]{"-f", "0906/web/ncrmweb0501_10.32.216.121_CrmSlave02.out.20190906"};

        List<String> commonOptions = new ArrayList<>();
        commonOptions.add("-t");
        commonOptions.add("exception");
        commonOptions.add("-e");
        commonOptions.add("GB2312");

        List<String[]> argsList = new LinkedList<>();
        argsList.add(args1);
        argsList.add(args2);

        argsList.add(args3);
        argsList.add(args4);

        argsList.add(args5);
        argsList.add(args6);
        argsList.add(args7);
        argsList.add(args8);

        for (String[] arg : argsList) {
            List<String> finalOptions = new ArrayList<>();
            finalOptions.addAll(Arrays.asList(arg));
            finalOptions.addAll(commonOptions);

            System.out.println("############################################################################");
            logAnalyzerMain.processOnce(finalOptions.toArray(new String[0]));
        }

    }

}
