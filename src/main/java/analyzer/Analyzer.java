package analyzer;

import interceptor.Interceptor;
import parser.LogParser;
import result.Result;
import parser.ParserException;

import java.io.IOException;
import java.util.List;

public interface Analyzer {

    List<Result> analyze() throws IOException, ParserException;

    void addInterceptors(Interceptor interceptor);

    void setLogParser(LogParser logParser);
}
