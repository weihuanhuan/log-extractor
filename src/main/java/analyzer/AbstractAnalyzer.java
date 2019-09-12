package analyzer;

import interceptor.Interceptor;
import result.Result;
import parser.LogParser;
import parser.ParserException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractAnalyzer implements Analyzer {

    private List<String> logFileList;
    protected String logEncoding;
    protected int matchLength;

    private LogParser logParser;
    private List<Interceptor> interceptors = new LinkedList<>();

    protected List<Result> results = new LinkedList<>();

    public AbstractAnalyzer(List<String> logFileList, String logEncoding, int matchLength) {
        this.logFileList = logFileList;
        this.logEncoding = logEncoding;
        this.matchLength = matchLength;
    }

    abstract public void initAnalyzer(String fileCanonicalPath);

    @Override
    public List<Result> analyze() throws IOException, ParserException {

        for (String f : logFileList) {
            File file = new File(f);
            String fileCanonicalPath = file.getCanonicalPath();
            if (!file.exists()) {
                System.out.println("File " + fileCanonicalPath + " does not exist, skip it!");
                continue;
            }
            System.out.println("Process " + fileCanonicalPath);

            //初始化解析器
            this.logParser = null;
            this.interceptors.clear();
            initAnalyzer(fileCanonicalPath);
            if (logParser == null) {
                throw new ParserException("The parser is not initialized, stop parser !");
            }

            //设置输出文件
            logParser.addLogFile(file);

            //添加处理逻辑
            for (Interceptor interceptor : interceptors) {
                logParser.setHandler(interceptor);
            }

            //处理
            logParser.parse();

            //取回结果
            for (Interceptor interceptor : interceptors) {
                Result result = interceptor.getResult();
                if (result != null) {
                    results.add(result);
                }
            }
        }
        return results;
    }

    @Override
    public void addInterceptors(Interceptor interceptor) {
        if (interceptor != null) {
            this.interceptors.add(interceptor);
        }
    }

    @Override
    public void setLogParser(LogParser logParser) {
        this.logParser = logParser;
    }
}
