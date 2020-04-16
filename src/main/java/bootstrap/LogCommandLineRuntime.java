package bootstrap;

import java.nio.file.Path;
import java.nio.file.Paths;
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

    //打印参数信息
    public void printRuntimeArgs() {
        Path toAbsolutePath = Paths.get(mergedDir).normalize().toAbsolutePath();
        System.out.println("Analyzer runtime arguments info:");
        System.out.println("log-type       : " + logType);
        System.out.println("log-encoding   : " + logEncoding);
        System.out.println("merged-dir     : " + toAbsolutePath);
        System.out.println("match-length   : " + matchLength);
        System.out.println("exclude-regex  : " + excludeRegex);
        System.out.println("compress-length: " + compressLength);
        System.out.println("capture-excel  : " + captureExcel);
        System.out.println("addition-match : " + additionalMatch);

        System.out.println();
        System.out.println("Target files be processed:");
        for (String f : logFiles) {
            Path file = Paths.get(f);
            System.out.println(file.normalize().toAbsolutePath());
        }
        System.out.println();
    }
}
