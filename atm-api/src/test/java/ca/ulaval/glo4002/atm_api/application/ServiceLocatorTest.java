package ca.ulaval.glo4002.atm_api.application;

import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

public class ServiceLocatorTest {

    @Before
    public void setUp() {
        ServiceLocator.reset();
    }

    @Test
    public void canResolveARegisteredASingleton() {
        TestImplementation implementation = new TestImplementation();

        ServiceLocator.registerSingleton(TestContract.class, implementation);

        assertSame(implementation, ServiceLocator.resolve(TestContract.class));
    }

    @Test(expected = UnableResolveServiceException.class)
    public void cannotResolveAContractIfNoImplementationIsRegistered() {
        ServiceLocator.resolve(TestContract.class);
    }

    @Test(expected = CannotRegisterContractTwiceException.class)
    public void cannotRegisteredSameContractTwice() {
        ServiceLocator.registerSingleton(TestContract.class, new TestImplementation());
        ServiceLocator.registerSingleton(TestContract.class, new TestImplementation());
    }

    private interface TestContract {
    }

    private class TestImplementation implements TestContract {
    }

}
