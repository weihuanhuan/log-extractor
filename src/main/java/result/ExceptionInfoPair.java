package result;

/**
 * Created by JasonFitch on 3/4/2020.
 */
public class ExceptionInfoPair {

    private String key;
    private String info;

    public ExceptionInfoPair(String key, String info) {
        this.key = key;
        this.info = info;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExceptionInfoPair infoPair = (ExceptionInfoPair) o;

        if (key != null ? !key.equals(infoPair.key) : infoPair.key != null) return false;
        return info != null ? info.equals(infoPair.info) : infoPair.info == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("key");
        stringBuilder.append("=");
        stringBuilder.append("\"");
        stringBuilder.append(key);
        stringBuilder.append("\"");
        stringBuilder.append(",");

        stringBuilder.append("info");
        stringBuilder.append("=");
        stringBuilder.append("\"");
        stringBuilder.append(info);
        stringBuilder.append("\"");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
