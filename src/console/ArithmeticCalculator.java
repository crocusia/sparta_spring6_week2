package console;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

enum OperationType{
    ADD('+'){
        @Override
        public <T1 extends Number, T2 extends Number> double apply(T1 num1, T2 num2){
            return num1.doubleValue() + num2.doubleValue();
        }
    },
    SUB('-'){
        @Override
        public <T1 extends Number, T2 extends Number> double apply(T1 num1, T2 num2){
            return num1.doubleValue() - num2.doubleValue();
        }
    },
    MUL('*'){
        @Override
        public <T1 extends Number, T2 extends Number> double apply(T1 num1, T2 num2){
            return num1.doubleValue() * num2.doubleValue();
        }
    },
    DIV('/'){
        @Override
        public <T1 extends Number, T2 extends Number> double apply(T1 num1, T2 num2){
            if(num2.doubleValue() == 0.0){
                throw new ArithmeticException();
            }
            return num1.doubleValue() / num2.doubleValue();
        }
    };
    //생성자
    private char operator;
    OperationType(char operator){
        this.operator = operator;
    }
    //추상 메서드
    public abstract <T1 extends Number, T2 extends Number> double apply(T1 num1, T2 num2);

    public static OperationType checkChar(char operator) {
        for(OperationType op : OperationType.values()){
            if(op.operator == operator){
                return op;
            }
        }
        throw new IllegalArgumentException("연산자: " + operator);
    }
}

public class ArithmeticCalculator {

    private List<Double> results = new ArrayList<>();

    public <T extends Number> double calculate(T num1, T num2, char operator){
        OperationType operation = OperationType.checkChar(operator);
        return operation.apply(num1, num2);
    }

    public void saveResult(double result) {
        this.results.add(result);
    }

    //lv2 요구사항 getter
    public List<Double> getResultList() {
        return new ArrayList<>(results); //clear를 사용해도 원본 내용이 삭제되지 않도록 복사본 반환
    }

    public List<Double> selectList(double point){
        List<Double> resultList = results.stream()
                .filter(r -> r > point)
                .collect(Collectors.toList());
        return resultList;
    }

    //lv2 요구사항 - 제일 오래된 계산 결과 삭제하기
    public void removeResult(){
        if(!this.results.isEmpty()){
            this.results.remove(0);
        }
    }
}