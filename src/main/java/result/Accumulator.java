package result;

import util.ReaderUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Accumulator {

    private String regex;

    private Map<ExceptionInfoPair, Integer> map = new HashMap<>();

    public Accumulator(int compressDigitalLength) {
        if (compressDigitalLength > 0) {
            regex = "\\d{" + compressDigitalLength + ",}";
        }
    }

    //使用map统计重复的异常数量
    public boolean count(String key) {
        return count(key, null);
    }

    public boolean count(String key, String info) {
        String countableInfo = info;
        if (regex != null && !ReaderUtils.isBlank(info)) {
            countableInfo = info.replaceAll(regex, " ###COMPRESSED_DIGITAL### ");
        }

        ExceptionInfoPair infoPair = new ExceptionInfoPair(key, countableInfo);
        Integer orDefault = map.getOrDefault(infoPair, new Integer(0));
        map.put(infoPair, ++orDefault);
        return true;
    }

    public List<Map.Entry<ExceptionInfoPair, Integer>> getDataList() {
        return new ArrayList<>(map.entrySet());
    }

    public List<Map.Entry<ExceptionInfoPair, Integer>> getSortedDataList() {
        List<Map.Entry<ExceptionInfoPair, Integer>> list = new ArrayList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<ExceptionInfoPair, Integer>>() {
            public int compare(Map.Entry<ExceptionInfoPair, Integer> e1, Map.Entry<ExceptionInfoPair, Integer> e2) {
                //降序排序
                return e2.getValue().compareTo(e1.getValue());
            }
        });
        return list;
    }

    //格式化输出用于快速打印结果
    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (Iterator<Map.Entry<ExceptionInfoPair, Integer>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<ExceptionInfoPair, Integer> entry = it.next();
            stringBuffer.append(entry.getKey());
            stringBuffer.append(" = ");
            stringBuffer.append(entry.getValue());
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }


}

