package edu.exceptions;

import org.junit.Test;
import org.mockito.internal.verification.Times;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class ExceptionsBasicsTest {

    static class AutoCloseableClass implements AutoCloseable {
        @Override
        public void close() throws Exception {
        }
    }

    @Test
    public void whenUsingTryWithResources_ThenAutomaticCallingCloseMethod() {
        AutoCloseableClass autoCloseableClass = null;
        try (AutoCloseableClass resource = spy(AutoCloseableClass.class)) {
            autoCloseableClass = resource;
        } catch (Exception e) {
        }

        try {
            verify(autoCloseableClass, new Times(1)).close();
        } catch (Exception e) {

        }
    }
}