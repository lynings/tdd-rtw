package pers.lyning.tddrtw.ioc;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import pers.lyning.tddrtw.ioc.exception.InstanceNotFountException;
import pers.lyning.tddrtw.ioc.sample.NoDependence;
import pers.lyning.tddrtw.ioc.sample.OneDependence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstancesTest {

    @Test
    void should_failure_when_get_a_noe_exist_instance() throws Exception {
        // given
        Instances instances = new Instances();
        instances.put(new Instance(new NoDependence()));
        // when
        Assert<?, ? extends Throwable> assertThatThrownBy = assertThatThrownBy(() -> instances.get(Object.class));
        // then
        assertThatThrownBy.isInstanceOf(InstanceNotFountException.class);
    }

    @Test
    void should_return_custom_instance_when_instance_existed() throws Exception {
        // given
        Instances instances = new Instances();
        Instance givenInstance = new Instance(new OneDependence(new OneDependence.A()));
        instances.put(givenInstance);
        // when
        Instance actualInstance = instances.get(OneDependence.class);
        // then
        assertThat(givenInstance)
                .usingDefaultComparator()
                .isEqualToComparingFieldByField(actualInstance);
    }

    @Test
    void should_return_string_instance_when_instance_existed() throws Exception {
        // given
        Instances instances = new Instances();
        Instance givenInstance = new Instance("hello");
        instances.put(givenInstance);
        // when
        Instance actualInstance = instances.get(String.class);
        // then
        assertThat(givenInstance)
                .usingDefaultComparator()
                .isEqualToComparingFieldByField(actualInstance);
    }
}