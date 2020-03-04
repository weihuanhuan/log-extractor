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
        Pattern pattern = Pattern.compile(regularExpression, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public static void printMatchEchoGroups(String string, String regularExpression) {
        Pattern pattern = Pattern.compile(regularExpression, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            int count = matcher.groupCount();
            System.out.println("#####groupCount:" + matcher.groupCount());
            for (int i = 0; i < count + 1; ++i)
                System.out.println("#####i=" + i + ":" + matcher.group(i));
        }
    }

    public static List<MatchResult> getMatchResult(String string, String regularExpression) {
        Pattern pattern = Pattern.compile(regularExpression, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(string);

        List<MatchResult> result = new LinkedList<>();
        while (matcher.find()) {
            result.add(matcher.toMatchResult());
        }

        return result;
    }

}
