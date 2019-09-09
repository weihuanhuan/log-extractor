package parser;

import java.io.File;

import interceptor.Interceptor;
import reader.FilesReader;

/**
 * Created by JasonFitch on 9/7/2019.
 */
abstract public class AbstractLogParser implements LogParser {

    protected Interceptor handler;

    protected FilesReader reader;

    public AbstractLogParser() {
        reader = new FilesReader();
    }

    public AbstractLogParser(String encoding) {
        reader = new FilesReader(encoding);
    }

    @Override
    abstract public void parse();

    @Override
    public void addLogFile(File f) {
        reader.addFile(f.getAbsolutePath());
    }

    @Override
    public void setHandler(Interceptor handler) {
        if (this.handler != null && this.handler != handler) {
            Interceptor current = this.handler;
            Interceptor next = current.getNext();
            while (next != null) {
                current = next;
                next = current.getNext();
            }
            current.setNext(handler);
        } else {
            this.handler = handler;
        }
    }

}
