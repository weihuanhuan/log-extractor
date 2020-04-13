package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import result.Result;
import writer.excel.XLSWriter;
import writer.text.TextWriter;
import writer.pic.PictureWriter;

/**
 * Created by JasonFitch on 4/13/2020.
 */
public class ResultUtils {

    public static void writeResults(List<Result> results, String outDir, int matchLengthInt) throws IOException {
        writeResults(results, outDir, matchLengthInt, Boolean.parseBoolean(Constants.DEFAULT_CAPTURE_EXCEL));
    }

    public static void writeResults(List<Result> results, String outDir, int matchLengthInt, boolean captureExcelBool) throws IOException {
        List<File> resultFiles = new ArrayList<>();

        for (Result result : results) {
            //处理在前的文件，如果存在就删除并新建，否则直接新建
            String fileNamePath = result.getFileNamePath();
            File resultDir = FileNameHelper.getResultDir(fileNamePath);

            //按时间生成文件名后缀
            String timestamp = FileNameHelper.getTimestamp(new Date());

            //写入提取的异常信息
            File textFile = FileNameHelper.getTextFile(resultDir, timestamp);
            TextWriter.write(result, textFile);
            System.out.println("Text file:    " + textFile.getCanonicalPath());

            //写入统计的Excel
            File excelFile = FileNameHelper.getExcelFile(resultDir, timestamp);
            XLSWriter.write(result, excelFile, matchLengthInt);
            System.out.println("Excel file:   " + excelFile.getCanonicalPath());

            //转换excel为图片
            if (captureExcelBool) {
                File picFile = FileNameHelper.getPicFile(resultDir, timestamp);
                PictureWriter.writeExcel(excelFile, picFile);
                System.out.println("Picture file: " + picFile.getCanonicalPath());
            }

            if (excelFile != null) {
                resultFiles.add(excelFile);
            }
            System.out.println();
        }

        boolean merge = true;
        if (!merge || resultFiles.isEmpty()) {
            return;
        }

        //merge each excel to one.
        String timestamp = FileNameHelper.getTimestamp(new Date());
        File file = FileNameHelper.getMergedExcelFile(new File(outDir), timestamp);
        XLSWriter.allToOne(resultFiles, file);
        System.out.println("Merged excel file: " + file.getCanonicalPath());
    }
}
