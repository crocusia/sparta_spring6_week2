package console;

enum OperationType{
    ADD('+'){
        public double apply(double num1, double num2){
            return num1 + num2;
        }
    },
    SUB('-'){
        public double apply(double num1, double num2){
            return num1 - num2;
        }
    },
    MUL('*'){
        public double apply(double num1, double num2){
            return num1 * num2;
        }
    },
    DIV('/'){
        public double apply(double num1, double num2){
            return num1 / num2;
        }
    };
    //생성자
    private char operator;
    OperationType(char operator){
        this.operator = operator;
    }
    //추상 메서드
    public abstract double apply(double num1, double num2);

    public static OperationType checkChar(char operator) {
        for(OperationType op : OperationType.values()){
            if(op.operator == operator){
                return op;
            }
        }
        throw new IllegalArgumentException("잘못된 연산자: " + operator);
    }
}

public class ArithmeticCalculator {
    public <T> double calculate(T num1, T num2, char operator){
        OperationType operation = OperationType.checkChar(operator);
        double result = operation.apply((double)num1, (double)num2);
        return (result%1 == 0) ? (int)result : result;
    }
}