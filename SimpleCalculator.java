import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SimpleCalculator extends JFrame implements ActionListener {
    private JTextField resultTextField;
    private String currentInput;
    private double currentValue;
    private char currentOperator;
    public SimpleCalculator() {
        currentInput = "";
        currentValue = 0;
        currentOperator = ' ';
        resultTextField = new JTextField();
        resultTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        resultTextField.setEditable(false);
        resultTextField.setHorizontalAlignment(JTextField.RIGHT);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };
        for (String label : buttonLabels) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.addActionListener(this);
        buttonPanel.add(button);
        }
        setLayout(new BorderLayout());
        add(resultTextField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        new SimpleCalculator();
    }
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (Character.isDigit(command.charAt(0)) || command.equals(".")) {
        currentInput += command;
        resultTextField.setText(currentInput);
        } else if (command.equals("=")) {
        calculateResult();
        } else if (command.equals("C")) {
        currentInput = "";
        currentValue = 0;
        currentOperator = ' ';
        resultTextField.setText("");
        } else {
        calculateResult();
        currentOperator = command.charAt(0);
        currentInput = "";
       }
    }
    private void calculateResult() {
        if (currentInput.length() > 0) {
            double newValue = Double.parseDouble(currentInput);
            switch (currentOperator) {
            case '+':
                currentValue += newValue;
                break;
            case '-':
                currentValue -= newValue;
                break;
            case '*':
                currentValue *= newValue;
                break;
            case '/':
                if (newValue != 0) {
                currentValue /= newValue;
                } else {
                resultTextField.setText("Error: Division by zero");
                return;
                }
                break;
            default:
                currentValue = newValue;
                break;
            }
            resultTextField.setText(String.valueOf(currentValue));
        }
    }
}
