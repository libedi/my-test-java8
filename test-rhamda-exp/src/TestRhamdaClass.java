import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;

public class TestRhamdaClass {
	public static void main(String[] args){
		
		/*
		 * 람다식은 인터페이스를 재정의할 때 간단하게 사용하는 방법으로 보임.
		 * 람다식 == 익명 메서드
		 */
		
		// 람다식 이전
		Comparator<Integer> compA = new Comparator<Integer>(){
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		};
		// 람다식 적용
		Comparator<Integer> compB = (o1, o2) -> o2 - o1;
		
		// 기본식
		Comparator<Long> comp1 = (o1, o2) -> o2 - o1 < 0 ? -1 : 1;
		int result1 = comp1.compare((long) 5, (long) 2);
		System.out.println("default : " + result1);
		
		// 파라미터 타입 지정
		Comparator<Long> comp2 = (Long o1, Long o2) -> Long.compare(o1, o2);
		
		// 파라미터 타입 지정하지 않음, 문맥에서 유추
		Comparator<Long> comp3 = (o1, o2) -> Long.compare(o1, o2);
		
		// 파라미터 한 개인 경우, 파라미터 목록에 괄호 생략
		Predicate<String> predicate = t -> t.length() > 10;
		boolean result2 = predicate.test("test");
		System.out.println("predicate : " + result2);
		
		// 파라미터가 없는 경우
		Callable<String> callable = () -> "noparam";
		try {
			System.out.println("callable : " + callable.call());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 결과값이 없는 경우
		Runnable runnable = () -> System.out.println("No Return");
		runnable.run();
		
		// 코드 블럭을 사용할 경우, return을 이용해서 결과값을 리턴
		Operator<Integer> plusSquareOp = (op1, op2) -> {
			int result = op1 + op2;
			return result * result;
		};
		System.out.println("plusSquareOp : " + plusSquareOp.operate(2, 5));
		
		/* 
		 * 프리변수 : 람다식의 파라미터나 식 내부에서 선언되지 않은 변수
		 * 프리변수의 값은 변경할 수 없음. 변경시 컴파일 에러
		 * 굳이 final 처리 안해줘도 됨.
		 */
		int multiple = 10;
//		multiple = 1;	// 변경시 컴파일 에러
		Operator<Integer> operator = (o1, o2) -> o1 * o2 * multiple;
		System.out.println("operator : " + operator.operate(3, 4));
		
		/*
		 * java.util.function 패키지에 미리 정의된 함수형 인터페이스가 정의됨
		 * - Function<T, R>
		 * - Predicate<T>
		 * - BIFunction<T, U, R>
		 * - Supplier<T>
		 * - Consumer<T>
		 */
		Function<String, Integer> func1 = t -> t.length();
		System.out.println("func1 : " + func1.apply("test"));
		
	}
}
