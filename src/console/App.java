package console;

import java.util.InputMismatchException;
import java.util.Scanner;

// 콘솔창을 이용한 Level 1 계산기 구현 코드
public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        //요구사항 - 반목문을 사용하되 exit 문자열을 입력하기 전까지 무한으로 계산을 진행하도록 함
        boolean isProcessing = true;
        while(isProcessing) {
            boolean isCalculation = true;
            while (isCalculation) {
                try {
                    //요구사항 - scanner를 사용해서 양의 정수 2개를 전달받기
                    System.out.print("연산할 첫 번째 정수를 입력해주세요 : ");
                    int num1 = scanner.nextInt();
                    System.out.print("연산할 두 번째 정수를 입력해주세요 : ");
                    int num2 = scanner.nextInt();
                    if (num1 < 0 || num2 < 0) {
                        throw new IllegalArgumentException("양의 정수(0 포함)만 입력 가능합니다.");
                    }

                    //요구사항 - scanner를 사용하여 사칙연산 기호를 전달받기
                    System.out.print("연산기호(+, -, *, /)를 입력해주세요 : ");
                    char operator = scanner.next().charAt(0);

                    int result = 0;
                    switch (operator) { //입력받은 문자열이 사칙연산인지 아닌지 확인
                        case '+':
                            result = num1 + num2;
                            break;
                        case '-':
                            result = num1 - num2;
                            break;
                        case '*':
                            result = num1 * num2;
                            break;
                        case '/':
                            result = num1 / num2;
                            break;
                        default:
                            //사칙연산기호가 아닐 때, 오류 던지기
                            throw new IllegalArgumentException("연산기호에 해당하지 않습니다." + operator);
                    }
                    //요구사항 - 양의 정수 2개와 사칙연산 기호를 사용해 연산을 진행한 후 결과값 출력
                    System.out.println("연산 결과 : " + result);
                    isCalculation = false;
                }  //요구사항 - 연산 오류가 발생할 경우 해당 오류에 대한 내용을 정제 후 출력
                catch (InputMismatchException e) {
                    //정수가 잘못 입력된 경우
                    System.out.println("오류: 잘못된 정수 값입니다.");
                    scanner.nextLine(); //입력 버퍼 리셋
                } catch (ArithmeticException e) {
                    // 0으로 나누는 경우
                    System.out.println("오류: 0으로 나눌 수 없습니다.");
                } catch (IllegalArgumentException e) {
                    // 사칙연산에 해당하지 않는 입력일 경우
                    System.out.println("오류: 잘못된 입력입니다 ->" + e.getMessage());
                } catch (Exception e) {
                    // 기타 예외 처리
                    System.out.println("알 수 없는 오류 발생: " + e.getMessage());
                }
            }
            scanner.nextLine(); //입력 버퍼에 남아있는 개행문자 제거
            System.out.println("종료하려면 exit를 입력해주세요: ");
            String isEnd = scanner.nextLine();
            if(isEnd.equals("exit")) { //exit를 입력 받은 경우 반복 종료
                isProcessing = false;
            }
        }
        scanner.close();
        System.out.println("계산기를 종료합니다.");
    }
}
