package test;

import bootstrap.LogAnalyzerMain;
import org.apache.commons.cli.Options;

import java.util.LinkedList;
import java.util.List;

public class MainTest {

    public static void main(String[] args) throws Exception {

        //程序入口
        LogAnalyzerMain logAnalyzerMain = new LogAnalyzerMain();
        Options options = logAnalyzerMain.setOptions();

        //测试参数
        //guanli
        String[] args1 = new String[]{"-f", "./0906/guanli/server.log", "-n", "100"};
        String[] args2 = new String[]{"-f", "./0906/guanli/server.log,./0906/guanli/server - Copy.log", "-n", "100"};
        String[] args3 = new String[]{"-t", "weblogic2", "-f", "0906/guanli/CrmBkg01.log"};

        //kefu
        String[] args4 = new String[]{"-f", "./0906/kefu/server.log"};
        String[] args5 = new String[]{"-t", "weblogic", "-f", "0906/kefu/ncrmcs0301_10.32.216.101_CrmCS03.out.20190906"};

        //web
        String[] args6 = new String[]{"-f", "./0906/web/CrmMaster1.log"};
        String[] args7 = new String[]{"-f", "./0906/web/crmslave2.log"};
        String[] args8 = new String[]{"-t", "weblogic", "-f", "0906/web/ncrmweb0501_10.32.216.121_CrmMaster01.out.20190906"};
        String[] args9 = new String[]{"-t", "weblogic", "-f", "0906/web/ncrmweb0501_10.32.216.121_CrmSlave02.out.20190906"};

        List<String[]> argsList = new LinkedList<>();
        argsList.add(args1);
        argsList.add(args2);
        argsList.add(args3);
        argsList.add(args4);
        argsList.add(args5);
        argsList.add(args6);
        argsList.add(args7);
        argsList.add(args8);
        argsList.add(args9);

        for (String[] arg : argsList) {
            System.out.println("############################################################################");
            logAnalyzerMain.processOnce(options, arg);
        }

    }

}
