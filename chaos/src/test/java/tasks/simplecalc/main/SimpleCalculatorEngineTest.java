package tasks.simplecalc.main;

import edu.tasks.simplecalc.main.SimpleCalculatorEngine;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleCalculatorEngineTest {
    private static SimpleCalculatorEngine calc;

    // создаем движок калькулятора для тестирования операции деления
    @BeforeClass
    public static void setUp() {
        calc = new SimpleCalculatorEngine();
    }

    // тестируем простое деление с проверкой результата
    @Test
    public void divideTestSimpleDivision01() throws Exception {
        assertEquals(new Double(10.0 / 3.0), calc.divide("10", "3"));
    }

    // тестируем простое деление с проверкой результата
    @Test
    public void divideTestSimpleDivision02() throws Exception {
        assertEquals(new Double(1.0 / 3.0), calc.divide("1", "3"));
    }

    // тестируем деление положительного числа на ноль
    @Test
    public void divideTestPositiveInfinity() throws Exception {
        assertEquals(new Double(1.0 / 0.0), calc.divide("1", "0"));
    }

    // тестируем деление отрицательного числа на ноль
    @Test
    public void divideTestNegativeInfinity() throws Exception {
        assertEquals(new Double(-1.0 / 0.0), calc.divide("-1", "0"));
    }

    // тестируем деление ноля на ноль
    @Test
    public void divideTestNaN() throws Exception {
        assertEquals(new Double(0.0 / 0.0), calc.divide("0", "0"));
    }

    // тестируем ситуацию, когда в делимом есть нечисловые символы
    @Test(expected = NumberFormatException.class)
    public void divideTestNonNumericCharactersInDividend() throws Exception {
        calc.divide("123abc123", "1");
    }

    // тестируем ситуацию, когда в делителе есть нечисловые символы
    @Test(expected = NumberFormatException.class)
    public void divideTestNonNumericCharactersInDivisor() throws Exception {
        calc.divide("1", "123abc123");
    }

    // тестируем ситуацию, когда в делимом пустая строка
    @Test(expected = NumberFormatException.class)
    public void divideTestEmptyDividend() throws Exception {
        calc.divide("", "123");
    }

    // тестируем ситуацию, когда в делителе пустая строка
    @Test(expected = NumberFormatException.class)
    public void divideTestEmptyDivisor() throws Exception {
        calc.divide("123", "");
    }
}
