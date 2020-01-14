package pers.lyning.tddrtw.ioc;

/**
 * @author lyning
 */
public class NameSetterProperty<T> implements Property<T> {

    private final String name;

    private final T value;

    public NameSetterProperty(String name, T value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public T value() {
        return value;
    }
}
