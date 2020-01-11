package pers.lyning.tddrtw.ioc;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import pers.lyning.tddrtw.ioc.exception.InstanceNotFountException;
import pers.lyning.tddrtw.ioc.sample.NoDependence;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstancesTest {

    @Test
    void should_failure_when_get_a_exist_instance() throws Exception {
        // given
        Instances instances = new Instances();
        instances.put(new Instance(new NoDependence()));
        // when
        Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> instances.get(Object.class));
        // then
        assertThatThrownBy.isInstanceOf(InstanceNotFountException.class);
    }
}