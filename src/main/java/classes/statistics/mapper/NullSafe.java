package classes.statistics.mapper;

public class NullSafe {
    public static String safeString(String value) {
        return value != null ? value : "";
    }

    public static String safeString(Object value) {
        return value != null ? value.toString() : "";
    }

    public static <T> T safeObject(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}
