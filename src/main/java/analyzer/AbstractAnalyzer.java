package analyzer;

import bootstrap.LogCommandLineRuntime;
import executor.TaskExecutor;
import interceptor.Interceptor;
import parser.LogParser;
import parser.ParserException;
import task.AnalysisTask;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public abstract class AbstractAnalyzer implements Analyzer {

    protected LogCommandLineRuntime lineRuntime;

    private LogParser logParser;

    private List<Interceptor> interceptors = new LinkedList<>();

    private TaskExecutor taskExecutor;

    public AbstractAnalyzer(LogCommandLineRuntime lineRuntime) {
        this.lineRuntime = lineRuntime;
        this.taskExecutor = new TaskExecutor();
    }

    abstract public void initAnalyzer(String fileCanonicalPath);

    @Override
    public void analyze() throws IOException, ParserException {
        taskExecutor.init(lineRuntime.getMatchLength(), lineRuntime.getCaptureExcel(), lineRuntime.getMergedDir());

        for (String f : lineRuntime.getLogFiles()) {
            File file = new File(f);
            String fileCanonicalPath = file.getCanonicalPath();
            if (!file.exists()) {
                System.out.println("File " + fileCanonicalPath + " does not exist, skip it!");
                continue;
            }

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

            AnalysisTask analysisTask = new AnalysisTask(fileCanonicalPath, logParser,
                    interceptors.toArray(new Interceptor[interceptors.size()]));
            taskExecutor.submit(analysisTask);
        }

        try {
            taskExecutor.waitFinish();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
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
