package console;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// 콘솔창을 이용한 Level 1 계산기 구현 코드
public class App {
    static Calculator calculator = new Calculator();
    //리스트 출력 메서드
    public static void printListResults(List<Integer> results){
        System.out.println("-----record-----");
        for(int r : results){
            System.out.println(r);
        }
        System.out.println("----------------");
    }

    public static void deleteRecord(int removeNum){
        if (removeNum < 0) {
            throw new IllegalArgumentException("음수는 입력할 수 없습니다.");
        }
        else {
            int size = calculator.getResultSize();
            //입력값이 저장된 데이터 개수보다 큰 경우, 삭제할 데이터의 개수를 저장된 데이터의 개수로 설정함
            if (removeNum > size) {
                System.out.println("입력값이 저장된 데이터 개수보다 커, 전체 기록을 삭제합니다.");
                removeNum = size;
            }
            //맨 앞의 원소를 삭제함
            for (int i = 0; i < removeNum; i++) {
                calculator.removeResult();
            }
            System.out.println("삭제가 완료되었습니다.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean isProcessing = true;
        while(isProcessing) {
            boolean isCalculate = true;
            while (isCalculate) {
                try {
                    System.out.print("연산할 첫 번째 정수를 입력해주세요 : ");
                    int num1 = scanner.nextInt();
                    System.out.print("연산할 두 번째 정수를 입력해주세요 : ");
                    int num2 = scanner.nextInt();
                    if (num1 < 0 || num2 < 0) {
                        throw new IllegalArgumentException("양의 정수(0 포함)만 입력 가능합니다.");
                    }

                    System.out.print("연산기호(+, -, *, /)를 입력해주세요 : ");
                    char operator = scanner.next().charAt(0);

                    int result;

                    if (operator != '+' && operator != '-' && operator != '*' && operator != '/') {
                        throw new IllegalArgumentException("연산기호에 해당하지 않습니다. " + operator);
                    }
                    else {
                        //lv2 요구사항 - Lv 1에서 구현한 App 클래스의 main 메서드에 Calculator 클래스가 활용될 수 있도록 수정
                        result = calculator.calculate(num1, num2, operator);
                        //lv2 요구사항 - setter 사용
                        calculator.saveResult(result);
                    }

                    System.out.println("연산 결과 : " + result);
                    isCalculate = false;
                }
                catch (InputMismatchException e) { //정수가 잘못 입력된 경우
                    System.out.println("오류: 정수를 입력해주세요.");
                } catch (ArithmeticException e) { // 0으로 나누는 경우
                    System.out.println("오류: 0으로 나눌 수 없습니다.");
                } catch (IllegalArgumentException e) {
                    System.out.println("오류: 잘못된 입력 입니다 ->" + e.getMessage());
                } catch (Exception e) { // 기타 예외 처리
                    System.out.println("알 수 없는 오류 발생: " + e.getMessage());
                    break;
                }
            }


            //lv2 요구사항 - getter 사용
            scanner.nextLine();
            System.out.println("연산 기록을 확인하고 싶다면 record를 입력해주세요: ");
            String isRecord = scanner.nextLine();
            if (isRecord.equals("record")) {
                printListResults(calculator.getResultList()); //리스트 내용을 출력하는 함수
                boolean isDelete = true;
                while (isDelete) {
                    try {
                        System.out.println("기록 삭제를 원할 시, 지우고 싶은 개수를 입력하세요: ");
                        String isRemove = scanner.nextLine(); //삭제를 원하지 않을 수도 있기 때문에 nextLine으로 받음
                        if (!isRemove.isEmpty()) {
                            int removeNum = Integer.parseInt(isRemove);
                            deleteRecord(removeNum);
                            printListResults(calculator.getResultList());
                        }
                        isDelete = false;
                    } catch (IllegalArgumentException e) {
                        System.out.println("오류: 정수를 입력해주세요. ->" + e.getMessage());
                    }
                }
            }

            System.out.print("종료하려면 exit를 입력해주세요: ");
            String isEnd = scanner.nextLine();
            if (isEnd.equals("exit")) { //exit를 입력 받은 경우 반복 종료
                isProcessing = false;
            }
        }
        scanner.close();
        System.out.println("계산기를 종료합니다.");
    }
}
