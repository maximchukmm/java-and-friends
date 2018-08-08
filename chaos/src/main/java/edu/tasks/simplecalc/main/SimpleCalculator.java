/**
 * Данный класс отвечает за отрисовку окна калькулятора.
 */

package edu.tasks.simplecalc.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Font;
import java.awt.GridLayout;

public class SimpleCalculator extends JFrame {

    // Объявляем все компоненты калькулятора
    private JPanel windowContent;
    private JTextField dividend;
    private JTextField divisor;
    private JTextField resultOfDivision;
    private JButton equals;
    private JLabel dividendLabel;
    private JLabel divisorLabel;
    private JLabel equalsLabel;
    private JLabel resultOfDivisionLabel;

    // Конфигурируем отображение главного окна калькулятора
    private void configFrame() {
        this.setResizable(false);
        this.setTitle("Division");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(windowContent);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // Конфигурируем отображение панели главного окна
    private void configPanel() {
        windowContent = new JPanel();
        GridLayout gridLayout = new GridLayout(4, 2);
        gridLayout.setHgap(5);
        windowContent.setLayout(gridLayout);

        windowContent.add(dividendLabel);
        windowContent.add(dividend);
        windowContent.add(divisorLabel);
        windowContent.add(divisor);
        windowContent.add(equalsLabel);
        windowContent.add(equals);
        windowContent.add(resultOfDivisionLabel);
        windowContent.add(resultOfDivision);
    }

    // Конфигурируем отображение текстовых полей
    private void configTextFields() {
        dividend = new JTextField(30);
        divisor = new JTextField(30);
        resultOfDivision = new JTextField(30);
        resultOfDivision.setEditable(false);
    }

    // Конфигурируем отображение кнопок
    private void configButtons() {
        equals = new JButton("=");
    }

    // Конфигурируем отображение меток
    private void configLabels() {
        dividendLabel = new JLabel("Dividend");
        divisorLabel = new JLabel("Divisor");
        equalsLabel = new JLabel("Equals");
        resultOfDivisionLabel = new JLabel("Result");
    }

    // Подключаем listener'ы
    private void configListeners(SimpleCalculatorEngine listener) {
        dividend.addKeyListener(listener);
        divisor.addKeyListener(listener);
        equals.addActionListener(listener);
    }

    // Конфигурируем отображение всего калькулятора
    private void configSimpleCalculator() {
        configLabels();
        configButtons();
        configTextFields();
        configPanel();
        configFrame();
        // Создаем вычислительный движок для калькулятора
        configListeners(new SimpleCalculatorEngine(this));
    }

    public SimpleCalculator() {
        configSimpleCalculator();
    }

    public String getDividendValue() {
        return dividend.getText();
    }

    public String getDivisorValue() {
        return divisor.getText();
    }

    public void setResultOfDivisionValue(String result) {
        resultOfDivision.setText(result);
    }

    // Меняем толщину шрифта на нормальную
    public void setPlainFontToResultOfDivision() {
        if (!resultOfDivision.getFont().isPlain()) {
            resultOfDivision.setFont(
                new Font(resultOfDivision.getFont().getName(),
                    Font.PLAIN,
                    resultOfDivision.getFont().getSize())
            );
        }
    }

    // Меняем толщину шрифта на жирную для отображения сообщения об ошибке
    public void setBoldFontToResultOfDivision() {
        if (!resultOfDivision.getFont().isBold()) {
            resultOfDivision.setFont(
                new Font(resultOfDivision.getFont().getName(),
                    Font.BOLD,
                    resultOfDivision.getFont().getSize())
            );
        }
    }
}
