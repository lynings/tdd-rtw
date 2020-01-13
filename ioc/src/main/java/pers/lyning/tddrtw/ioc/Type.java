package pers.lyning.tddrtw.ioc;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * @author lyning
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Type {

    private final Class<?> clazz;

    private final List<Property> properties;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Type type = (Type) o;
        return clazz.equals(type.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz);
    }

    public Instance instance() {
        Constructible constructible = new ConstructorResolver(getClazz()).resolve();
        Object[] constructorArgs = Arrays.stream(constructible.parameterTypes())
                .map(Instances::get)
                .map(Instance::value)
                .toArray();
        return constructible.newInstance(constructorArgs);

    }

    public static Type of(Class<?> clazz, Property... properties) {
        List<Property> propertyList = Optional.ofNullable(properties)
                .map(Arrays::asList)
                .orElse(new ArrayList<>());
        return new Type(clazz, propertyList);
    }

    public static Type of(Class<?> clazz) {
        return new Type(clazz, new ArrayList<>());
    }
}
