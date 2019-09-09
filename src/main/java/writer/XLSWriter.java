package writer;

import org.apache.poi.ss.util.CellRangeAddress;
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

public class XLSWriter {

    static private String EXCEL_SUFFIX = ".xlsx";

    static private String EXCEL_FILENAME = "exception-statistic";


    public static void write(List<Result> results, String outDir, int matchLength) throws IOException {

        //按时间生成表格文件名后缀
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = df.format(new Date());


        //避免处理太快时后者覆盖前者数据
        String excelFilePath = outDir + "/" + EXCEL_FILENAME + "-" + date + EXCEL_SUFFIX;
        File excelFile = new File(excelFilePath);
        int avoidConflict = 0;
        while (excelFile.exists()) {
            excelFile = new File(excelFilePath.replace(EXCEL_SUFFIX, "-" + ++avoidConflict + EXCEL_SUFFIX));
        }

        //创建一个excel文件
        XSSFWorkbook wb = new XSSFWorkbook();

        for (Result result : results) {
            StatisticResult realResult = (StatisticResult) result;

            String fileNamePath = realResult.getFileNamePath();
            File file = new File(fileNamePath);

            //创建一个sheet
            XSSFSheet sheet = wb.createSheet(file.getName());


            //设置第columnIndex列的列宽，单位为字符宽度的1/256
            sheet.setColumnWidth(0, (matchLength + 20) << 8);
            sheet.setColumnWidth(1, 20 << 8);
            sheet.setAutoFilter(CellRangeAddress.valueOf("B1:B500"));

            XSSFRow headRow = sheet.createRow(0);

            //表头定义
            List<String> heads = new ArrayList<>();
            heads.add("Exception Key");
            heads.add("Occur Count");

            //填充表格头信息
            for (int i = 0; i < heads.size(); i++) {
                String value = heads.get(i);
                headRow.createCell(i).setCellValue(value);
            }

            //填充每一个数据行
            int dataIndex = 1;
            List<Map.Entry<String, Integer>> accumulator = realResult.getAccumulator().getSortedDataList();
            for (Iterator<Map.Entry<String, Integer>> it = accumulator.iterator(); it.hasNext(); ) {
                Map.Entry<String, Integer> entry = it.next();
                String key = entry.getKey();
                Integer value = entry.getValue();
                XSSFRow dataRow = sheet.createRow(dataIndex++);
                dataRow.createCell(0).setCellValue(key);
                dataRow.createCell(1).setCellValue(value);
            }
        }

        try {
            //写入到文件
            FileOutputStream outputStream = new FileOutputStream(excelFile);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            System.out.println("create excel file " + excelFile.getCanonicalPath());
        } catch (Exception e) {
            System.out.println("delete excel file " + excelFile.getCanonicalPath());
            throw e;
        }
    }

}
