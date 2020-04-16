package bootstrap;

import java.util.List;

/**
 * Created by JasonFitch on 4/16/2020.
 */
public class LogCommandLineRuntime {

    private LogCommandLine originalCommandline;

    public List<String> logFiles;

    public String logEncoding;

    public String mergedDir;

    public Integer matchLength;

    public String excludeRegex;

    public Integer compressLength;

    public boolean captureExcel;

    public String logType;

    public String additionalMatch;

    public LogCommandLineRuntime(LogCommandLine originalCommandline) {
        this.originalCommandline = originalCommandline;
    }

    public String getAdditionalMatch() {
        return additionalMatch;
    }

    public void setAdditionalMatch(String additionalMatch) {
        this.additionalMatch = additionalMatch;
    }

    public List<String> getLogFiles() {
        return logFiles;
    }

    public void setLogFiles(List<String> logFiles) {
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
        this.mergedDir = mergedDir;
    }

    public Integer getMatchLength() {
        return matchLength;
    }

    public void setMatchLength(Integer matchLength) {
        this.matchLength = matchLength;
    }

    public String getExcludeRegex() {
        return excludeRegex;
    }

    public void setExcludeRegex(String excludeRegex) {
        this.excludeRegex = excludeRegex;
    }

    public Integer getCompressLength() {
        return compressLength;
    }

    public void setCompressLength(Integer compressLength) {
        this.compressLength = compressLength;
    }

    public boolean getCaptureExcel() {
        return captureExcel;
    }

    public void setCaptureExcel(boolean captureExcel) {
        this.captureExcel = captureExcel;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public LogCommandLine getOriginalCommandline() {
        return originalCommandline;
    }

    public void setOriginalCommandline(LogCommandLine originalCommandline) {
        this.originalCommandline = originalCommandline;
    }
}
