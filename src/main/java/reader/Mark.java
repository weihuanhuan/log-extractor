package reader;

import java.util.Arrays;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class Mark {

    public int cursor;
    public int line;
    public int col;
    public char[] stream;

    public Mark(char[] inStream) {
        this.stream = inStream;
        this.cursor = 0;
        this.line = 1;
        this.col = 1;
    }

    public Mark(Mark other) {
        this.stream = other.stream;
        this.cursor = other.cursor;
        this.line = other.line;
        this.col = other.col;
    }

    @Override
    public int hashCode() {
        return (this.line * 10000 + this.col * 1000 + this.cursor) % 500;
    }

    @Override
    public String toString() {
        return "reader.Mark{" + "cursor=" + cursor + ", line=" + line + ", col=" + col + ", stream=" + Arrays.toString(stream) + '}';
    }
}
