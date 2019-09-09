package test;

import matcher.LogMatcher;

public class MatcherTest {

    public static void main(String[] args) {
        testReg();
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

}
