package parser;

import interceptor.Interceptor;

import java.io.File;

public interface LogParser {

    void parse();

    void addLogFile(File file);

    void setHandler(Interceptor interceptor);
}
