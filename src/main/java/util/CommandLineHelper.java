package util;

import bootstrap.LogAnalyzerMain;
import bootstrap.LogCommandLine;
import bootstrap.LogCommandLineRuntime;
import bootstrap.LogOption;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Created by JasonFitch on 4/16/2020.
 */
public class CommandLineHelper {

    private static Class commandLineClass = LogCommandLine.class;

    private static Class mainClass = LogAnalyzerMain.class;

    public static LogCommandLineRuntime buildCommandLineRuntime() {
        return buildCommandLineRuntime(new String[0]);
    }

    public static LogCommandLineRuntime buildCommandLineRuntime(String[] args) {
        Options options = buildCommandLineOptions();
        CommandLine commandLine = parserCommandLineArgs(options, args);
        LogCommandLineRuntime lineRuntime = buildCommandLineRuntime(commandLine);
        return lineRuntime;
    }

    public static LogCommandLineRuntime buildCommandLineRuntime(CommandLine commandLine) {
        LogCommandLine logCommandLine = new LogCommandLine();

        Option[] options = commandLine.getOptions();
        for (Option option : options) {
            String longOpt = option.getLongOpt();
            String value = option.getValue();
            logCommandLine.addOriginalArgValue(longOpt, value);
        }

        LogCommandLineRuntime logCommandLineRuntime = logCommandLine.newLogCommandLineRuntime();
        return logCommandLineRuntime;
    }

    //设置解析器所用的参数
    public static Options buildCommandLineOptions() {
        Options options = new Options();
        for (Field field : commandLineClass.getFields()) {
            Option option = buildOneOption(field);
            if (option != null)
                options.addOption(option);
        }
        return options;
    }

    //处理可执行文件的入参
    public static CommandLine parserCommandLineArgs(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(mainClass.getSimpleName(), options);
            System.exit(1);
        }
        return cmd;
    }

    private static Option buildOneOption(Field field) {
        LogOption[] optionAnno = field.getAnnotationsByType(LogOption.class);
        if (optionAnno == null || optionAnno.length == 0) {
            return null;
        }

        String opt = optionAnno[0].opt();
        String longOpt = optionAnno[0].longOpt();
        boolean hasArg = optionAnno[0].hasArg();
        String description = optionAnno[0].description();
        boolean require = optionAnno[0].require();

        Option option = new Option(opt, longOpt, hasArg, description);
        option.setRequired(require);
        return option;
    }

    //打印参数信息
    public static void printArgs(LogCommandLineRuntime commandLineRuntime) {
        String logType = commandLineRuntime.getLogType();
        String logEncoding = commandLineRuntime.getLogEncoding();
        String mergedDir = commandLineRuntime.getMergedDir();
        Integer matchLength = commandLineRuntime.getMatchLength();
        String excludeRegex = commandLineRuntime.getExcludeRegex();
        Integer compressLength = commandLineRuntime.getCompressLength();
        boolean captureExcel = commandLineRuntime.getCaptureExcel();
        List<String> logFileList = commandLineRuntime.getLogFiles();
        String additionalMatch = commandLineRuntime.getAdditionalMatch();

        Path toAbsolutePath = Paths.get(mergedDir).normalize().toAbsolutePath();
        System.out.println("Analyzer runtime arguments info:");
        System.out.println("log-type       : " + logType);
        System.out.println("log-encoding   : " + logEncoding);
        System.out.println("merged-dir     : " + toAbsolutePath);
        System.out.println("match-length   : " + matchLength);
        System.out.println("exclude-regex  : " + excludeRegex);
        System.out.println("compress-length: " + compressLength);
        System.out.println("capture-excel  : " + captureExcel);
        System.out.println("addition-match : " + additionalMatch);

        System.out.println();
        System.out.println("Target files be processed:");
        for (String f : logFileList) {
            Path file = Paths.get(f);
            System.out.println(file.normalize().toAbsolutePath());
        }

        System.out.println();
    }
}
