package util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import writer.excel.XLSWriter;
import writer.pic.PictureWriter;

/**
 * Created by JasonFitch on 4/13/2020.
 */
public class FileNameHelper {

    public static File getExcelFile(File outDir, String date) {
        //如果存在，删除旧的excel文件
        File excelFile = new File(outDir, Constants.DEFAULT_FILENAME + "-" + date + XLSWriter.EXCEL_SUFFIX);
        checkResultFile(excelFile);
        return excelFile;
    }

    public static File getMergedExcelFile(File outDir, String date) {
        //如果存在，删除旧的excel文件
        File excelFile = new File(outDir, Constants.DEFAULT_MERGE_FILENAME + "-" + date + XLSWriter.EXCEL_SUFFIX);
        checkResultFile(excelFile);
        return excelFile;
    }

    public static File getPicFile(File outDir, String date) {
        //如果存在，删除旧的pic文件
        File picFile = new File(outDir, Constants.DEFAULT_FILENAME + "-" + date + PictureWriter.PIC_SUFFIX);
        checkResultFile(picFile);
        return picFile;
    }

    public static void checkResultFile(File file) {
        if (file.exists()) {
            FileUtils.deleteFile(file);
        }
    }

    public static String getTimestamp(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String dateStr = df.format(date);
        return dateStr;
    }

    public static File getResultDir(String fileNamePath) {
        File resultDir = new File(fileNamePath + Constants.DEFAULT_RESULT_DIR_SUFFIX);
        if (resultDir.exists()) {
            FileUtils.deleteFileRecursion(resultDir);
        }
        resultDir.mkdirs();

        return resultDir;
    }

}
