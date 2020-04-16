package reader;

import bootstrap.LogCommandLineOptions;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

import parser.ParserException;
import util.ReaderUtils;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class InputStreamFileReader implements FileReader {

    private Mark current;
    private String currentFilePath;

    private String encoding;
    private Queue<File> logFiles = new LinkedList<>();

    public static int LF = '\n';

    public InputStreamFileReader() {
        this.encoding = LogCommandLineOptions.LOG_ENCODING_DEFAULT;
    }

    public InputStreamFileReader(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public void addFile(String fname) {
        logFiles.offer(new File(fname));
    }

    @Override
    public void parseFile(String fname, String encoding) throws ParserException {
        InputStreamReader reader = null;
        try {
            reader = ReaderUtils.getInputStreamReader(fname, encoding);
            CharArrayWriter caw = new CharArrayWriter();
            char[] buf = new char[1024];
            for (int i = 0; (i = reader.read(buf)) != -1; ) {
                caw.write(buf, 0, i);
            }
            caw.close();
            if (this.current == null) {
                this.current = new Mark(caw.toCharArray());
            }
        } catch (Exception ex) {
            throw new ParserException(ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception any) {
                }
            }
        }
    }

    @Override
    public boolean hasMoreInput() throws ParserException {
        try {
            if (this.current == null || this.current.cursor >= this.current.stream.length) {
                if (logFiles.isEmpty()) {
                    return false;
                } else {
                    this.current = null;

                    this.currentFilePath = logFiles.poll().getCanonicalPath();

                    parseFile(currentFilePath, encoding);
                }
            }
        } catch (IOException e) {
            throw new ParserException(e);
        }
        return true;
    }

    public void nextChars(String split) throws ParserException {
        for (int i = 0; i < split.length(); i++) {
            nextChar();
        }
    }

    public int nextChar() throws ParserException {
        if (!hasMoreInput()) {
            return -1;
        }

        int ch = this.current.stream[this.current.cursor];
        this.current.cursor += 1;

        if (ch == LF) {
            this.current.line += 1;
            this.current.col = 1;
        } else {
            this.current.col += 1;
        }
        return ch;

    }

    private int peekChar() throws ParserException {
        if (!hasMoreInput()) {
            return -1;
        }
        return this.current.stream[this.current.cursor];
    }

    public void pushBackChar() {
        this.current.cursor -= 1;
        this.current.col -= 1;
    }

    private Mark mark() {
        return new Mark(this.current);
    }

    private void reset(Mark mark) {
        this.current = new Mark(mark);
    }

    public Mark skipUntil(String limit) throws ParserException {

        int limlen = limit.length();
        int ch = -1;

        skip:
        for (mark(), ch = nextChar(); ch != -1; mark(), ch = nextChar()) {
            if (ch == limit.charAt(0)) {
                Mark restart = mark();
                for (int i = 1; i < limlen; i++) {
                    if (peekChar() == limit.charAt(i)) {
                        nextChar();
                    } else {
                        reset(restart);
                        continue skip;
                    }
                }
                return this.current;
            }
        }
        return null;
    }

    public String readTextUtil(String limit) throws ParserException {
        CharArrayWriter ttext = new CharArrayWriter();
        int limlen = limit.length();
        try {

            skip:
            while (hasMoreInput()) {

                Mark textLast = mark();
                int ch = nextChar();
                if (ch == limit.charAt(0)) {
                    Mark restart = mark();
                    for (int i = 1; ; i++) {
                        if (i >= limlen) {
                            reset(textLast);
                            break skip;
                        }
                        if (peekChar() == limit.charAt(i)) {
                            nextChar();
                        } else {
                            reset(restart);
                            ttext.write(ch);
                            break;
                        }
                    }
                } else {
                    ttext.write(ch);
                }
            }
            return new String(ttext.toCharArray());
        } catch (Exception e) {
            throw new ParserException(e);
        }
    }

    public Mark getCurrent() {
        return current;
    }

    public void setCurrent(Mark current) {
        this.current = current;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }


}
