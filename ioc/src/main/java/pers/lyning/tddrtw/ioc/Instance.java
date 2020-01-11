package pers.lyning.tddrtw.ioc;

/**
 * @author lyning
 */
class Instance {
    private final Object value;

    public Instance(Object value) {
        this.value = value;
    }

    public boolean isInstanceOf(Class<?> clazz) {
        return clazz.isInstance(value);
    }

    public <T> T value() {
        return (T) value;
    }
}