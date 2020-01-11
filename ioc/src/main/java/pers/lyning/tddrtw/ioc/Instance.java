package pers.lyning.tddrtw.ioc;

/**
 * @author lyning
 */
class Instance {
    private final Class<?> type;

    private final Object value;

    public Instance(Class<?> type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Class<?> type() {
        return type;
    }

    public <T> T value() {
        return (T) value;
    }
}