package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {

    private final JTextField inputField; // 입력 필드
    private final JTextField resultField; // 결과 출력 필드
    private boolean isCalculated = false;

    private static InputFunction inputFunction = new InputFunction();
    private static Calculator calculator = new Calculator();

    //실수형으로 저장된 정수를 출력하는 메서드
    private static String printResult(double result){
        String num = Double.toString(result);
        if(result % 1 == 0){
            num = Integer.toString((int)result);
        }
        return num;
    }

    public class BtnClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            String currentText = inputField.getText();
            if(isCalculated){
                resultField.setText("");
                currentText = "";
                isCalculated = false;
                System.out.println("true임");
            }
            switch (command){
                case "record":
                    inputField.setText(currentText);
                    break;
                case "exit":
                    System.exit(0);
                    break;
                case "⌫":
                    inputField.setText(inputFunction.inputBackspace(currentText));
                    break;
                case "C":
                    inputField.setText(inputFunction.inputC());
                    break;
                case "(":
                    inputField.setText(inputFunction.inputOpenParentheses(currentText));
                    break;
                case ")":
                    inputField.setText(inputFunction.inputCloseParentheses(currentText));
                    break;
                case "+":
                    inputField.setText(inputFunction.inputOperator(currentText, command));
                    break;
                case "-":
                    inputField.setText(inputFunction.inputOperator(currentText, command));
                    break;
                case "*":
                    inputField.setText(inputFunction.inputOperator(currentText, command));
                    break;
                case "/":
                    inputField.setText(inputFunction.inputOperator(currentText, command));
                    break;
                case "+/-":
                    break;
                case "=":
                    String finalStr = inputFunction.inputComplete(currentText);
                    inputField.setText(finalStr);

                    resultField.setText(printResult(calculator.calculate(finalStr)));
                    isCalculated = true;
                    break;
                case ".":
                    inputField.setText(inputFunction.inputDot(currentText, command));
                    break;
                default:
                    //숫자 또는 연산기호
                    inputField.setText(inputFunction.inputNumber(currentText, command));
                    break;
            }
        }
    }

    public App(){
        setTitle("Sparta Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        //툴바
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new BorderLayout());
        JButton recordBtn = new JButton("record");
        recordBtn.addActionListener(new BtnClickListener());
        JButton exitBtn = new JButton("exit");
        exitBtn.addActionListener(new BtnClickListener());
        toolPanel.add(recordBtn, BorderLayout.WEST);
        toolPanel.add(exitBtn, BorderLayout.EAST);
        //입력창
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.RIGHT); //우측 정렬
        inputField.setEditable(false); //우선 키보드 입력을 막음
        JButton backspaceBtn = new JButton("⌫");
        backspaceBtn.addActionListener(new BtnClickListener());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(backspaceBtn, BorderLayout.EAST);
        //상단(툴바 + 입력창 + 결과창)
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        resultField = new JTextField();
        resultField.setHorizontalAlignment(JTextField.RIGHT);
        resultField.setEditable(false);
        upperPanel.add(resultField, BorderLayout.SOUTH);
        upperPanel.add(toolPanel, BorderLayout.NORTH);
        upperPanel.add(inputPanel, BorderLayout.CENTER);

        container.add(upperPanel, BorderLayout.NORTH);

        //중앙
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(5, 4, 2, 2));

        String[] labels = {
            "C", "(", ")", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
        };

        for(String l : labels){
            JButton btn = new JButton(l);
            if(l.equals("+/-")){
                btn.setEnabled(false);
            }
            btn.addActionListener(new BtnClickListener());
            btnPanel.add(btn);
        }

        container.add(btnPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args){
        App app = new App();
        app.setVisible(true);
    }
}
