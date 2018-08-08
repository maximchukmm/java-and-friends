package edu.other;

import org.junit.Test;
import org.mockito.internal.verification.Times;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class SmallTests {

    static class MyBoolean {

        boolean getTrue() {
            return true;
        }

        boolean getFalse() {
            return false;
        }
    }

    @Test
    public void logicalAnd_WhenFirstExpressionIsFalse_ThenEvaluationStops() {
        MyBoolean bool = spy(MyBoolean.class);

        if (bool.getFalse() && bool.getTrue()) {
        }

        verify(bool, new Times(1)).getFalse();
        verify(bool, new Times(0)).getTrue();
    }

    @Test
    public void logicalAnd_WhenFirstExpressionIsTrue_ThenEvaluationDoesNotStop() {
        MyBoolean bool = spy(MyBoolean.class);

        if (bool.getTrue() && bool.getFalse()) {
        }

        verify(bool, new Times(1)).getTrue();
        verify(bool, new Times(1)).getFalse();
    }

    @Test
    public void bitwiseAnd_WhenFirstExpressionIsFalse_ThenEvaluationDoesNotStop() {
        MyBoolean bool = spy(MyBoolean.class);

        if (bool.getFalse() & bool.getTrue()) {
        }

        verify(bool, new Times(1)).getFalse();
        verify(bool, new Times(1)).getTrue();
    }

    @Test
    public void bitwiseAnd_WhenFirstExpressionIsTrue_ThenEvaluationDoesNotStop() {
        MyBoolean bool = spy(MyBoolean.class);

        if (bool.getTrue() & bool.getFalse()) {
        }

        verify(bool, new Times(1)).getFalse();
        verify(bool, new Times(1)).getTrue();
    }

    @Test
    public void whenUsingInstanceOfOnNullReference_ThenReturnFalse() {
        Object nullObject = null;

        assertFalse(nullObject instanceof Object);
    }

    @Test
    public void whenComparingNullWithNull_ThenAlwaysReturnTrue() {
        assertTrue(null == null);
    }
}


