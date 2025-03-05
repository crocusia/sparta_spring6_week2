package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {

    private JTextField inputField; // 입력 필드
    static InputFunction inputFunction = new InputFunction();

    public class BtnClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            String currentText = inputField.getText();
            switch (command){
                case "record":
                    break;
                case "exit":
                    System.exit(0);
                    break;
                case "⌫":
                    inputField.setText(inputFunction.InputBackspace(currentText));
                    break;
                case "C":
                    inputField.setText(inputFunction.InputC());
                    break;
                case "(":
                    inputField.setText(inputFunction.InputParentheses(currentText, command));
                    break;
                case ")":
                    inputField.setText(inputFunction.InputParentheses(currentText, command));
                    break;
                case "+":
                    inputField.setText(inputFunction.InputOperator(currentText, command));
                    break;
                case "-":
                    inputField.setText(inputFunction.InputOperator(currentText, command));
                    break;
                case "*":
                    inputField.setText(inputFunction.InputOperator(currentText, command));
                    break;
                case "/":
                    inputField.setText(inputFunction.InputOperator(currentText, command));
                    break;
                case "+/-":
                    break;
                case "=":
                    inputField.setText(inputFunction.InputComplete(currentText));
                    break;
                case ".":
                    inputField.setText(inputFunction.InputDot(currentText, command));
                    break;
                default:
                    //숫자 또는 연산기호
                    inputField.setText(inputFunction.InputNumber(currentText, command));
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
        //상단(툴바 + 입력창)
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
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
