package writer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import result.Result;

/**
 * Created by JasonFitch on 9/12/2019.
 */
public class FileUtils {

    public static void writeResults(List<Result> results, String outDir, int matchLengthInt) throws IOException {

        for (Result result : results) {
            //处理在前的文件，如果存在就删除并新建，否则直接新建
            String fileNamePath = result.getFileNamePath();
            File tempDir = new File(fileNamePath + "-result");
            if (tempDir.exists()) {
                FileUtils.deleteFile(tempDir);
            }
            tempDir.mkdirs();

            //写入提取的异常信息
            TextWriter.write(result, tempDir);

            //写入统计的Excel
            XLSWriter.write(result, tempDir, matchLengthInt);
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
}
