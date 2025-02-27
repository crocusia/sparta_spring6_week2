package console;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// 콘솔창을 이용한 Level 1 계산기 구현 코드
public class App {
    //리스트 출력 메서드
    public void printListResults(List<Integer> results){
        System.out.println("-----record-----");
        for(int r : results){
            System.out.println(r);
        }
        System.out.println("----------------");
    }

    public static void main(String[] args) {
        App app = new App();
        Calculator calculator = new Calculator();

        //lv1 요구사항 - 반목문을 사용하되 exit 문자열을 입력하기 전까지 무한으로 계산을 진행하도록 함
        Scanner scanner = new Scanner(System.in);
        while(true) {
            while (true) {
                try {
                    //lv1 요구사항 - 양의 정수(0 포함)를 입력받기
                    System.out.print("연산할 첫 번째 정수를 입력해주세요 : ");
                    int num1 = scanner.nextInt();
                    System.out.print("연산할 두 번째 정수를 입력해주세요 : ");
                    int num2 = scanner.nextInt();
                    if (num1 < 0 || num2 < 0) {
                        throw new IllegalArgumentException("양의 정수(0 포함)만 입력 가능합니다.");
                    }

                    //lv1 요구사항 - 사칙연산 기호(➕,➖,✖️,➗)를 입력받기
                    System.out.print("연산기호(+, -, *, /)를 입력해주세요 : ");
                    char operator = scanner.next().charAt(0);

                    int result;
                    if (operator != '+' && operator != '-' && operator != '*' && operator != '/') {
                        throw new IllegalArgumentException("연산기호에 해당하지 않습니다. " + operator);
                    } else {
                        //lv2 요구사항 - Lv 1에서 구현한 App 클래스의 main 메서드에 Calculator 클래스가 활용될 수 있도록 수정
                        result = calculator.calculate(num1, num2, operator);
                        //lv2 요구사항 - setter 사용
                        calculator.saveResult(result);
                    }
                    //lv1 요구사항 - 양의 정수 2개와 사칙연산 기호를 사용해 연산을 진행한 후 결과값 출력
                    System.out.println("연산 결과 : " + result);
                    break;
                }//lv1 요구사항 - 연산 오류가 발생할 경우 해당 오류에 대한 내용을 정제 후 출력
                catch (InputMismatchException e) {
                    //정수가 잘못 입력된 경우
                    System.out.println("오류: 정수를 입력해주세요.");
                } catch (ArithmeticException e) {
                    // 0으로 나누는 경우
                    System.out.println("오류: 0으로 나눌 수 없습니다.");
                } catch (IllegalArgumentException e) {
                    System.out.println("오류: 잘못된 입력 입니다 ->" + e.getMessage());
                } catch (Exception e) {
                    // 기타 예외 처리
                    System.out.println("알 수 없는 오류 발생: " + e.getMessage());
                    break;
                }
            }


            //lv2 요구사항 - getter 사용
            scanner.nextLine();
            System.out.println("연산 기록을 확인하고 싶다면 record를 입력해주세요: ");
            String isRecord = scanner.nextLine();
            if (isRecord.equals("record")) {
                List<Integer> resultList = calculator.getResultList();
                app.printListResults(resultList); //리스트 내용을 출력하는 함수
                int resultSize = resultList.size(); //리스트 크기 저장
                resultList.clear();
                while (true) {
                    try {
                        System.out.println("기록 삭제를 원할 시, 지우고 싶은 개수를 입력하세요: ");
                        String isRemove = scanner.nextLine(); //원하지 않을 수도 있기 때문에 nextLine으로 받음
                        if (!isRemove.isEmpty()) {
                            int removeNum = Integer.parseInt(isRemove);
                            if (removeNum < 0) {
                                throw new IllegalArgumentException("음수는 입력할 수 없습니다.");
                            } else {
                                if (removeNum > resultSize) {
                                    System.out.println("입력값이 저장된 데이터 개수보다 커, 전체 기록을 삭제합니다.");
                                    removeNum = resultSize;
                                }
                                for (int i = 0; i < removeNum; i++) {
                                    calculator.removeResult();
                                }
                                System.out.println("삭제가 완료되었습니다.");

                                resultList = calculator.getResultList();
                                app.printListResults(resultList);
                                resultList.clear();

                                break;
                            }
                        }
                        else{
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("오류: 정수를 입력해주세요. ->" + e.getMessage());
                    }
                }
            }

            //lv1 요구사항 반복문을 사용하되, 반복의 종료를 알려주는 “exit” 문자열을 입력하기 전까지
            //무한으로 계산을 진행할 수 있도록 소스 코드를 수정하기
            System.out.print("종료하려면 exit를 입력해주세요: ");
            String isEnd = scanner.nextLine();
            if (isEnd.equals("exit")) {
                //exit를 입력 받은 경우 반복 종료
                break;
            }
        }
        scanner.close();
        System.out.println("계산기를 종료합니다.");
    }
}
