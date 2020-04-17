package matcher;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class LogMatcher {

    static public String DEFAULT_LOG_FILED_FENCE_REG = "<.*?>";

    static public String TRACE_STACK_LINE_REG = "(^\\tat |^Caused by: |^\\t... \\d+ more)";

    static public String NESTED_EXCEPTION_REG = "; nested exception is: .*$";

    static public String EXCEPTION_REG = ".*Exception.*$";

    static public String BES_LOG_REG = "####\\|(.*?)\\|(.*?)\\|(.*?)\\|(.*?)\\|(.*?)\\|####\n";

    static public String WEBLOGINC_LOG_REG = "<(.*?)> <(.*?)> <(.*?)> <(.*?)> <(.*?)> \n(.*)\n";

    //调用栈中的异常名      排除 【 . 】
    //异常信息里面的异常名   排除 【 a-z A-Z 0-9 $ _ , " 】
    static public String EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG = "(?:[A-Za-z]+\\.)+(?:[A-Z_][A-Za-z0-9$_]*)?(?:Exception|Error)(?![a-zA-Z0-9$_,\".])";

    static public String EXCEPTION_ATCLAZZ_ALLOW_CAPS_PACK_REG = "((?:[A-Za-z]+\\.)+[A-Z]?[A-Za-z0-9$_]+?): .*?\\s+?\tat ";

    static public String EXCEPTION_REG_ALL = "(?:" + EXCEPTION_CLAZZ_ALLOW_CAPS_PACK_REG + ")|(?:" + EXCEPTION_ATCLAZZ_ALLOW_CAPS_PACK_REG + ")";

    static public String CAUSED_BY = "Caused by: ";

    public static boolean printMatchesWholeEntry(String string, String regularExpression) {
        Matcher matcher = getMatcher(string, regularExpression);
        return matcher.matches();
    }

    public static void printMatchEchoGroups(String string, String regularExpression) {
        Matcher matcher = getMatcher(string, regularExpression);
        while (matcher.find()) {
            int count = matcher.groupCount();
            System.out.println("#####groupCount:" + matcher.groupCount());
            for (int i = 0; i < count + 1; ++i)
                System.out.println("#####i=" + i + ":" + matcher.group(i));
        }
    }

    public static List<MatchResult> getMatchAllResult(String string, String regularExpression) {
        Pattern pattern = getPattern(regularExpression);
        return getMatchAllResult(string, pattern);
    }

    public static List<MatchResult> getMatchAllResult(String string, Pattern pattern) {
        Matcher matcher = getMatcher(string, pattern);
        return getMatchAllResult(matcher);
    }

    public static List<MatchResult> getMatchAllResult(Matcher matcher) {
        List<MatchResult> result = new LinkedList<>();
        while (matcher.find()) {
            result.add(matcher.toMatchResult());
        }
        return result;
    }

    public static List<MatchResult> getMatchOneResult(String string, String regularExpression) {
        Pattern pattern = getPattern(regularExpression);
        return getMatchOneResult(string, pattern);
    }

    public static List<MatchResult> getMatchOneResult(String string, Pattern pattern) {
        Matcher matcher = getMatcher(string, pattern);
        return getMatchOneResult(matcher);
    }

    public static List<MatchResult> getMatchOneResult(Matcher matcher) {
        List<MatchResult> result = new LinkedList<>();
        if (matcher.find()) {
            result.add(matcher.toMatchResult());
        }
        return result;
    }

    public static boolean isMatchContent(String string, String regularExpression) {
        Pattern pattern = getPattern(regularExpression);
        return isMatchContent(string, pattern);
    }

    public static boolean isMatchContent(String string, Pattern pattern) {
        Matcher matcher = pattern.matcher(string);
        return isMatchContent(matcher);
    }

    public static boolean isMatchContent(Matcher matcher) {
        return matcher.matches();
    }

    public static Matcher getMatcher(String string, String regex) {
        Pattern pattern = getPattern(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher;
    }

    public static Matcher getMatcher(String string, Pattern pattern) {
        return pattern.matcher(string);
    }

    public static Pattern getPattern(String regex) {
        return Pattern.compile(regex, Pattern.DOTALL);
    }

}
