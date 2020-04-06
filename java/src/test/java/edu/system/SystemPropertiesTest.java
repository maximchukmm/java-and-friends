package edu.system;

import org.junit.Assert;
import org.junit.Test;

public class SystemPropertiesTest {

    @Test
    public void userHome() {
        System.out.println(System.getProperty("user.home"));
        Assert.assertNotNull(System.getProperty("user.home"));
    }
}
