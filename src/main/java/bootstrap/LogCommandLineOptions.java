package bootstrap;

public class LogCommandLineOptions {

    public static final String LOG_TYPE = "t";
    public static final String LOG_TYPE_LONG = "log-type";
    public static final String LOG_TYPE_DEFAULT = "bes95";
    public static final String LOG_TYPE_DESC =
            "Optional, String, log file type, currently supports bes, bes95, weblogic, weblogic2 and exception, default <" + LOG_TYPE_DEFAULT + "> .";

    public static final String LOG_ENCODING = "e";
    public static final String LOG_ENCODING_LONG = "log-encoding";
    public static final String LOG_ENCODING_DEFAULT = "UTF-8";
    public static final String LOG_ENCODING_DESC =
            "Optional, ,String, log file encoding, default <" + LOG_ENCODING_DEFAULT + "> .";

    public static final String MERGED_DIR = "d";
    public static final String MERGED_DIR_LONG = "merged-dir";
    public static final String MERGED_DIR_DEFAULT = System.getProperty("user.dir");
    public static final String MERGED_DIR_DESC =
            "Optional, String, merged result output dir, default <" + MERGED_DIR_DEFAULT + "> .";

    public static final String MATCH_LENGTH = "n";
    public static final String MATCH_LENGTH_LONG = "match-length";
    public static final String MATCH_LENGTH_DEFAULT = "100";
    public static final String MATCH_LENGTH_DESC =
            "Optional, Integer, character length that matching the same exception, default <" + MATCH_LENGTH_DEFAULT + "> .";

    public static final String EXCLUDE_REGEX = "x";
    public static final String EXCLUDE_REGEX_LONG = "exclude-regex";
    public static final String EXCLUDE_REGEX_DEFAULT = "(^.*_access_log.*$)";
    public static final String EXCLUDE_REGEX_DESC =
            "Optional, String, exclude regular expressions for parsing log file name, default <" + EXCLUDE_REGEX_DEFAULT + "> .";

    public static final String COMPRESS_LENGTH = "c";
    public static final String COMPRESS_LENGTH_LONG = "compress-length";
    public static final String COMPRESS_LENGTH_DEFAULT = "0";
    public static final String COMPRESS_LENGTH_DESC =
            "Optional, Integer, compressed digital length, to avoid scattered statistical results dut to different id info, default <" + COMPRESS_LENGTH_DEFAULT + "> .";

    public static final String CAPTURE_EXCEL = "s";
    public static final String CAPTURE_EXCEL_LONG = "capture-excel";
    public static final String CAPTURE_EXCEL_DEFAULT = "true";
    public static final String CAPTURE_EXCEL_DESC =
            "Optional, Boolean, capture excel info a picture, default <" + CAPTURE_EXCEL_DEFAULT + "> .";

    public static final String LOG_FILES = "f";
    public static final String LOG_FILES_LONG = "log-files";
    public static final String LOG_FILES_DEFAULT = "";
    public static final String LOG_FILES_DESC =
            "Required, String, input files, comma separated list of input files or dirs.";

    public static final String ADDITITION_MATCH = "a";
    public static final String ADDITITION_MATCH_LONG = "addition-match";
    public static final String ADDITITION_MATCH_DEFAULT = "";
    public static final String ADDITITION_MATCH_DESC =
            "Optional, String, addition match, a regex for addition match content.";

}
