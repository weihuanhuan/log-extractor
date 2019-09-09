package matcher;

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


}
