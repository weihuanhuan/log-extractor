package analyzer;

import interceptor.Interceptor;
import parser.LogParser;
import parser.ParserException;

import java.io.IOException;

public interface Analyzer {

    void analyze() throws IOException, ParserException;

    void addInterceptors(Interceptor interceptor);

    void setLogParser(LogParser logParser);
}
