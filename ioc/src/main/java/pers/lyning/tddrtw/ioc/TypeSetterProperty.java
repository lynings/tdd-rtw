package pers.lyning.tddrtw.ioc;

/**
 * @author lyning
 */
public class TypeSetterProperty<T> implements Property<Class<T>> {

    private final String name;

    private final Class<T> type;

    public TypeSetterProperty(Class<T> type) {
        this.type = type;
        String className = type.getSimpleName();
        name = className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Class<T> value() {
        return type;
    }
}
