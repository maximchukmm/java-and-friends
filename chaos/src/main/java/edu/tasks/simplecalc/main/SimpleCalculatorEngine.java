/**
 * Данный класс отвечает за обработку вводимых данных
 * в форму, которая создается классом SimpleCalculator.
 * Этот класс является моей попыткой реализовать паттерн MVC:
 * класс SimpleCalculator занимается отображением данных
 * класс SimpleCalculatorEngine занимается обработкой данных
 */

package edu.tasks.simplecalc.main;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Класс SimpleCalculatorEngine наследуется от класса KeyAdapter для возможности
 * обработки введенных символов в тектовые поля.
 * Класс SimpleCalculatorEngine реализует интерфейс ActionListener для возможности
 * обработки события "нажатие на кнопку равно".
 */
public class SimpleCalculatorEngine extends KeyAdapter implements ActionListener {

    // Ссылка на форму калькулятора для получения данных из формы и установки данных в форму
    private SimpleCalculator simpleCalculator;

    // Конструктор для создания объекта, который будет тестироваться
    public SimpleCalculatorEngine() {
    }

    public SimpleCalculatorEngine(SimpleCalculator simpleCalculator) {
        this.simpleCalculator = simpleCalculator;
    }

    public Double divide(String dividend, String divisor) throws NumberFormatException {
        // Перед попыткой преобразовать строку в число заменяем разделитель десятичной части на точку
        double doubleDividend = Double.parseDouble(dividend.replaceAll(",", "."));
        double doubleDivisor = Double.parseDouble(divisor.replaceAll(",", "."));
        return doubleDividend / doubleDivisor;
    }

    // Обрабатываем каждый введенный в текстовое поле символ
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() instanceof JTextField) {
            JTextField textField = (JTextField) e.getSource();
            // Ограничиваем длину чисел длиной текстового поля
            if (textField.getText().length() > textField.getColumns()) {
                textField.setText(textField.getText().substring(0, textField.getColumns()));
            }
        }
    }

    // Обрабатываем нажатие кнопки равно
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            try {
                Double resultOfDivision = divide(simpleCalculator.getDividendValue(),
                    simpleCalculator.getDivisorValue());
                simpleCalculator.setPlainFontToResultOfDivision();
                simpleCalculator.setResultOfDivisionValue(resultOfDivision.toString());
            } catch (NumberFormatException ex) {
                simpleCalculator.setBoldFontToResultOfDivision();
                simpleCalculator.setResultOfDivisionValue("NON NUMERIC CHARACTERS IN NUMBERS!");
            }
        }
    }
}
