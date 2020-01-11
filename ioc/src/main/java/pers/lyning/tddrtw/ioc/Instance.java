package pers.lyning.tddrtw.ioc;

/**
 * @author lyning
 */
class Instance {
    private final Object value;

    public Instance(Object value) {
        this.value = value;
    }

    public <T> T value() {
        return (T) value;
    }
}