package edu.tutorials.plural.patternsforpragmaticunittesting.bankmanager;

import org.junit.Before;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

abstract class BaseTestClass {
    @Before
    public void setUp() throws Exception {
        Logger logger = mock(Logger.class);
        Mockito.doAnswer(invocation -> {
            System.out.println(invocation.getArguments()[0]);
            return null;
        }).when(logger).writeLine(anyString());
        Logging.setLogger(logger);
    }
}
