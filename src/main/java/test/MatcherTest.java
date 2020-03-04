package test;

import matcher.LogMatcher;

public class MatcherTest {

    public static void main(String[] args) {
        testReg();
        testException();
    }

    private static void testReg() {

        System.out.println("#################################################################################");
        LogMatcher.printMatchesWholeEntry(SimulateLogEntry.BES_LOG, LogMatcher.BES_LOG_REG);
        System.out.println("#################################################################################");
        LogMatcher.printMatchEchoGroups(SimulateLogEntry.BES_LOG, LogMatcher.BES_LOG_REG);

        System.out.println("#################################################################################");

        LogMatcher.printMatchesWholeEntry(SimulateLogEntry.WEBLOGIC_LOG, LogMatcher.WEBLOGINC_LOG_REG);
        System.out.println("#################################################################################");
        LogMatcher.printMatchEchoGroups(SimulateLogEntry.WEBLOGIC_LOG, LogMatcher.WEBLOGINC_LOG_REG);
        System.out.println("#################################################################################");
    }

    private static void testException() {
        System.out.println("-------with package small-------------");

        LogMatcher.printMatchEchoGroups("a.b.c.PrefixError   :xxxxxxxxx", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);
        LogMatcher.printMatchEchoGroups("a.b.c.PrefixError   :xxxxxxxxx", LogMatcher.EXCEPTION_REG_ALL);
        LogMatcher.printMatchEchoGroups("a.b.c.PrefixException", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);

        LogMatcher.printMatchEchoGroups("a.b.c.PError", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);
        LogMatcher.printMatchEchoGroups("a.b.c.PException", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);

        LogMatcher.printMatchEchoGroups("a.b.c._PError", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);
        LogMatcher.printMatchEchoGroups("a.b.c._PException", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);

        LogMatcher.printMatchEchoGroups("a.b.c.Error", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);
        LogMatcher.printMatchEchoGroups("a.b.c.Exception", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);

        LogMatcher.printMatchEchoGroups("a.ba.c.Error", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);
        LogMatcher.printMatchEchoGroups("a.ba.c.Exception", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);

        System.out.println("-------should not------------");

        LogMatcher.printMatchEchoGroups("a.b.c.pError", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);
        LogMatcher.printMatchEchoGroups("a.b.c.pException", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);

        System.out.println("-------with package big-------------");

        LogMatcher.printMatchEchoGroups("a.B.c.PrefixError", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);
        LogMatcher.printMatchEchoGroups("a.B.c.PrefixException", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);

        LogMatcher.printMatchEchoGroups("a.Ba.c.PrefixError", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);
        LogMatcher.printMatchEchoGroups("a.Ba.c.PrefixException", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);

        System.out.println("-------without package------------");

        LogMatcher.printMatchEchoGroups("PError", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);
        LogMatcher.printMatchEchoGroups("PException", LogMatcher.EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG);

        System.out.println("####################");
        LogMatcher.printMatchEchoGroups(SimulateLogEntry.NONSTANDARD_LOG, LogMatcher.EXCEPTION_ATCLAZZ_ALLOW_CAPS_PACK_REG);
        LogMatcher.printMatchEchoGroups(SimulateLogEntry.NONSTANDARD_LOG, LogMatcher.EXCEPTION_REG_ALL);

        System.out.println("####################");
        LogMatcher.printMatchEchoGroups("a.Ba.c.PrefixExceptionException", LogMatcher.EXCEPTION_REG_ALL);
    }

}
