package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {

    private JTextField inputField; // 입력 필드

    private String checkInput(String text, String command) {
        if (command.equals("+") || command.equals("-") ||command.equals("*") || command.equals("/")) {
            if(text.isEmpty()){ //연산기호가 가장 먼저 입력되면 0을 먼저 넣어주기
                return "0" + command;
            }
            else{ //연산기호가 연속으로 입력되지 않도록 하기
                char lastChar = text.charAt(text.length() - 1);
                if(lastChar == '+' || lastChar == '-' || lastChar == '×' || lastChar == '÷'){
                    return text;
                }
                else{
                    return text + command;
                }
            }
        }
        else{
            return text + command;
        }
    }
    public class BtnClickListner implements ActionListener{
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
                    if(!currentText.isEmpty()){
                        inputField.setText(currentText.substring(0, currentText.length()-1));
                    }
                    break;
                case "+/-":
                    break;
                case "=":
                    break;
                default:
                    //숫자 또는 연산기호
                    inputField.setText(currentText + command);
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
        recordBtn.addActionListener(new BtnClickListner());
        JButton exitBtn = new JButton("exit");
        exitBtn.addActionListener(new BtnClickListner());
        toolPanel.add(recordBtn, BorderLayout.WEST);
        toolPanel.add(exitBtn, BorderLayout.EAST);
        //입력창
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.RIGHT); //우측 정렬
        inputField.setEditable(false); //우선 키보드 입력을 막음
        JButton backspaceBtn = new JButton("⌫");
        backspaceBtn.addActionListener(new BtnClickListner());
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
            btn.addActionListener(new BtnClickListner());
            btnPanel.add(btn);
        }

        container.add(btnPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args){
        App app = new App();
        app.setVisible(true);
    }
}
