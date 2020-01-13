package pers.lyning.tddrtw.ioc;

import lombok.NonNull;

/**
 * @author lyning
 */
public class TypeSetterProperty implements Property {

    private final String name;

    private final Object type;

    public TypeSetterProperty(@NonNull Class<?> type) {
        this.type = type;
        String className = type.getSimpleName();
        name = className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object value() {
        return type;
    }
}
