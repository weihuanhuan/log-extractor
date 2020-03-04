package writer;

import result.ExceptionInfoPair;
import result.Result;
import result.StatisticResult;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import util.FileUtils;

public class XLSWriter {

    static private int EXCEL_MAX_COLUMN_WIDTH = 255;
    static private String EXCEL_SUFFIX = ".xlsx";

    static private String EXCEL_FILENAME = "exception-statistic";

    public static void write(Result result, File outDir, int matchLength) throws IOException {

        StatisticResult realResult = (StatisticResult) result;
        String fileNamePath = realResult.getFileNamePath();
        File file = new File(fileNamePath);

        //创建一个excel文件
        XSSFWorkbook wb = new XSSFWorkbook();

        //创建一个sheet
        XSSFSheet sheet = wb.createSheet(file.getName());

        //设置第columnIndex列的列宽，单位为字符宽度的1/256,
        // 注意 Excel 的最大列宽255
        int exceptionWidth = 64;
        int infoWidth = matchLength + 32;
        int countWidth = 8;
        sheet.setColumnWidth(0, exceptionWidth << 8);
        if (infoWidth > EXCEL_MAX_COLUMN_WIDTH) {
            sheet.setColumnWidth(1, EXCEL_MAX_COLUMN_WIDTH << 8);
        } else {
            sheet.setColumnWidth(1, infoWidth << 8);
        }
        sheet.setColumnWidth(2, countWidth << 8);

        //表头定义
        XSSFRow headRow = sheet.createRow(0);
        List<String> heads = new ArrayList<>();
        heads.add("Exception");
        heads.add("Info");
        heads.add("Count");

        //填充表格头信息
        for (int i = 0; i < heads.size(); i++) {
            String value = heads.get(i);
            headRow.createCell(i).setCellValue(value);
        }

        //填充每一个数据行
        int dataIndex = 1;
        List<Map.Entry<ExceptionInfoPair, Integer>> accumulator = realResult.getAccumulator().getSortedDataList();
        for (Iterator<Map.Entry<ExceptionInfoPair, Integer>> it = accumulator.iterator(); it.hasNext(); ) {
            Map.Entry<ExceptionInfoPair, Integer> entry = it.next();
            ExceptionInfoPair infoPair = entry.getKey();
            Integer value = entry.getValue();
            XSSFRow dataRow = sheet.createRow(dataIndex++);
            dataRow.createCell(0).setCellValue(infoPair.getKey());
            dataRow.createCell(1).setCellValue(infoPair.getInfo());
            dataRow.createCell(2).setCellValue(value);
        }

        //按时间生成表格文件名后缀
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String date = df.format(new Date());

        //如果存在，删除旧的excel文件
        File excelFile = new File(outDir, EXCEL_FILENAME + "-" + date + EXCEL_SUFFIX);
        if (excelFile.exists()) {
            FileUtils.deleteFile(excelFile);
        }

        try {
            //写入到文件
            FileOutputStream outputStream = new FileOutputStream(excelFile);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            System.out.println("Staticics file: " + excelFile.getCanonicalPath());
        } catch (Exception e) {
            throw e;
        }

    }

}
