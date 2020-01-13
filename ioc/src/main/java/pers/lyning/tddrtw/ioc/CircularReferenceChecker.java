package pers.lyning.tddrtw.ioc;

import pers.lyning.tddrtw.ioc.exception.CircularReferenceException;

import java.util.List;

/**
 * @author lyning
 */
class CircularReferenceChecker {

    public static void check(ConstructorDependenceResolver.DependencyPath dependencyPath) {
        List<String> dependencies = dependencyPath.asReverseList();
        for (int index = 1, size = dependencies.size(); index <= size - 1; index++) {
            if (2 * index > size) {
                break;
            }
            List<String> left = dependencies.subList(0, index);
            List<String> right = dependencies.subList(index, 2 * index);
            if (left.equals(right)) {
                throw new CircularReferenceException("cyclic dependency!");
            }
        }
    }
}