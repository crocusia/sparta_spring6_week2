package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    //전체 입력과 연산 결과를 저장하는 컬렉션
    private List<String> inputStorage = new ArrayList<>();
    private List<Double> resultStorage = new ArrayList<>();

    //전체 입력을 숫자, 연산기호, 괄호에 따라 구분한 String 배열
    private List<String> inputSeperate = new ArrayList<>();

    //우선순위 반환 메서드
    private static int getPriority(String Operator){
        if(Operator.equals("+") || Operator.equals("-")){
            return 1;
        }
        else if(Operator.equals("*")  || Operator.equals("/") ){
            return 2;
        }
        return 0;
    }

    private static enum OperationType{
        ADD("+"){
            @Override
            public double apply(double num1, double num2){
                return num1 + num2;
            }
        },
        SUB("-"){
            @Override
            public double apply(double num1, double num2){
                return num1 - num2;
            }
        },
        MUL("*"){
            @Override
            public double apply(double num1, double num2){
                return num1 * num2;
            }
        },
        DIV("/"){
            @Override
            public double apply(double num1, double num2){
                if(num2 == 0.0){
                    throw new ArithmeticException();
                }
                return num1 / num2;
            }
        };
        //생성자
        private final String operator;
        OperationType(String operator){
            this.operator = operator;
        }

        //추상 메서드 lv3(도전 기능) 요구사항 - 제네릭을 사용한 타입 무관 연산 수행
        public abstract double apply(double num1, double num2);

        //연산 기호 판별
        public static OperationType checkOperator(String operator) {
            for(OperationType op : OperationType.values()){
                if(op.operator.equals(operator)){
                    return op;
                }
            }
            throw new IllegalArgumentException("연산자: " + operator);
        }
    }


    //전체 입력을 숫자, 연산기호, 괄호에 따라 구분해 컬렉션에 저장하는 메서드
    private void separateInput(String input){
        //패턴, 매처, 정규표현식 사용
        Pattern pattern = Pattern.compile("(\\d*\\.?\\d+)|([+\\-*/()])");
        Matcher matcher = pattern.matcher(input);

        inputSeperate.clear();
        while(matcher.find()){
            inputSeperate.add(matcher.group());
        }
    }

    //우선순위에 따라 연산을 불러오는 메서드
    private double calculateByPriority(){
        Stack<Double> stackNumbers = new Stack<>();
        Stack<String> stackOperators = new Stack<>();

        for(String data : inputSeperate){
            if(data.matches("-?\\d+(\\.\\d+)?")){
                stackNumbers.push(Double.parseDouble(data));
            }
            else if(data.equals("(")){
                stackOperators.push(data);
            }
            else if(data.equals(")")){
                while(!stackOperators.isEmpty() && !stackOperators.peek().equals("(")){
                    OperationType operation = OperationType.checkOperator(stackOperators.pop());
                    double num2 = stackNumbers.pop();
                    double num1 = stackNumbers.pop();
                    double result = operation.apply(num1, num2);
                    stackNumbers.push(result);
                }
                stackOperators.pop();
            }
            else{
                while (!stackOperators.isEmpty() && getPriority(stackOperators.peek()) >= getPriority(data)) {
                    OperationType operation = OperationType.checkOperator(stackOperators.pop());
                    double num2 = stackNumbers.pop();
                    double num1 = stackNumbers.pop();
                    double result = operation.apply(num1, num2);
                    stackNumbers.push(result);
                }
                stackOperators.push(data);
            }
        }
        while (!stackOperators.isEmpty()) {
            OperationType operation = OperationType.checkOperator(stackOperators.pop());
            double num2 = stackNumbers.pop();
            double num1 = stackNumbers.pop();
            double result = operation.apply(num1, num2);
            stackNumbers.push(result);
        }
        return stackNumbers.pop();
    }

    //스택을 사용해 우선순위에 따른 연산 처리 메서드
    public double calculate(String input){
        separateInput(input);
        double result = calculateByPriority();

        inputStorage.add(input);
        resultStorage.add(result);
        inputSeperate.clear();

        return result;
    }

}
