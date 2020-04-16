package bootstrap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import util.ReaderUtils;
import util.ReflectionUtils;

/**
 * Created by JasonFitch on 4/16/2020.
 */
public class LogCommandLine {

    private static final String FILENAME_DELIMITER = ",";

    //keep order
    private Map<String, String> originalArgValueMap = new LinkedHashMap<>();

    @LogOption(opt = LogCommandLineOptions.LOG_FILES, longOpt = LogCommandLineOptions.LOG_FILES_LONG, require = true,
            description = LogCommandLineOptions.LOG_FILES_DESC)
    public String logFiles;

    @LogOption(opt = LogCommandLineOptions.LOG_ENCODING, longOpt = LogCommandLineOptions.LOG_ENCODING_LONG,
            defaultArg = LogCommandLineOptions.LOG_ENCODING_DEFAULT, description = LogCommandLineOptions.LOG_ENCODING_DESC)
    public String logEncoding;

    //JF annotation attribute value must be constant.
    @LogOption(opt = LogCommandLineOptions.MERGED_DIR, longOpt = LogCommandLineOptions.MERGED_DIR_LONG,
            description = LogCommandLineOptions.MERGED_DIR_DESC)
    public String mergedDir = LogCommandLineOptions.MERGED_DIR_DEFAULT;

    @LogOption(opt = LogCommandLineOptions.MATCH_LENGTH, longOpt = LogCommandLineOptions.MATCH_LENGTH_LONG,
            defaultArg = LogCommandLineOptions.MATCH_LENGTH_DEFAULT, description = LogCommandLineOptions.MATCH_LENGTH_DESC)
    public String matchLength;

    @LogOption(opt = LogCommandLineOptions.EXCLUDE_REGEX, longOpt = LogCommandLineOptions.EXCLUDE_REGEX_LONG,
            defaultArg = LogCommandLineOptions.EXCLUDE_REGEX_DEFAULT, description = LogCommandLineOptions.EXCLUDE_REGEX_DESC)
    public String excludeRegex;

    @LogOption(opt = LogCommandLineOptions.COMPRESS_LENGTH, longOpt = LogCommandLineOptions.COMPRESS_LENGTH_LONG,
            defaultArg = LogCommandLineOptions.COMPRESS_LENGTH_DEFAULT, description = LogCommandLineOptions.COMPRESS_LENGTH_DESC)
    public String compressLength;

    @LogOption(opt = LogCommandLineOptions.CAPTURE_EXCEL, longOpt = LogCommandLineOptions.CAPTURE_EXCEL_LONG,
            defaultArg = LogCommandLineOptions.CAPTURE_EXCEL_DEFAULT, description = LogCommandLineOptions.CAPTURE_EXCEL_DESC)
    public String captureExcel;

    @LogOption(opt = LogCommandLineOptions.LOG_TYPE, longOpt = LogCommandLineOptions.LOG_TYPE_LONG,
            defaultArg = LogCommandLineOptions.LOG_TYPE_DEFAULT, description = LogCommandLineOptions.LOG_TYPE_DESC)
    public String logType;

    @LogOption(opt = LogCommandLineOptions.ADDITITION_MATCH, longOpt = LogCommandLineOptions.ADDITITION_MATCH_LONG,
            description = LogCommandLineOptions.ADDITITION_MATCH_DESC)
    public String additionalMatch;

    public String getAdditionalMatch() {
        return additionalMatch;
    }

    public void setAdditionalMatch(String additionalMatch) {
        this.additionalMatch = additionalMatch;
    }

    public String getLogFiles() {
        return logFiles;
    }

    public void setLogFiles(String logFiles) {
        this.logFiles = logFiles;
    }

    public String getLogEncoding() {
        return logEncoding;
    }

    public void setLogEncoding(String logEncoding) {
        this.logEncoding = logEncoding;
    }

    public String getMergedDir() {
        return mergedDir;
    }

    public void setMergedDir(String mergedDir) {
        if (!ReaderUtils.isBlank(mergedDir)) {
            this.mergedDir = mergedDir;
        }
    }

    public String getMatchLength() {
        return matchLength;
    }

    public void setMatchLength(String matchLength) {
        try {
            if (Integer.parseInt(matchLength) < 0) {
                return;
            }
        } catch (Exception e) {
            return;
        }
        this.matchLength = matchLength;
    }

    public String getExcludeRegex() {
        return excludeRegex;
    }

    public void setExcludeRegex(String excludeRegex) {
        this.excludeRegex = excludeRegex;
    }

    public String getCompressLength() {
        return compressLength;
    }

    public void setCompressLength(String compressLength) {
        try {
            if (Integer.parseInt(compressLength) < 0) {
                return;
            }
        } catch (Exception e) {
            return;
        }
        this.compressLength = compressLength;
    }

    public String getCaptureExcel() {
        return captureExcel;
    }

    public void setCaptureExcel(String captureExcel) {
        this.captureExcel = captureExcel;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public void addOriginalArgValue(String name, String value) {
        originalArgValueMap.put(name, value);
    }

    public String removeOriginalArgValue(String name) {
        return originalArgValueMap.remove(name);
    }

    public Map<String, String> getOriginalArgValueMap() {
        return originalArgValueMap;
    }

    public void setOriginalArgValueMap(Map<String, String> originalArgValueMap) {
        this.originalArgValueMap = originalArgValueMap;
    }

    public LogCommandLineRuntime newLogCommandLineRuntime() {
        initLogCommandLine();
        validateAndCorrect();

        LogCommandLineRuntime lineRuntime = new LogCommandLineRuntime(this);
        lineRuntime.setLogType(logType);
        lineRuntime.setLogEncoding(logEncoding);
        lineRuntime.setMergedDir(mergedDir);
        lineRuntime.setMatchLength(Integer.parseInt(matchLength));
        lineRuntime.setExcludeRegex(excludeRegex);
        lineRuntime.setCompressLength(Integer.parseInt(compressLength));
        lineRuntime.setCaptureExcel(Boolean.parseBoolean(captureExcel));
        List<String> logFilesList = Arrays.asList(logFiles.split(FILENAME_DELIMITER));
        lineRuntime.setLogFiles(logFilesList);
        lineRuntime.setAdditionalMatch(additionalMatch);
        return lineRuntime;
    }

    private void initLogCommandLine() {
        if (originalArgValueMap.isEmpty()) {
            return;
        }

        for (Field field : this.getClass().getFields()) {
            LogOption[] optionAnno = field.getAnnotationsByType(LogOption.class);
            if (optionAnno == null || optionAnno.length == 0) {
                continue;
            }

            String longOpt = optionAnno[0].longOpt();
            String value = originalArgValueMap.get(longOpt);
            if (!ReaderUtils.isBlank(value)) {
                ReflectionUtils.setFieldValue(this, field, value);
            }
        }
    }

    private void validateAndCorrect() {
        for (Field field : this.getClass().getFields()) {
            String fieldValue = ReflectionUtils.getFieldValue(this, field);
            if (!ReaderUtils.isBlank(fieldValue)) {
                continue;
            }

            LogOption[] optionAnno = field.getAnnotationsByType(LogOption.class);
            if (optionAnno == null || optionAnno.length == 0) {
                continue;
            }

            String defaultArg = optionAnno[0].defaultArg();
            ReflectionUtils.setFieldValue(this, field, defaultArg);
        }
    }

}
