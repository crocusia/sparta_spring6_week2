package console;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    //lv2 요구사항 - 연산 결과를 저장하는 컬렉션 타입 필드
    List<Integer> results = new ArrayList<>();

    //lv2 요구사항 - 사칙연산을 수행한 후, 결과값을 반환하는 메서드 구현
    private int add(int num1, int num2) {
        return num1 + num2;
    }
    private int sub(int num1, int num2) {
        return num1 - num2;
    }
    private int mul(int num1, int num2) {
        return num1 * num2;
    }
    private int div(int num1, int num2) {
        return num1 / num2;
    }

    //lv2 요구사항 - 양의 정수 2개(0 포함)와 연산 기호를 매개변수로 받아
    //사칙연산 기능을 수행한 후 결과 값을 반환하는 메서드
    public int calculate(int num1, int num2, char operator){
        int result = 0;
        switch (operator) { //입력받은 문자열이 사칙연산인지 아닌지 확인
            case '+':
                result = add(num1, num2);
                break;
            case '-':
                result = sub(num1, num2);
                break;
            case '*':
                result = mul(num1, num2);
                break;
            case '/':
                result = div(num1, num2);
                break;
            default:
                break;
        }
        return result;
    }
    //lv2 요구사항 - App 클래스의 main 메서드에서 Calculator클래스의
    //연산 결과를 저장하고 있는 컬렉션 필드에 직접 접근하지 못하도록 수정 (캡슐화)

    //lv2 요구사항 setter
    public void saveResult(int result) {
        this.results.add(result);
    }

    //lv2 요구사항 getter
    public List<Integer> getResultList() {
        return new ArrayList<>(results); //clear를 사용해도 원본 내용이 삭제되지 않도록 복사본 반환
    }

    //요구사항 - 제일 오래된 계산 결과 삭제하기
    public void removeResult(){
        if(!this.results.isEmpty()){
            this.results.remove(0);
        }
    }
}
