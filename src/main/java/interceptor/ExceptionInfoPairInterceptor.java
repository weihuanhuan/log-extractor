package interceptor;

import bootstrap.LogCommandLineRuntime;
import entry.LogRecord;
import entry.WebLogicRecord;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import matcher.LogMatcher;
import result.StatisticResult;
import util.ReaderUtils;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class ExceptionInfoPairInterceptor extends AbstractInterceptor implements Interceptor {

    private int matchLength;
    private String additionalMatch;

    private static Pattern exPattern;
    private static Pattern adPattern;

    public ExceptionInfoPairInterceptor(LogCommandLineRuntime lineRuntime, StatisticResult result) {
        this.matchLength = lineRuntime.getMatchLength();
        this.exPattern = LogMatcher.getPattern(LogMatcher.EXCEPTION_REG_ALL);
        this.additionalMatch = lineRuntime.getAdditionalMatch();
        if (!ReaderUtils.isBlank(additionalMatch)) {
            this.adPattern = LogMatcher.getPattern(additionalMatch);
        }
        this.result = result;
    }

    @Override
    public void invoke(LogRecord logRecord) {
        StatisticResult statisticResult = (StatisticResult) this.result;

        boolean hasException1 = false;
        boolean hasException2 = false;
        boolean hasException3 = false;
        if (logRecord instanceof WebLogicRecord) {
            WebLogicRecord webLogicRecord = (WebLogicRecord) logRecord;
            String webLogicRecordInfo = webLogicRecord.getInfo();
            hasException1 = fillResult(statisticResult, webLogicRecordInfo, matchLength);
        }

        String detail = logRecord.getDetail();
        hasException2 = fillResult(statisticResult, detail, matchLength);

        if (adPattern != null && !hasException1 && !hasException2) {
            hasException3 = fillAdditionResult(statisticResult, detail, matchLength);
        }

        if (hasException1 || hasException2 || hasException3) {
            statisticResult.getExceptionList().add(logRecord);
        }

        if (this.next != null) {
            this.next.invoke(logRecord);
        }
    }

    private static boolean fillResult(StatisticResult statisticResult, String content, int matchLength) {
        List<MatchResult> groups = LogMatcher.getMatchAllResult(content, exPattern);
        if (groups.isEmpty()) {
            return false;
        }

        for (MatchResult matchResult : groups) {
            String group;
            int start;
            int end;

            String group1 = matchResult.group(1);
            if (group1 == null || group1.isEmpty()) {
                group = matchResult.group(0);
                start = matchResult.start(0);
                end = matchResult.end(0);
            } else {
                group = matchResult.group(1);
                start = matchResult.start(1);
                end = matchResult.end(1);
            }

            int causedByStart = start - LogMatcher.CAUSED_BY.length();
            if (causedByStart >= 0) {
                String causedByException = content.substring(causedByStart, causedByStart + LogMatcher.CAUSED_BY.length());
                if ((LogMatcher.CAUSED_BY).equals(causedByException)) {
                    continue;
                }
            }

            String exceptionInfo;
            int infoPartEnd = end + matchLength;
            if (infoPartEnd > content.length()) {
                infoPartEnd = content.length();
            }
            if (infoPartEnd < end) {
                infoPartEnd = end;
            }
            exceptionInfo = content.substring(end, infoPartEnd);

            int finalInfoEnd = exceptionInfo.length();
            int atIndex = exceptionInfo.indexOf("\tat ");
            int lineIndex = exceptionInfo.indexOf("\n");
            if (atIndex >= 0) {
                finalInfoEnd = atIndex;
            }
            if (lineIndex >= 0 && finalInfoEnd > lineIndex) {
                finalInfoEnd = lineIndex;
            }
            if (finalInfoEnd < exceptionInfo.length()) {
                exceptionInfo = exceptionInfo.substring(0, finalInfoEnd);
            }

            exceptionInfo = exceptionInfo.trim();
            statisticResult.getAccumulator().count(group, exceptionInfo);
        }
        return true;
    }

    private boolean fillAdditionResult(StatisticResult statisticResult, String content, int matchLength) {
        // 简单正则，巨慢无比，如何优化？
//        List<MatchResult> groups = LogMatcher.getMatchOneResult(content, adPattern);
//        if (groups.isEmpty()) {
//            return false;
//        }

        boolean matchContent = LogMatcher.isMatchContent(content, adPattern);
        if (!matchContent) {
            return false;
        }

        String group = additionalMatch;
        String exceptionInfo = content;
        if (matchLength < exceptionInfo.length()) {
            exceptionInfo = exceptionInfo.substring(0, matchLength);
        }
        statisticResult.getAccumulator().count(group, exceptionInfo.trim());
        return true;
    }

}
