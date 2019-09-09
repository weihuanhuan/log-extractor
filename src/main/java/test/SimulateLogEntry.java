package test;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class SimulateLogEntry {

    public static String BES_LOG = "####|2019-08-20 01:30:03.836|INFO|null|_ThreadID=53;_ThreadName=busiCacheMgrTimer_Worker-6;|190820 01:30:03.834 busiCacheMgrTimer_Worker-6 ERROR ExceptionUtil.stackTrace(ExceptionUtil.java:22) ExceptionUtil.stackTrace Exception: [] \n" +
            "java.lang.NullPointerException\n" +
            "\tat java.util.Properties$LineReader.readLine(Properties.java:418) ~[?:1.6.0_45]\n" +
            "\tat java.util.Properties.load0(Properties.java:337) ~[?:1.6.0_45]\n" +
            "\tat java.util.Properties.load(Properties.java:325) ~[?:1.6.0_45]\n" +
            "\tat com.huawei.ngcrm.inventory.cache.menuproperty.MenuPropertyService.init(MenuPropertyService.java:50) ~[MenuPropertyService.class:?]\n" +
            "\tat com.huawei.ngcrm.inventory.cache.menuproperty.MenuPropertyService.refresh(MenuPropertyService.java:27) [MenuPropertyService.class:?]\n" +
            "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.6.0_45]\n" +
            "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39) ~[?:1.6.0_45]\n" +
            "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25) ~[?:1.6.0_45]\n" +
            "\tat java.lang.reflect.Method.invoke(Method.java:597) ~[?:1.6.0_45]\n" +
            "\tat org.springframework.util.MethodInvoker.invoke(MethodInvoker.java:273) [org.springframework.core-3.0.5.RELEASE.jar:3.0.5.RELEASE]\n" +
            "\tat org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean$MethodInvokingJob.executeInternal(MethodInvokingJobDetailFactoryBean.java:264) [org.springframework.context.support-3.0.5.RELEASE.jar:3.0.5.RELEASE]\n" +
            "\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:86) [org.springframework.context.support-3.0.5.RELEASE.jar:3.0.5.RELEASE]\n" +
            "\tat org.quartz.core.JobRunShell.run(JobRunShell.java:216) [quartz-1.8.5.jar:?]\n" +
            "\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:549) [quartz-1.8.5.jar:?]\n" +
            "|####\n";

    public static String WEBLOGIC_LOG = "<2019-9-6 上午12时00分25秒 CST> <Error> <HTTP> <BEA-101215> <Malformed Request \"null\". Request parsing failed, Code: -1> \n" +
            "java.lang.ArrayIndexOutOfBoundsException: overflow detected\n" +
            "\tat weblogic.utils.http.HttpRequestParser.checkOverflow(HttpRequestParser.java:288)\n" +
            "\tat weblogic.utils.http.HttpRequestParser.incPos(HttpRequestParser.java:278)\n" +
            "\tat weblogic.utils.http.HttpRequestParser.method(HttpRequestParser.java:414)\n" +
            "\tat weblogic.utils.http.HttpRequestParser.startLine(HttpRequestParser.java:359)\n" +
            "\tat weblogic.utils.http.HttpRequestParser.parse(HttpRequestParser.java:323)\n" +
            "\tat weblogic.utils.http.HttpRequestParser.parse(HttpRequestParser.java:115)\n" +
            "\tat weblogic.servlet.internal.MuxableSocketHTTP.dispatch(MuxableSocketHTTP.java:323)\n" +
            "\tat weblogic.servlet.internal.MuxableSocketHTTP.requeue(MuxableSocketHTTP.java:273)\n" +
            "\tat weblogic.servlet.internal.VirtualConnection.requeue(VirtualConnection.java:333)\n" +
            "\tat weblogic.servlet.internal.ServletResponseImpl.send(ServletResponseImpl.java:1581)\n" +
            "\tat weblogic.servlet.internal.ServletRequestImpl.run(ServletRequestImpl.java:1507)\n" +
            "\tat weblogic.work.ExecuteThread.execute(ExecuteThread.java:263)\n" +
            "\tat weblogic.work.ExecuteThread.run(ExecuteThread.java:221)\n";

    public static void main(String[] args) {

        System.out.println(BES_LOG);
        System.out.println("####################################################################");
        System.out.println(WEBLOGIC_LOG);

    }

}
