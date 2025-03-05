package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator <T> {
    //전체 입력과 연산 결과를 저장하는 컬렉션
    private List<String> inputStorage;
    private List<Number> resultStorage;

    //전체 입력을 숫자, 연산기호, 괄호에 따라 구분한 String 배열
    private List<String> inputSeperate;

    //우선순위 계산에 사용할 숫자, 기호 스택
    Stack<Number> stackNumber;
    Stack<Character> stackChar;

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

    //스택을 사용해 우선순위에 따른 연산 처리 메서드
    public void calculate(String input){
        separateInput(input);

    }


}
