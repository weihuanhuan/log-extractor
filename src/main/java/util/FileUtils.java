package util;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by JasonFitch on 9/12/2019.
 */
public class FileUtils {

    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }

        if (file.isFile()) {
            file.delete();
        }
    }

    public static void deleteFileRecursion(File file) {
        if (!file.exists()) {
            return;
        }

        if (file.isFile()) {
            file.delete();
        } else {
            String absoluteParent = file.getAbsolutePath();
            String[] childFilePaths = file.list();
            for (String childFilePath : childFilePaths) {
                File childFile = new File(absoluteParent, childFilePath);
                deleteFileRecursion(childFile);
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
