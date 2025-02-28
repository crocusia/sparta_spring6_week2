package console;

import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// 콘솔창을 이용한 Level 1 계산기 구현 코드
public class App {
    //리스트 출력 메서드
    public void printListResults(List<Double> results){
        System.out.println("-----record-----");
        for(double r : results){
            if(r % 1 == 0){
                System.out.printf("%.0f ", r);
            }
            else{
            System.out.println(r);
            }
        }
        System.out.println("----------------");
    }

    public static <T extends Number> T checkNumber(String input) {
        if (input.matches("-?\\d+")) { // 정수 판별
            return (T) Integer.valueOf(input);
        } else if (input.matches("-?\\d+\\.\\d+")) { // 실수 판별
            return (T) Double.valueOf(input);
        } else {
            throw new InputMismatchException();
        }
    }

    public static void main(String[] args) {
        App app = new App();
        Calculator calculator = new Calculator();
        ArithmeticCalculator arithmeticCalculator = new ArithmeticCalculator();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            while (true) {
                try {
                    //lv1 요구사항 - 양의 정수(0 포함)를 입력받기
                    System.out.print("연산할 첫 번째 값을 입력해주세요 : ");
                    Number num1 = checkNumber(scanner.nextLine());
                    System.out.print("연산할 두 번째 값을 입력해주세요 : ");
                    Number num2 = checkNumber(scanner.nextLine());
                    System.out.print("연산기호(+, -, *, /)를 입력해주세요 : ");
                    char operator = scanner.next().charAt(0);
                    scanner.nextLine();
                    double result;
                    if (operator != '+' && operator != '-' && operator != '*' && operator != '/') {
                        throw new IllegalArgumentException("연산기호에 해당하지 않습니다. " + operator);
                    }
                    else {
                        result = arithmeticCalculator.calculate(num1, num2, operator);
                        arithmeticCalculator.saveResult(result);
                    }

                    if(result % 1 == 0){
                        System.out.printf("연산 결과 : %.0f \n", result);
                    }
                    else {
                        System.out.println("연산 결과 : " + result);
                    }
                    break;
                }
                catch (InputMismatchException e) {
                    //정수가 잘못 입력된 경우
                    System.out.println("오류(입력): 잘못된 입력입니다.");
                } catch (ArithmeticException e) {
                    // 0으로 나누는 경우
                    System.out.println("오류(입력): 0으로 나눌 수 없습니다.");
                } catch (IllegalArgumentException e) {
                    System.out.println("오류(입력): 잘못된 입력 입니다 ->" + e.getMessage());
                } catch (Exception e) {
                    // 기타 예외 처리
                    System.out.println("알 수 없는 오류 발생(입력): " + e.getMessage());
                    scanner.nextLine();
                    break;
                }
            }

            System.out.println("연산 기록을 확인하고 싶다면 record를 입력해주세요: ");
            String isRecord = scanner.nextLine();
            if (isRecord.equals("record")) {
                List<Double> resultList = arithmeticCalculator.getResultList();
                app.printListResults(resultList); //리스트 내용을 출력하는 함수
                int resultSize = resultList.size(); //리스트 크기 저장
                resultList.clear();
                while (true) {
                    try {
                        System.out.println("기록 삭제를 원할 시 delete, 기록 조회를 원할 시 select를 입력해주세요: ");
                        String isRemove = scanner.nextLine();
                        if (!isRemove.isEmpty()) {
                            if (isRemove.equals("delete")) {
                                while (true) {
                                    try {
                                        System.out.println("삭제할 데이터 개수를 입력하세요");
                                        int removeNum = scanner.nextInt();
                                        scanner.nextLine();
                                        if (removeNum < 0) {
                                            throw new IllegalArgumentException("음수는 입력할 수 없습니다.");
                                        } else {
                                            if (removeNum > resultSize) {
                                                System.out.println("입력값이 저장된 데이터 개수보다 커, 전체 기록을 삭제합니다.");
                                                removeNum = resultSize;
                                            }
                                            for (int i = 0; i < removeNum; i++) {
                                                arithmeticCalculator.removeResult();
                                            }
                                            System.out.println("삭제가 완료되었습니다.");

                                            resultList = arithmeticCalculator.getResultList();
                                            app.printListResults(resultList);
                                            resultList.clear();

                                            break;
                                        }
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("오류(기록삭제) : " + e);
                                    } catch (InputMismatchException e) {
                                        System.out.println("오류(기록삭제) : 잘못된 입력입니다.");
                                    }
                                }
                                break;
                            }
                            else if (isRemove.equals("select")) {
                                while (true) {
                                    try {
                                        System.out.println("조회할 값을 입력하세요(해당 값 이상의 기록이 출력됩니다.) : ");
                                        int pointNum = scanner.nextInt();
                                        scanner.nextLine();
                                        resultList = arithmeticCalculator.selectList(pointNum);
                                        app.printListResults(resultList);
                                        resultList.clear();
                                        break;
                                    } catch (InputMismatchException e) {
                                        System.out.println("오류(기록조회) : 잘못된 입력입니다.");
                                        scanner.nextLine();
                                    }
                                }
                                break;
                            } else {
                                throw new IllegalArgumentException("그런 기능은 없습니다. 넘어가려면 enter키를 눌러주세요.");
                            }
                        } else {
                            break;
                        }
                    }catch (IllegalArgumentException e){
                        System.out.println("오류(기록관련기능) : " + e);
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
