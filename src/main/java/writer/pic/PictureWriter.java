package writer.pic;

import java.io.File;

/**
 * Created by JasonFitch on 4/13/2020.
 */
public class PictureWriter {

    public static String PIC_SUFFIX = ".png";

    public static void writeExcel(File excelFile, File picFile) {
        try {
            XLSUtils.toImage(excelFile, picFile);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
