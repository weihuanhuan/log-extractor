package util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import result.Result;
import writer.text.TextWriter;
import writer.excel.XLSWriter;

/**
 * Created by JasonFitch on 9/12/2019.
 */
public class FileUtils {

    public static void writeResults(List<Result> results, String outDir, int matchLengthInt) throws IOException {
        writeResults(results, outDir, matchLengthInt, Boolean.parseBoolean(Constants.DEFAULT_CAPTURE_EXCEL));
    }

    public static void writeResults(List<Result> results, String outDir, int matchLengthInt, boolean captureExcelBool) throws IOException {

        for (Result result : results) {
            //处理在前的文件，如果存在就删除并新建，否则直接新建
            String fileNamePath = result.getFileNamePath();
            File tempDir = new File(fileNamePath + Constants.DEFAULT_RESULT_DIR_SUFFIX);
            if (tempDir.exists()) {
                FileUtils.deleteFile(tempDir);
            }
            tempDir.mkdirs();

            //写入提取的异常信息
            TextWriter.write(result, tempDir);

            //写入统计的Excel
            XLSWriter.write(result, tempDir, matchLengthInt, captureExcelBool);
        }
    }

    public static void deleteFile(File file) {
        if (file.isFile()) {
            file.delete();
        } else {
            String[] childFilePaths = file.list();
            for (String childFilePath : childFilePaths) {
                File childFile = new File(file.getAbsolutePath() + "\\" + childFilePath);
                deleteFile(childFile);
            }
            file.delete();
        }
    }


    public static void addFile(File file, List<String> logFiles) {
        String filePath = file.getPath();
        if (!logFiles.contains(filePath)) {
            logFiles.add(filePath);
        }
    }

    public static void recursionDir(File dir, List<String> logFiles) {
        List<File> fileList = Arrays.asList(dir.listFiles());
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (File file : fileList) {
            if (file.isFile()) {
                addFile(file, logFiles);
            } else {
                recursionDir(file, logFiles);
            }
        }
    }

    public static void filterByDirName(List<String> logFiles, String resultDirSuffixRegex) {
        Iterator<String> iterator = logFiles.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.matches(resultDirSuffixRegex)) {
                iterator.remove();
            }
        }
    }

    public static void filterByFileName(List<String> logFiles, String accessLogFileRegex) {
        Iterator<String> iterator = logFiles.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.replaceAll(".*\\\\", "").matches(accessLogFileRegex)) {
                iterator.remove();
            }
        }
    }
}
