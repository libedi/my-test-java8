import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import dummy.Contact;
import dummy.ContactSource;
import dummy.Gender;

/**
 * 씹고 뜯고 맛보고 즐기는 스트림 API 예제
 * <a href="http://www.slideshare.net/arawnkr/api-42494051">링크</a>
 * @author libedi
 *
 */
public class TestStreamApi2 {

	/**
	 * 내 연락처에 등록된 사람들 중 "플로리다"에 사는 "남자들"의 "평균나이"는?<br>
	 * -> 명시적 반복을 통해 요구사항 구현<br>
	 * -> 명령형 프로그래밍 : "어떻게"가 아닌, "무엇을" 계산하는지가 "중요"
	 */
	public void test1OldVersion(){
		List<Contact> contacts = new ContactSource().findAll();
		int manCount = 0;
		int totalAge = 0;
		for(Contact contact : contacts){
			if("Florida".equals(contact.getState()) && Gender.Male == contact.getGender()){
				manCount++;
				totalAge += contact.getAge();
			}
		}
		double average = totalAge / manCount;
		System.out.println("OLD VERSION : " + average);
	}
	
	/**
	 * 스트림 API와 람다 표현식으로 요구사항 구현<br>
	 * -> 파이프-필터 기반 API
	 */
	public void test2UseStreamRhamda(){
		List<Contact> contacts = new ContactSource().findAll();
		OptionalDouble optDbl = contacts.stream()
										.filter(c -> "Florida".equals(c.getState()))
										.filter(c -> Gender.Male == c.getGender())
										.mapToInt(c -> c.getAge())
										.average();
		double average = optDbl.getAsDouble();
		System.out.println("STREAM API + RHAMDA EXP : " + average);
	}
	
	/**
	 * 스트림과 콜렉션은 다르다.<br>
	 * <br>
	 * 1. Collection<br>
	 * - 외부 반복 (External Iteration)<br>
	 * - 반복을 통해 생성<br>
	 * - 효율적이고 직접적인 요소 처리<br>
	 * - 유한 데이터 구조<br>
	 * - 반복적 재사용 가능<br>
	 * <br>
	 * 2. Stream<br>
	 * - 내부 반복 (Internal Iteration)<br>
	 * - 반복을 통해 연산<br>
	 * - 파이프-필터 기반 API<br>
	 * - 무한 연속 흐름 데이터 구조<br>
	 * - 재사용 불가
	 */
	public void test3DifferStreamAndCollection(){}
	
	/**
	 * <pre>
	 * 1. 스트림 API의 목적
	 * - 데이터 보관이 아닌 "데이터 처리"에 집중
	 * 
	 * 2. 스트림 API의 특징
	 * - 반복의 내재화
	 * 		: 반복 구조 캡슐화 (제어 흐름 추상화, 제어의 역전)
	 * 		: 최적화와 알고리즘 분리
	 * - 지연 연산
	 * 		: 스트림을 반환하는 필터-맵 API 는 기본적으로 지연(lazy) 연산
	 * 		: 지연 연산을 통한 성능 최적화 (무상태 중개 연산 및 반복 작업 최소화)
	 * - 병렬 처리
	 * 		: 동일한 코드로 순차 또는 병렬 연산을 쉽게 처리
	 * 		: 쓰레드에 안전하지 않은 컬렉션도 병렬 처리 지원
	 * 		: 단, 스트림 연산 중 데이터 원본을 변경하면 안된다.  ------> 중요!
	 * 
	 * 3. 스트림 인터페이스 : java.util.stream
	 * - Stream<T>		: 객체를 요소로 하는 범용 스트림
	 * - IntStream		: int를 요소로 하는 범용 스트림
	 * - LongStream		: long을 요소로 하는 범용 스트림
	 * - DoubleStream	: double를 요소로 하는 범용 스트림
	 * 
	 * 4. 2가지 유형의 스트림 인터페이스
	 * - 순차 스트림 (stream.parallel()) ----> <---- (stream.sequential()) 병렬 스트림
	 * </pre>
	 * 
	 */
	public void test4StreamApi(){}
	
	/**
	 * 5. 신뢰할 수 없는 반환값
	 */
	public void test5NonTrustReturnValue(){
		List<Contact> contacts = new ContactSource().findAll();
		Contact contact = contacts.stream()
								.filter(c -> "Florida".equals(c.getState()))
								.findFirst()	// 스트림에서 필터 연산 후, 연락처(Contact) 객체를 찾아줘.
								.get();			// 사실 요건 없다고 생각하고.
		
//		contact.call();			// contact == null 이면...? NullPointerException 발생!
		if(contact != null){	// 방어코드로 회피
			contact.call();
		}
	}
	
	/**
	 * 5. 신뢰할 수 있는 반환값 : java.util.Optional<T>
	 * - Java 8에서 새롭게 추가된 클래스
	 * - 연산 후 반환값이 있을 수도, 없을 수도 있을 때 사용
	 * - 반환값이 객체 또는 null인 T보다 안전한 대안
	 */
	public void test5trustReturnValue(){
		List<Contact> contacts = new ContactSource().findAll();
		/*
		 * 1. Optinal : 반환값이 없을 수도 있어. 그러니 꼭 확인해봐 (명시적으로 알림)
		 */
		Optional<Contact> optional = contacts.stream()
											.filter(c -> "Florida".equals(c.getState()))
											.findFirst();
		/*
		 * 값이 있으면 true,
		 * 값이 없으면 false
		 */
		if(optional.isPresent()){
			optional.get().call();
		}
		
		/*
		 * 2.
		 */
		contacts.stream()
				.filter(c -> "Florida".equals(c.getState()))
				.findFirst()
				.ifPresent(c -> c.call());		// 값이 있으면 "넘겨준 람다를 실행"해줘.
		
		/*
		 * 3.
		 */
		Contact contact = contacts.stream()
								.filter(c -> "Florida".equals(c.getState()))
								.findFirst()
								.orElseGet(() -> Contact.empty());		// 값이 있으면 "정상 값을 반환"하고,
																		// 없으면 "기본 값을 반환"해줘!
	}
	
	/**
	 * 6. 분할 반복자 인터페이스 : java.util.Spliterator<T>
	 * - 스트림 API 에 사용되는 새로운 클래스
	 * - 병렬 실행시 컬렉션의 데이터 요소를 분할 처리
	 */
	public void test6Spliterator(){
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		
		Spliterator<Integer> firstSplit = numbers.spliterator();
		Spliterator<Integer> secondSplit = firstSplit.trySplit();
		
		// firstSplit = 5, 6, 7, 8
		// secondSplit = 1, 2, 3, 4
		
		System.out.println("fisrtSplit :");
		firstSplit.forEachRemaining(t -> System.out.println(t));
		System.out.println("secondSplit :");
		secondSplit.forEachRemaining(t -> System.out.println(t));
	}
	
	/**
	 * 7. 스트림 API 의 3단계
	 * - order.stream().map(n -> n.price).sum()
	 * -    스트림생성 |중개연산(스트림변환)|최종연산(스트림사용)
	 */
	public void test7StreamApiProcess(){
		/*
		 * 1단계 - 스트림 생성
		 * 		: Collection	- .streams(), .parallelStream()
		 * 		: Arrays		- Arrays.streams(*)
		 * 		: Stream ranges	- range(...), rangeClosed(...)
		 * 		: Directly from values - of(*)
		 * 		: Generators	- iterate(...), generate(...)
		 * 		: Resources		- lines()
		 */
		// 컬렉션(List)으로 순차 스트림 생성
		List<String> collection = new ArrayList<>();
		Stream<String> stream = collection.stream();	// 순차스트림 생성 : 내부적으로 Spliterator.spliterator() 로 스트림 생성
		
		// 배열을 스트림으로 변환
		int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8};
		IntStream intStream = Arrays.stream(numbers);
		
		// of 메소드는 가변인자로 스트릠을 생성 가능
		Stream<String> ofStream = Stream.of("Using", "Stream", "API", "From", "Java8");
		
		// 1부터 10까지 유한 스트림 생성
		LongStream longStream = LongStream.rangeClosed(1, 10);
		
		
		/*
		 * 2단계 - 중개 연산자 (Intermediate Operator)
		 * 		: 스트림을 받아서 스트림을 반환
		 * 		: 무상태 연산과 내부 상태 유지 연산으로 나누어짐
		 * 		: 기본적으로 "지연(lazy) 연산 처리" (성능 최적화)
		 */
		List<Contact> contacts = new ContactSource().findAll();
		contacts.stream()
				.filter(c -> "Florida".equals(c.getState()))	// 중개 연산자는
				.filter(c -> Gender.Male == c.getGender())		// 최종 연산자 실행 전에는
				.mapToInt(c -> c.getAge());						// 연산 처리하지 않는다.
		
		/*
		 * 3단계 - 최종 연산자 (Terminal Operator)
		 * 		: 스트림의 요소들을 연산 후 결과 값을 반환
		 * 		: 최종 연산 시 모든 연산 수행 (반복 작업 최소화)
		 * 		: 이후 더 이상 스트림을 사용할 수 없음
		 */
		contacts.stream()
				.filter(c -> "Florida".equals(c.getState()))
				.filter(c -> Gender.Male == c.getGender())
				.mapToInt(c -> c.getAge())
				.average();						// 평균 값을 "계산 후 반환" 하는 최종 연산자
	}
	
	/**
	 * 스트림 API 정리
	 */
	public void summary(){
		/*
		 * 스트림 API 3단계
		 */
//		orders.stream().map(n->n.price).sum();
		//   스트림생성|중개연산(변환) |최종연산(스트림사용)
		
		/*
		 * 스트림 생성
		 * - Collection 	: stream(), parallelStream()
		 * - Arrays			: stream(*)
		 * - Stream ranges	: range(...), rangeClosed(...)
		 * - Directly from values : of(*)
		 * - Generators		: iterator(..), generate(..)
		 * - Resources		: lines()
		 */
		// Collection
		List<String> collection = new ArrayList<>();
		
		Stream<String> stream = collection.stream();					// 순차 스트림
		Stream<String> parallelStream = collection.parallelStream();	// 병렬 스트림
		
		// Arrays
		int[] numbers = new int[]{1, 2, 3, 4, 5, 6, 7};
		
		IntStream intStream = Arrays.stream(numbers);		// 배열을 스트림으로 변환
		IntStream pIntStream = intStream.parallel();		// 순차 스트림을 병렬 스트림으로 변환
		
		// Directly from values
		Stream<String> ofStream = Stream.of("Using", "Stream", "API", "From", "Java8");	// of 메소드는 가변인자로 스트림을 생성 가능
		Stream<String> pOfStream = ofStream.parallel();									// 순차 스트림을 병렬 스트림으로 변환
		
		// Stream ranges
		IntStream rangeStream = IntStream.range(1, 10);			// 1부터 9까지 유한 스트림 생성
		LongStream longStream = LongStream.rangeClosed(1, 10);	// 1부터 10까지 유한 스트림 생성
		
		// Generators : 무한 스트림 (Infinite Stream)
		Stream<Double> randoms = Stream.generate(Math::random);				// 난수 스트림
		Stream<Integer> infiniteNumbers = Stream.iterate(0, n -> n + 1);	// 0 1 2 3 ... 무한 수열
		Stream<int[]> fibonacci = Stream.iterate(new int[]{0, 1},  n -> new int[]{n[1], n[0] + n[1]});	// 피보나치 수열
		fibonacci.limit(10)
				 .map(n -> n[0])
				 .forEach(System.out::println);
		
		// Resources
		// CSV 파일을 읽어 스트림을 생성
		try(Stream<String> lines = Files.lines(Paths.get("addressBook.csv"))) {
			long count = lines.map(line -> line.split(","))
								.filter(values -> values[6].contains("FL"))
								.count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		TestStreamApi2 api = new TestStreamApi2();
		api.test6Spliterator();
	}
	
}
