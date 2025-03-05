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

    //온점 사용 후 숫자 입력 여부 확인 메서드
    private String isEndDot(String text){
        String result = text;
        if(!isDouble){ //이미 온점이 사용된 상태이고
            char lastChar = text.charAt(text.length()-1);
            if(lastChar == '.'){ //온점 뒤에 숫자가 입력되지 않았다면
                result = result + "0";
            }
        }
        return result;
    }

    //InputFunction 클래스 생성자
    public InputFunction(){
        this.isDouble = true;
        this.isParentheses = false;
        this.numParentheses = 0;
    }

    //BackSpace 클릭 처리 메서드
    public String inputBackspace(String text){
        if(!text.isEmpty()){
            char lastChar = text.charAt(text.length() - 1);
            if(lastChar == ')'){            //지우는 문자가 닫는 괄호이면
                numParentheses++;           //닫아야 하는 괄호의 개수 1 증가
            }
            else if(lastChar == '('){       //지우는 문자가 여는 괄호이면
                numParentheses--;           //닫아야 하는 괄호의 개수 1 감소
                if(numParentheses <= 0){
                    numParentheses = 0;     //여는 괄호가 전부 제거된 경우
                    isParentheses = false;  //괄호 시작 여부 false로 변경
                }
            }
            else if(lastChar == '.'){
                isDouble = true;
            }
            return text.substring(0, text.length()-1); //끝 문자를 지운 문자열을 반환
        }
        return "";
    }

    //Clear C 클릭 처리 메서드
    public String inputC (){
        //초기화
        this.isDouble = true;
        this.isParentheses = false;
        this.numParentheses = 0;
        return "";
    }

    //여는 괄호 클릭 시 처리 메서드
    public String inputOpenParentheses(String text){
        String result = isEndDot(text);
        if(!text.isEmpty()) {
            char lastChar = result.charAt(result.length()-1);
            if (Character.isDigit(lastChar)) { //여는 괄호의 바로 앞 문자가 숫자라면
                result = result + "*("; //곱하기 연산자를 여는 괄호 앞에 추가
                isParentheses = true;       //괄호가 열려 있다고 설정
                numParentheses++;           //닫아야 하는 괄호의 개수 1 증가
                isDouble = true;
            }
        }
        else{
            result = result + "(";
            isParentheses = true;       //괄호가 열려 있다고 설정
            numParentheses++;           //닫아야 하는 괄호의 개수 1 증가
            isDouble = true;
        }
        return result;
    }

    //닫는 괄호 클릭 시 처리 메서드
    public String inputCloseParentheses(String text) {
        String result = isEndDot(text);
        if (isParentheses) { //괄호가 열려있는 상태일 때
            numParentheses--;           //닫아야 하는 괄호의 개수 1 감소
            if (numParentheses <= 0) {    //닫아야 하는 괄호가 더 이상 없을 때
                isParentheses = false;  //괄호의 열림 여부를 닫힘으로 설정
                numParentheses = 0;
            }
            result = result + ")";
            isDouble = true;
        }
        return result;
    }

    //연산 기호 클릭 시 처리 메서드
    public String inputOperator(String text, String command){
        String result = isEndDot(text);
        if(text.isEmpty()){                 //연산기호가 가장 먼저 입력되면
            result = result + "0" + command;           //맨 앞에 0을 삽입
            isDouble = true;
        }
        else{
            //연산기호가 연속으로 입력되지 않도록 방지
            char lastChar = text.charAt(text.length() - 1);
            if(checkOperator(lastChar)){                             //바로 앞 문자가 연산 기호라면
                result = result.substring(0, text.length()-1) + command; //해당 기호를 새로 입력된 기호로 대체
                isDouble = true;
            }
            else if(lastChar == '('){       //바로 앞 문자가 여는 괄호라면
                result = result + "";       //연산 기호는 입력되지 않음
            }
            else{
                result = result + command;
                isDouble = true;
            }
        }
        return result;
    }

    // +/- 클릭 시 처리 메서드
    public String inputFormatSign(String text){
        return text; //추후 추가 필요
    }

    // = 클릭 시 처리 메서드
    public String inputComplete(String text){
        String result = isEndDot(text);
        //닫아야 하는 괄호의 개수가 남아있는 만큼 닫는 괄호 입력
        for(int i = numParentheses; i > 0; i--){
            result = inputCloseParentheses(result);
        }
        isDouble = true;
        return result;
    }

    //실수형 표현 온점 클릭 시 처리 메서드
    public String inputDot(String text, String command){
        String result = text;
        if (isDouble) {                 //실수형을 입력할 수 있는 상황이라면
            isDouble = false;           //실수형 입력 가능 여부를 불가로 변경
            if(result.isEmpty()){         //아무것도 입력되지 않은 상황이라면
                result = result + "0" + command;   //앞에 0을 생성한 후 온점 추가
            }
            else{
                char lastChar = result.charAt(result.length()-1);
                if(lastChar == ')'){
                    result = result + "";
                }
                else {
                    result = result + command;
                }
            }
        }
        return result;
    }

    //숫자 버튼 클릭 시 처리 메서드
    public String inputNumber(String text, String command){
        String result = text;
        if(!result.isEmpty()){
            char lastChar = result.charAt(result.length()-1);
            if(lastChar == ')'){       //닫는 괄호로 끝나 있는 경우
                result = result + "";  //숫자 입력 불가
            }
            else{
                result = result + command;
            }
        }
        else{
            result = result + command;
        }
        return result;
    }
}