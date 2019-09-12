package reader;


import parser.ParserException;

/**
 * Created by JasonFitch on 9/11/2019.
 */
public interface FileReader {

    void addFile(String fname);

    void parseFile(String fname, String encoding) throws ParserException;

    boolean hasMoreInput() throws ParserException;
}
