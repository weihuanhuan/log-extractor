package result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Accumulator {

    Map<String, Integer> map = new HashMap<>();

    //使用map统计重复的异常数量
    public boolean count(String key) {
        Integer orDefault = map.getOrDefault(key, new Integer(0));
        map.put(key, ++orDefault);
        return true;
    }

    public List<Map.Entry<String, Integer>> getDataList() {
        return new ArrayList<>(map.entrySet());
    }

    public List<Map.Entry<String, Integer>> getSortedDataList() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
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
        for (Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            stringBuffer.append(entry.getKey().trim());
            stringBuffer.append(" = ");
            stringBuffer.append(entry.getValue());
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }
}

