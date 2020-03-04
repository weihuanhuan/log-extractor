package util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import parser.ParserException;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class ReaderUtils {

    public static InputStreamReader getInputStreamReader(String fname, String encoding) throws Exception {
        InputStreamReader reader;
        InputStream in = new FileInputStream(fname);
        try {
            reader = new InputStreamReader(in, encoding);
        } catch (UnsupportedEncodingException ex) {
            throw new ParserException(ex);
        }
        return reader;
    }

    public static boolean isBlank(String string) {
        return (string == null || string.isEmpty());
    }
}
