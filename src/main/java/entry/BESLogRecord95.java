package entry;

/**
 * Created by JasonFitch on 9/7/2019.
 */
public class BESLogRecord95 extends BESLogRecord {

    public static final String prefix = "##|";
    public static final String split = "|";
    public static final String suffix = "|##\n";

    @Override
    public String getPrefix() {
        return BESLogRecord95.prefix;
    }

    @Override
    public String getSplit() {
        return BESLogRecord95.split;
    }

    @Override
    public String getSuffix() {
        return BESLogRecord95.suffix;
    }
}
