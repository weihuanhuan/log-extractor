package util;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class Constants {

    //一些默认值的定义
    public static String EMPTY_STRING = "";

    public static String DEFAULT_LOG_ENCODING = "UTF-8";

    public static String DEFAULT_LOG_TYPE = "bes95";

    public static String DEFAULT_MERGED_DIR = System.getProperty("user.dir");

    public static String DEFAULT_MATCH_LENGTH = "100";

    public static String DEFAULT_RESULT_DIR_SUFFIX = "-result";

    public static String DEFAULT_RESULT_DIR_SUFFIX_REGEX = "(^.*\\\\.*?" + DEFAULT_RESULT_DIR_SUFFIX + "\\\\.*$)";

    public static String DEFAULT_EXCLUDE_REGEX = "(^.*_access_log.*$)";

    public static String DEFAULT_COMPRESS_DIGITAL_LENGTH = "0";

    public static String DEFAULT_CAPTURE_EXCEL = "true";

    public static String DEFAULT_LOG_SUFFIX = "####|";

    public static String DEFAULT_LOG_PREFIX = "|####";

    public static String DEFAULT_LOG_FILED_SEPARATOE = "|";

    public static String CHARSET_NAME_ISO_8859_1 = "ISO-8859-1";

    public static String DEFAULT_FILENAME = "exception-statistic";

    public static String DEFAULT_MERGE_FILENAME = "exception-statistic-merged";

}
