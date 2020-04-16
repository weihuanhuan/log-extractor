package writer.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.FileNameHelper;

/**
 * Created by JasonFitch on 4/13/2020.
 */
public class XLSMerger {

    public static void merge(List<File> resultFiles, File mergeFile) throws IOException {
        XSSFWorkbook mergedExcel = new XSSFWorkbook();

        try {
            // 遍历每个源excel文件，resultFiles为源文件的名称集合
            for (File fromExcelName : resultFiles) {
                InputStream inputStream = new FileInputStream(fromExcelName);
                XSSFWorkbook fromExcel = new XSSFWorkbook(inputStream);

                int length = fromExcel.getNumberOfSheets();
                for (int i = 0; i < length; i++) {
                    // 遍历每个sheet
                    XSSFSheet fromSheet = fromExcel.getSheetAt(i);
                    String fromSheetName = fromSheet.getSheetName();

                    // excel中的每一个 sheetname 必须是唯一的。
                    fromSheetName = avoidSheetNameConflict(mergedExcel, fromSheetName);

                    XSSFSheet newSheet = mergedExcel.createSheet(fromSheetName);
                    copySheet(mergedExcel, fromSheet, newSheet);
                }

                fromExcel.close();
                inputStream.close();
            }

            //写入到新的excel文件
            FileOutputStream fileOutputStream = new FileOutputStream(mergeFile);
            mergedExcel.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static String avoidSheetNameConflict(XSSFWorkbook mergedExcel, String fromSheetName) {
        String avoidConflictName = fromSheetName;
        while (mergedExcel.getSheet(avoidConflictName) != null) {
            String timestamp = FileNameHelper.getTimestamp(new Date());
            avoidConflictName = fromSheetName + "-" + timestamp;
        }
        return avoidConflictName;
    }

    /**
     * Sheet复制
     *
     * @param wb
     * @param fromSheet
     * @param toSheet
     */
    public static void copySheet(XSSFWorkbook wb, XSSFSheet fromSheet, XSSFSheet toSheet) {
        // 处理合并的单元格区域
        mergeSheetAllRegion(fromSheet, toSheet);

        // 设置列宽
        int length = fromSheet.getRow(fromSheet.getFirstRowNum()).getLastCellNum();
        for (int i = 0; i <= length; i++) {
            toSheet.setColumnWidth(i, fromSheet.getColumnWidth(i));
        }

        // 构建每一行
        for (Iterator rowIt = fromSheet.rowIterator(); rowIt.hasNext(); ) {
            XSSFRow oldRow = (XSSFRow) rowIt.next();
            XSSFRow newRow = toSheet.createRow(oldRow.getRowNum());
            copyRow(wb, oldRow, newRow);
        }
    }

    /**
     * 行复制功能
     *
     * @param wb
     * @param oldRow
     * @param toRow
     */
    public static void copyRow(XSSFWorkbook wb, XSSFRow oldRow, XSSFRow toRow) {
        toRow.setHeight(oldRow.getHeight());

        for (Iterator cellIt = oldRow.cellIterator(); cellIt.hasNext(); ) {
            XSSFCell tmpCell = (XSSFCell) cellIt.next();
            XSSFCell newCell = toRow.createCell(tmpCell.getColumnIndex());
            copyCell(wb, tmpCell, newCell);
        }
    }

    /**
     * 复制单元格
     *
     * @param wb
     * @param fromCell
     * @param toCell
     */
    public static void copyCell(XSSFWorkbook wb, XSSFCell fromCell, XSSFCell toCell) {
        // 单元格样式
        XSSFCellStyle newstyle = wb.createCellStyle();
        copyCellStyle(fromCell.getCellStyle(), newstyle);
        toCell.setCellStyle(newstyle);

        // 单元格注释
        if (fromCell.getCellComment() != null) {
            toCell.setCellComment(fromCell.getCellComment());
        }

        // 不同数据类型处理
        CellType fromCellType = fromCell.getCellType();
        toCell.setCellType(fromCellType);
        if (CellType.NUMERIC.equals(fromCellType)) {
            if (XSSFDateUtil.isCellDateFormatted(fromCell)) {
                toCell.setCellValue(fromCell.getDateCellValue());
            } else {
                toCell.setCellValue(fromCell.getNumericCellValue());
            }
        } else if (CellType.STRING.equals(fromCellType)) {
            toCell.setCellValue(fromCell.getRichStringCellValue());
        } else if (CellType.BLANK.equals(fromCellType)) {
            // nothing21
        } else if (CellType.BOOLEAN.equals(fromCellType)) {
            toCell.setCellValue(fromCell.getBooleanCellValue());
        } else if (CellType.ERROR.equals(fromCellType)) {
            toCell.setCellErrorValue(fromCell.getErrorCellValue());
        } else if (CellType.FORMULA.equals(fromCellType)) {
            toCell.setCellFormula(fromCell.getCellFormula());
        } else {
            // nothing29
        }

    }


    public static void copyCellStyle(XSSFCellStyle fromStyle, XSSFCellStyle toStyle) {
        // 此一行代码搞定
        toStyle.cloneStyleFrom(fromStyle);
    }

    /**
     * 合并单元格
     *
     * @param fromSheet
     * @param toSheet
     */
    public static void mergeSheetAllRegion(XSSFSheet fromSheet, XSSFSheet toSheet) {
        int num = fromSheet.getNumMergedRegions();
        CellRangeAddress cellR = null;
        for (int i = 0; i < num; i++) {
            cellR = fromSheet.getMergedRegion(i);
            toSheet.addMergedRegion(cellR);
        }
    }

    public class XSSFDateUtil extends DateUtil {

    }
}
