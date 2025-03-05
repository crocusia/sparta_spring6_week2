package gui;

public class InputFunction {
    private boolean isDouble; //실수 표현을 위한 온점 사용 가능 여부를 판별
    private boolean isParentheses; //괄호의 시작 여부를 판별
    private int numParentheses; //여는 괄호의 개수

    //사칙 연산 기호인지를 판별하는 메서드
    private boolean checkOperator(char lastChar){
        if(lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/'){
            return true;
        }
        else{
            return false;
        }
    }

    //InputFunction 클래스 생성자
    public InputFunction(){
        this.isDouble = true;
        this.isParentheses = false;
        this.numParentheses = 0;
    }

    //BackSpace 클릭 처리 메서드
    public String InputBackspace(String text){
        if(!text.isEmpty()){
            char lastChar = text.charAt(text.length() - 1);
            if(lastChar == ')'){            //지우는 문자가 닫는 괄호이면
                numParentheses++;           //닫아야 하는 괄호의 개수 1 증가
            }
            else if(lastChar == '('){       //지우는 문자가 여는 괄호이면
                numParentheses--;           //닫아야 하는 괄호의 개수 1 감소
            }
            return text.substring(0, text.length()-1); //끝 문자를 지운 문자열을 반환
        }
        return "";
    }

    //Clear C 클릭 처리 메서드
    public String InputC (){
        return "";
    }

    //괄호 클릭 시 처리 메서드
    public String InputParentheses(String text, String command){
        if (command.equals("(")) {        //여는 괄호인 경우
            isParentheses = true;       //괄호가 열려 있다고 설정
            numParentheses++;           //닫아야 하는 괄호의 개수 1 증가
            if(!text.isEmpty()) {
                char lastChar = text.charAt(text.length()-1);
                if (Character.isDigit(lastChar)) { //여는 괄호의 바로 앞 문자가 숫자라면
                    return text + "*" + command; //곱하기 연산자를 여는 괄호 앞에 추가
                }
            }
            return text + command;
        }
        else {
            //괄호가 열려있고, 닫는 괄호의 앞이 연산 기호가 아닐 때
            if (isParentheses && !checkOperator(command.charAt(0))) {
                numParentheses--;           //닫아야 하는 괄호의 개수 1 감소
                if (numParentheses == 0) {    //닫아야 하는 괄호가 더 이상 없을 때
                    isParentheses = false;  //괄호의 열림 여부를 닫힘으로 설정
                }
                return text + command;
            }
        }
        return text;
    }

    //연산 기호 클릭 시 처리 메서드
    public String InputOperator(String text, String command){
        isDouble = true;                    //실수형 입력 가능 여부를 가능으로 변경
        if(text.isEmpty()){                 //연산기호가 가장 먼저 입력되면
            return "0" + command;           //맨 앞에 0을 삽입
        }
        else{
            //연산기호가 연속으로 입력되지 않도록 방지
            char lastChar = text.charAt(text.length() - 1);
            if(checkOperator(lastChar)){                             //바로 앞 문자가 연산 기호라면
                return text.substring(0, text.length()-1) + command; //해당 기호를 새로 입력된 기호로 대체
            }
            else if(lastChar == '('){       //바로 앞 문자가 여는 괄호라면
                return text;                //연산 기호는 입력되지 않음
            }
            else{
                return text + command;
            }
        }
    }

    // +/- 클릭 시 처리 메서드
    public String InputFormatSign(String text){
        return text; //추후 추가 필요
    }

    // = 클릭 시 처리 메서드
    public String InputComplete(String text){
        String result = text;
        //닫아야 하는 괄호의 개수가 남아있는 만큼 닫는 괄호 입력
        for(int i = numParentheses; i > 0; i--){
            result = InputParentheses(result, ")");
        }
        return result;
    }

    //실수형 표현 온점 클릭 시 처리 메서드
    public String InputDot(String text, String command){
        if (isDouble) {                 //실수형을 입력할 수 있는 상황이라면
            isDouble = false;           //실수형 입력 가능 여부를 불가로 변경
            if(text.isEmpty()){         //아무것도 입력되지 않은 상황이라면
                return "0" + command;   //앞에 0을 생성한 후 온점 추가
            }
            else{
                return text + command;
            }
        }
        return text;
    }

    //숫자 버튼 클릭 시 처리 메서드
    public String InputNumber(String text, String command){
        return text + command;
    }
}