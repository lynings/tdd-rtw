package pers.lyning.tddrtw.ioc;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author lyning
 */
class ConstructorResolver {
    private final Class<?> clazz;

    public ConstructorResolver(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Constructible resolve() {
        return Arrays.stream(clazz.getDeclaredConstructors())
                // 拿到参数最多的构造函数
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .map(Constructible::new)
                .get();
    }
}
