import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

public class TestStreamApiClass {

	/**
	 * Old version : HOW 중심
	 */
	public void oldVersion(){
		int sum = 0;
		int count = 0;
		Integer[] numArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		List<Integer> list = Arrays.asList(numArr);
		for(Integer num : list){					// num 리스트에서 개별 num을 구해서
			if(num > 5){							// num 이 5보다 크면
				sum += num;							// sum에 num을 더하고 개수를 1증가
				count++;
			}
		}
		double avg = (double) sum / count;			// sum과 count로 평균 계산
		System.out.println("OLD VERSION : " + avg);
	}
	
	/**
	 * JAVA8 STREAM API version : WHAT을 기술
	 */
	public void java8Version(){
		Integer[] numArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		List<Integer> list = Arrays.asList(numArr);		
		OptionalDouble avgOpt = 
				list.stream()					// num list 중에서
					.filter(x -> x > 5)			// num이 5보다 큰 num의 : Predicate 람다식 구현
					.mapToInt(x -> x)			// num을 구해서			: ToIntFunction 람다식 구현
					.average();					// 평균을 구함
		double avg = avgOpt.getAsDouble();
		System.out.println("JAVA8 VERSION : " + avg);
	}
	
	/*
	 * 1. 스트림 API 의 구성
	 *     list.stream().filter(x -> x > 5).count();
	 * 스트림 생성 | 중개연산(스트림변환) | 종단 연산(스트림 사용)
	 * 
	 * 2. 스트림 종류
	 * - Stream<T> 		: 범용 스트림
	 * - IntStream 		: 값 타입이 int 인 스트림
	 * - LongStream 	: 값 타입이 long 인 스트림
	 * - DoubleStream 	: 값 타입이 double 인 스트림
	 * 
	 * 3. 스트림 생성
	 * - Collection 	: 컬렉션객체.stream()
	 * - Files 			: Stream<String> Files.lines()
	 * - BufferedReader : Stream<String> lines()
	 * - Arrays 		: Arrays.stream(*)
	 * - Random			: Random.doubles(*), Random.ints(*), Random.longs(*)
	 * - Stream
	 * 		: Stream.of(*)
	 * 		: range(start, end), rangeClosed(start, end) > IntStream, LongStream 에서 제공
	 * 		: Stream.generate(Supplier<T> s)
	 * 		: Stream.iterate(T seed, UnaryOperator<T> f)
	 * 
	 * 4. 스트림 중개 연산 - 주요 중개 연산 : 상태없음 (Stateless)
	 * - Stream<R> map(Function<? super T, ? extends R> mapper)
	 * 		: 입력 T 타입 요소를 R 타입 요소로 변환한 스트림 생성
	 * - Stream<T> filter(Predicate<? super T> predicate)
	 * 		: 조건을 충족하는 요소를 제공하는 새로운 스트림 생성
	 * - Stream<T> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
	 * 		: T 타입 요소를 1:N의 R 타입 요소로 변환한 스트림 생성
	 * - Stream<T> peek(Consumer<? super T> action)
	 * 		: T 타입 요소를 사용만 하고, 기존 스트림을 그대로 제공하는 스트림 생성
	 * - Stream<T> skip(long n)
	 * 		: 처음 n개의 요소를 제외하는 스트림 생성
	 * - Stream<T> limit(long maxSize)
	 * 		: maxSize 까지의 요소만 제공하는 스트림 생성
	 * - mapToInt(), mapToLong(), mapToDouble()
	 * 
	 * 5. 스트림 중개 연산 - 주요 중개 연산 : 상태있음
	 * - sorted(), sorted(Comparator<T> comparator)
	 * 		: 정렬된 스트림을 생성
	 * 		: 전체 스트림의 요소를 정렬하기 때문에, 무한 스트림에 적용할 수 없음
	 * - distinct()
	 * 		: 같은 값을 갖는 요소를 중복해서 발생하지 않는 스트림 생성
	 */
	
//	// 3번 예제
//	public void simpleSample() throws IOException{
//		Path path = Paths.get("/resources/apache.log");
//		try(Stream<String> lines = Files.lines(path)){
//			OptionalDouble optionalDouble = 
//					lines
//						.map(s -> parseApacheLog(s))
//						.filter(log -> log.getStatusCode() == 200)
//						.mapToInt(log -> log.getResponseTime())
//						.average();
//			
//			System.out.println(optionalDouble.isPresent() ? optionalDouble.getAsDouble() : "none");
//		}
//	}
	
	public static void main(String[] args){
		TestStreamApiClass test = new TestStreamApiClass();
		test.oldVersion();
		test.java8Version();
	}
}
