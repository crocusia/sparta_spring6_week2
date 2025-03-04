package console;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//lv3(도전 기능) 요구사항 - Enum 타입을 활용하여 연산자 타입에 대한 정보를 관리
enum OperationType{
    ADD('+'){
        @Override
        public <T1 extends Number, T2 extends Number> Number apply(T1 num1, T2 num2){
            return num1.doubleValue() + num2.doubleValue();
        }
    },
    SUB('-'){
        @Override
        public <T1 extends Number, T2 extends Number> Number apply(T1 num1, T2 num2){
            return num1.doubleValue() - num2.doubleValue();
        }
    },
    MUL('*'){
        @Override
        public <T1 extends Number, T2 extends Number> Number apply(T1 num1, T2 num2){
            return num1.doubleValue() * num2.doubleValue();
        }
    },
    DIV('/'){
        @Override
        public <T1 extends Number, T2 extends Number> Number apply(T1 num1, T2 num2){
            if(num2.doubleValue() == 0.0){
                throw new ArithmeticException();
            }
            return num1.doubleValue() / num2.doubleValue();
        }
    };
    //생성자
    private final char operator;
    OperationType(char operator){
        this.operator = operator;
    }

    //추상 메서드 lv3(도전 기능) 요구사항 - 제네릭을 사용한 타입 무관 연산 수행
    public abstract <T1 extends Number, T2 extends Number> Number apply(T1 num1, T2 num2);

    //연산 기호 판별
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

    private final List<Number> results = new ArrayList<>();

    public <T1 extends Number, T2 extends Number> Number calculate(T1 num1, T2 num2, char operator){
        OperationType operation = OperationType.checkChar(operator);
        return operation.apply(num1, num2);
    }

    public void saveResult(Number result) {
        results.add(result);
    }

    public List<Number> getResultList() {
        return new ArrayList<>(results); //clear를 사용해도 원본 내용이 삭제되지 않도록 복사본 반환
    }

    public int getResultSize() {
        return results.size();
    }
    //lv3(도전 기능) 요구사항 - Lambda와 Stream을 활용하여 조회
    public List<Number> selectList(Number point){
        return results.stream()
                .filter(r -> r.doubleValue() > point.doubleValue())
                .collect(Collectors.toList());
    }

    public void removeResult(){
        if(!results.isEmpty()){
            results.remove(0);
        }
    }
}