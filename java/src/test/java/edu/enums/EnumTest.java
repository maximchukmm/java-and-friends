package edu.enums;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnumTest {

    @Test
    public void equals_WhenEnumComparesWithNonEnumObject_ThenAlwaysReturnFalse() {
        Object object = new Object();

        assertFalse(Pattern.SINGLETON.equals(object));
    }

    @Test
    public void equals_WhenEnumComparesWithAnotherEnumObject_ThenAlwaysReturnFalse() {
        assertFalse(Pattern.SINGLETON.equals(Framework.SPRING));
    }

//    @Test
//    public void equalSign_WhenEnumComparesWithAnotherEnumObject_ThenDoesNotCompile() {
//        assertFalse(Pattern.SINGLETON == Framework.SPRING); //doesn't compile
//    }

    @Test
    public void equalSign_WhenEnumComparesWithNonEnumObject_ThenAlwaysReturnFalse() {
        Object object = new Object();

        assertFalse(Pattern.SINGLETON == object);
    }

    @Test
    public void equals_WhenEnumComparesWithItSelfReferencedByObject_ThenAlwaysReturnTrue() {
        Object singleton = Pattern.SINGLETON;

        assertTrue(Pattern.SINGLETON.equals(singleton));
    }

    @Test
    public void equals_WhenEnumComparesWithNullReference_ThenAlwaysReturnFalse() {
        assertFalse(Pattern.SINGLETON.equals(null));
    }

    @Test(expected = NullPointerException.class)
    public void equals_WhenNullEnumCallsEqualsMethod_ThenNullPointerExceptionThrows() {
        Pattern nullPattern = null;
        nullPattern.equals(Pattern.SINGLETON);
    }

    @Test
    public void equalSign_WhenNullEnumComparesByEqualSingWithNonNullEnum_ThenDoesNotThrowNullPointerException() {
        Pattern nullPattern = null;
        Pattern nonNullPattern = Pattern.SINGLETON;

        assertFalse(nullPattern == nonNullPattern);
    }

    @Test
    public void equalSign_WhenNullEnumComparesByEqualSingWithNonNullEnum_ThenAlwaysReturnFalse() {
        Pattern nullPattern = null;
        Pattern nonNullPattern = Pattern.SINGLETON;

        assertFalse(nullPattern == nonNullPattern);
    }

    @Test
    public void equalSign_WhenCompareNullEnums_ThenAlwaysReturnTrue() {
        Pattern firstNullPattern = null;
        Pattern secondNullPattern = null;

        assertTrue(firstNullPattern == secondNullPattern);
    }

    public enum Pattern {
        FACADE, FABRIC, SINGLETON, DECORATOR;
    }

    public enum Framework {
        SPRING, HIBERNATE, STRUTS, VAADIN, JSF;
    }
}
