package reader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Queue;
import parser.ParserException;
import util.Constants;

/**
 * Created by JasonFitch on 9/11/2019.
 */
public class RandomFileReader implements Reader {

    private int lineNo;
    private boolean nextAddLine = true;

    private String currentFilePath;
    private RandomAccessFile randomAccessFile;

    private String encoding;
    private Queue<File> logFiles = new LinkedList<>();

    public RandomFileReader() {
        this.encoding = Constants.DEFAULT_LOG_ENCODING;
    }

    public RandomFileReader(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public void addFile(String fname) {
        logFiles.offer(new File(fname));
    }

    public void parseFile(String fname, String encoding) throws ParserException {
        try {
            randomAccessFile = new RandomAccessFile(fname, "r");
            System.out.println("###################################" + currentFilePath);
            System.out.println("###################################" + randomAccessFile.length());
        } catch (Exception ex) {
            throw new ParserException(ex);
        }
    }

    public boolean hasMoreData() throws IOException {
        if (randomAccessFile == null) {
            lineNo = 0;
            nextAddLine = true;
            currentFilePath = logFiles.poll().getCanonicalPath();
            try {
                parseFile(currentFilePath, encoding);
            } catch (ParserException e) {
                return false;
            }
        }
        if (randomAccessFile.getFilePointer() >= randomAccessFile.length()) {
            return false;
        }
        return true;
    }


    public String readNextLine() throws ParserException, IOException {
        String result = "";
        if (!hasMoreData()) {
            return result;
        }

        byte[] bytes;
        try {
            String line = randomAccessFile.readLine();
            if (nextAddLine) {
                ++(lineNo);
            } else {
                nextAddLine = true;
            }

            bytes = line.getBytes(Constants.CHARSET_NAME_ISO_8859_1);
            result = new String(bytes, Constants.DEFAULT_LOG_ENCODING);
        } catch (IOException e) {
            throw e;
        }
        return result;
    }


    public String readStringBytes(int n) throws IOException, ParserException {
        String result = "";
        if (!hasMoreData()) {
            return result;
        }

        try {
            byte[] bytes = new byte[n];
            int read = randomAccessFile.read(bytes);
            result = new String(bytes, Constants.DEFAULT_LOG_ENCODING);

            String replaceAll = result.replaceAll("\\n", "");
            int countLF = result.length() - replaceAll.length();
            if (countLF != 0 && !result.endsWith("\n")) {
                ++countLF;
                nextAddLine = false;
            } else {
                nextAddLine = true;
            }

            lineNo += countLF;
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public String readStringBytes(String string) throws IOException, ParserException {
        return readStringBytes(string.getBytes(Constants.CHARSET_NAME_ISO_8859_1).length);
    }

    public String pushBackString(int n) throws IOException {
        String back = "";
        if (n <= 0 || n > randomAccessFile.getFilePointer()) {
            return back;
        }

        try {
            byte[] bytes = new byte[n];
            randomAccessFile.seek(randomAccessFile.getFilePointer() - n);
            int read = randomAccessFile.read(bytes);
            randomAccessFile.seek(randomAccessFile.getFilePointer() - n);
            back = new String(bytes, Constants.DEFAULT_LOG_ENCODING);

            String replaceAll = back.replaceAll("\\n", "");
            int countLF = back.length() - replaceAll.length();
            if (countLF != 0 && !back.endsWith("\n")) {
                ++countLF;
                nextAddLine = false;
            } else {
                nextAddLine = true;
            }

            lineNo -= countLF;
        } catch (IOException e) {
            throw e;
        }
        return back;
    }

    public String pushBackString(String str) throws IOException {
        return pushBackString(str.getBytes(Constants.CHARSET_NAME_ISO_8859_1).length);
    }

    public int getLineNo() {
        return lineNo;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public long getFilePointer() throws IOException {
        return randomAccessFile.getFilePointer();
    }

    public long getFileLength() throws IOException {
        return randomAccessFile.length();
    }
}
