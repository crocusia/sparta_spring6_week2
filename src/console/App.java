package console;

import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


// 콘솔창을 이용한 계산기 구현 코드
public class App {

    static ArithmeticCalculator arithmeticCalculator = new ArithmeticCalculator();

    //실수형으로 저장된 정수를 출력하는 메서드
    public static void printResult(Number result){
        if(result.doubleValue() % 1 == 0){
            System.out.printf("%.0f \n", result.doubleValue());
        }
        else {
            System.out.println(result);
        }
    }

    //리스트를 출력하는 메서드
    public static void printListResults(List<Number> resultList){
        System.out.println("-----record-----");
        for(Number r : resultList){
            printResult(r);
        }
        System.out.println("----------------");
    }

    //연산 결과가 저장된 리스트의 원소를 삭제하는 메서드
    public static void deleteRecord(int removeNum){
        if (removeNum < 0) {
            throw new IllegalArgumentException("음수는 입력할 수 없습니다.");
        }
        else {
            int size = arithmeticCalculator.getResultSize();
            //입력값이 저장된 데이터 개수보다 큰 경우, 삭제할 데이터의 개수를 저장된 데이터의 개수로 설정함
            if (removeNum > size) {
                System.out.println("입력값이 저장된 데이터 개수보다 커, 전체 기록을 삭제합니다.");
                removeNum = size;
            }
            //맨 앞의 원소를 삭제함
            for (int i = 0; i < removeNum; i++) {
                arithmeticCalculator.removeResult();
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
                    System.out.print("연산할 첫 번째 값을 입력해주세요 : ");
                    Number num1 = scanner.nextDouble();
                    System.out.print("연산할 두 번째 값을 입력해주세요 : ");
                    Number num2 = scanner.nextDouble();
                    System.out.print("연산기호(+, -, *, /)를 입력해주세요 : ");
                    char operator = scanner.next().charAt(0);
                    scanner.nextLine();

                    //사칙연산
                    Number result = arithmeticCalculator.calculate(num1, num2, operator);
                    arithmeticCalculator.saveResult(result);
                    printResult(result);

                    isCalculate = false;
                }
                catch (InputMismatchException e) { //정수가 잘못 입력된 경우
                    System.out.println("오류(입력): 잘못된 입력입니다.");
                    scanner.nextLine();
                } catch (ArithmeticException e) { // 0으로 나누는 경우
                    System.out.println("오류(입력): 0으로 나눌 수 없습니다.");
                } catch (IllegalArgumentException e) { //연산 기호가 잘못 입력된 경우
                    System.out.println("오류(입력): 잘못된 입력 입니다 ->" + e.getMessage());
                } catch (Exception e) { // 기타 예외 처리
                    System.out.println("알 수 없는 오류 발생(입력): " + e.getMessage());
                    scanner.nextLine();
                    break;
                }
            }

            //기록 조회
            System.out.println("연산 기록을 확인하고 싶다면 record를 입력해주세요: ");
            String isRecord = scanner.nextLine();
            if (isRecord.equals("record")) {
                printListResults(arithmeticCalculator.getResultList()); //리스트 내용을 출력 후 초기화
                try {
                    System.out.println("기록 삭제를 원할 시 delete, 기록 조회를 원할 시 select를 입력해주세요: ");
                    String isRemove = scanner.nextLine();
                    if (!isRemove.isEmpty()) {
                        if (isRemove.equals("delete")) {
                            boolean isDelete = true;
                            while (isDelete) {
                                try {
                                    System.out.println("삭제할 데이터 개수를 입력하세요");
                                    int removeNum = scanner.nextInt();
                                    scanner.nextLine();
                                    //기록 삭제 메서드 호출
                                    deleteRecord(removeNum);
                                    printListResults(arithmeticCalculator.getResultList());
                                    isDelete = false;
                                } catch (IllegalArgumentException e) {
                                    System.out.println("오류(기록삭제) : " + e);
                                } catch (InputMismatchException e) {
                                    System.out.println("오류(기록삭제) : 잘못된 입력입니다.");
                                }
                            }
                        } else if (isRemove.equals("select")) {
                            boolean isSelect = true;
                            while (isSelect) {
                                try {
                                    System.out.println("조회할 값을 입력하세요(해당 값 이상의 기록이 출력됩니다.) : ");
                                    Number pointNum = scanner.nextDouble();
                                    scanner.nextLine();
                                    //조건에 따라 조회된 리스트 내용 출력
                                    printListResults(arithmeticCalculator.selectList(pointNum));
                                    isSelect = false;
                                } catch (InputMismatchException e) {
                                    System.out.println("오류(기록조회) : 잘못된 입력입니다.");
                                }
                            }
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("오류(기록관련기능) : " + e);
                }
            }

            System.out.print("종료하려면 exit를 입력해주세요: ");
            String isEnd = scanner.nextLine();
            if (isEnd.equals("exit")) {
                isProcessing = false;
            }
        }
        scanner.close();
        System.out.println("계산기를 종료합니다.");
    }
}
