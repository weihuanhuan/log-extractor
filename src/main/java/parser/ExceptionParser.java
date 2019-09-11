package parser;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import reader.RandomFileReader;
import util.Constants;

/**
 * Created by JasonFitch on 9/11/2019.
 */
public class ExceptionParser extends AbstractLogParser implements LogParser {


    public RandomFileReader randomFileReader;


    public static void main(String[] args) throws IOException {

        ExceptionParser exceptionParser = new ExceptionParser();

        String s = "./0906/guanli/server.log";
//        File file = new File();
//        System.out.println(file.getCanonicalPath());
        exceptionParser.randomFileReader.addFile(s);
        exceptionParser.parse();

    }


    public ExceptionParser() {
        randomFileReader = new RandomFileReader();
    }

    public ExceptionParser(String encoding) {
        randomFileReader = new RandomFileReader(encoding);
    }

    @Override
    public void parse() {


        Map<String, Integer> results = new LinkedHashMap<>();

        try {
            while (randomFileReader.hasMoreData()) {
                String back;
                String currentLine = randomFileReader.readNextLine();

//                System.out.println(currentLine);
//                System.out.println(randomFileReader.getLineNo());

                String result;
                int suffixMatchLength = 200;
                int exception = currentLine.indexOf("Exception: ");
                boolean atLine = currentLine.matches("^\\tat ");
                if (exception > -1 && !atLine) {


                    int prefixMatchLength = 100;
                    int realPrefixMatchLength = prefixMatchLength;

                    int Ex = currentLine.getBytes().length - exception;
                    randomFileReader.pushBackString(Ex);

                    int Sx = currentLine.getBytes(Constants.CHARSET_NAME_ISO_8859_1).length - Ex;
                    int difference = Sx - prefixMatchLength;
                    if (difference <= 0) {
                        realPrefixMatchLength = Sx;
                    }
                    back = randomFileReader.pushBackString(realPrefixMatchLength);
                    int lineNo = randomFileReader.getLineNo();
                    randomFileReader.readStringBytes(realPrefixMatchLength);

                    int remain = currentLine.getBytes(Constants.CHARSET_NAME_ISO_8859_1).length - exception;
                    if (remain >= suffixMatchLength) {
                        result = currentLine.substring(exception, exception + suffixMatchLength);
                    } else {
                        result = currentLine.substring(exception) + randomFileReader.readStringBytes(suffixMatchLength - remain);
                    }
                    int i = result.indexOf("\tat ");
                    if (i > -1) {
                        result = result.substring(0, i);
                    }
                    results.put("\n" + back + result, lineNo);
                }


//                back = randomFileReader.pushBackString(5);
//                System.out.println(back);
//                System.out.println(randomFileReader.getLineNo());
//
//                next = randomFileReader.readStringBytes(20);
//                System.out.println(next);
//                System.out.println(randomFileReader.getLineNo());

//                System.out.println("-------------------------");
            }
            System.out.println("##################################");

            for (Map.Entry<String, Integer> entry : results.entrySet()) {
                System.out.println(entry.getValue() + " ##### " + entry.getKey());
                System.out.println();
            }

        } catch (ParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
