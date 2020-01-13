package pers.lyning.tddrtw.ioc;

/**
 * @author lyning
 */
public class ValueSetterProperty implements Property {

    private final String name;

    private final Object value;

    public ValueSetterProperty(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object value() {
        return value;
    }
}
