import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import dummy.Contact;
import dummy.ContactSource;

/**
 * 스트림 API 활용편
 * @author libedi
 *
 */
public class TestSreamApiExample {
	
	/**
	 * 뉴욕 주로 등록된 연락처만 출력
	 */
	public void printNewyork(){
		List<Contact> contacts= new ContactSource().findAll();
		
		// for 구문
		for(Contact contact : contacts){
			if(contact.equalsToState("New York")){
				System.out.println(contact);
			}
		}
		
		/*
		 * 조건을 만족하는 요소로 구성된 스트림을 얻을 때
		 * - 중개 연산 : Stream<T> filter(T -> boolean)
		 * - 각 요소를 확인해서 조건을 통과한 요소만으로 새 스트림 생성
		 * - 참/거짓을 반환하는 조건식을 인수로 전달
		 */
		// Stream API(filter) + Lambda Expressions
		contacts.stream()
				.filter(contact -> contact.equalsToState("New York"))
		/*
		 * 각 요소별로 어떤 처리를 하고 싶을 때
		 * - 최종 연산 : void forEach(T -> void)
		 * - 각 요소를 인수로 전달된 함수에 전달해 처리
		 * - 최종 연산이기 때문이 이후 스트림을 사용할 수 없음
		 */
				// Stream API(filter, forEach) + Lambda Expressions
				.forEach(contact -> System.out.println(contact));
	}
	
	/**
	 * 연락처에서 이메일만 출력
	 */
	public void printEmail(){
		List<Contact> contacts= new ContactSource().findAll();

		// for 구문
		for(Contact contact : contacts){
			System.out.println(contact.getEmail());
		}
		
		// Stream API + Lambda Expressions
		contacts.stream()
				.forEach(contact -> System.out.println(contact.getEmail()));
		/*
		 * 스트림에 있는 값들을 변환하고 싶을 때
		 * - 중개 연산 : Stream<R> map(T -> R)
		 * - T 타입의 요소를 1:1로 R 타입의 요소로 변환 후 스트림 생성
		 * - 변환을 처리하는 함수를 인수로 전달
		 */
		// Stream API + Lambda Expressions
		contacts.stream()
				.map(contact -> contact.getEmail())
				.forEach(email -> System.out.println(email));
		
		// Stream API + Method Reference
		contacts.stream()
				.map(Contact::getEmail)				// 메소드 참조 :
				.forEach(System.out::println);		// 람다 표현식의 다른 형태. class::method 로 사용.
	}
	
	/**
	 * 1부터 100까지 합계 구하기
	 */
	public void sum1To100(){
		// for 구문
		int sum1 = 0;
		for(int number = 1; number <= 100; number++){
			sum1 += number;
		}
		
		/*
		 * 스트림의 값들을 결합 또는 계산하고 싶을 때
		 * - 최종 연산 : reduce
		 * - Optional<T> reduce((T, T) -> T)
		 * - T reduce(T, (T, T) -> T)
		 * - T 타입의 요소 둘 씩 reducer로 계산해 최종적으로 하나의 값을 계산
		 */
		// Stream API + Lambda Expressions
		int sumSeq = IntStream.rangeClosed(1, 100)
							.reduce(0,  (left, right) -> left + right);		// 1부터 100까지 순차적으로 구함
		
		// Stream API (parallel) + Lambda Expressions 
		int sumPar = IntStream.rangeClosed(1, 100)
							.parallel()
							.reduce(0, (l, r) -> l + r);					// 1부터 10, 10부터 20, .. 의 합을 병렬로 구하여 sum
		
		// Stream API + Method Reference
		int sumMr = IntStream.rangeClosed(1, 100)
							.reduce(0, Integer::sum);						// Integer.sum() 메서드 구현도 가능
	}
	
	/**
	 * 사람들의 나이 합계, 평균, 최소, 최대 구하기
	 */
	public void totalProcessing(){
		List<Contact> contacts= new ContactSource().findAll();
		
		long sum1 = 0;
		int min1 = 0, max1 = 0;
		for(Contact contact : contacts){
			int age = contact.getAge();
			sum1 += age;
			min1 = Math.min(min1, age);
			max1 = Math.max(max1, age);
		}
		double average1 = sum1 / contacts.size();
		
		/*
		 * 기본 타입 스트림이 제공하는 편리한 계산식
		 * - 최종 연산 : sum() | average() | min() | max()
		 * - 합계 : [int, long, double] sum()
		 * - 평균 : OptionalDouble average()
		 * - 최소값 : Optional[Int, Long, Double] min()
		 * - 최대값 : Optional[Int, Long, Double] max()
		 */
		// Stream API + Method Reference : 4번이나 반복......
		int sum 				= contacts.stream().mapToInt(Contact::getAge).sum();
		OptionalDouble average 	= contacts.stream().mapToInt(Contact::getAge).average();
		OptionalInt min			= contacts.stream().mapToInt(Contact::getAge).min();
		OptionalInt max			= contacts.stream().mapToInt(Contact::getAge).max();
		
		/*
		 * 기본 타입 스트림이 제공하는 편리한 계산식
		 * - 최종 연산 : summaryStatistics()
		 * - [Int, Long, Double]SummaryStatistics summaryStatistics()
		 * - 합계, 평균, 최소값, 최대값, 개수에 대한 요약 통계
		 */
		// Stream API + Method Reference
		IntSummaryStatistics summaryStatistics = 
				contacts.stream()
						.mapToInt(Contact::getAge)
						.summaryStatistics();
		
		long sumSs 			= summaryStatistics.getSum();
		double averageSs 	= summaryStatistics.getAverage();
		int minSs			= summaryStatistics.getMin();
		int maxSs			= summaryStatistics.getMax();
		long count			= summaryStatistics.getCount();
	}
	
	/**
	 * 연락처에서 도시 목록 구하기
	 */
	public void getCityList(){
		List<Contact> contacts= new ContactSource().findAll();
		
		// for 구문
//		List<String> cities = new ArrayList<>();
//		for(Contact contact : contacts){
//			String city = contact.getCity();
//			cities.add(city);
//		}
		
		/*
		 * 스트림 연산 후 결과를 살펴보고 싶을 때
		 * - 최종 연산 : 집계
		 * - iterator()
		 * - Object[] toArray()
		 * - A[] toArray(IntFunction<A[]>)
		 * - R collect(() -> R, (R, T) -> R, (R, R) -> void)
		 * 		: () -> R 			= 공급자. 대상 객체의 새로운 인스턴스 생성 (ex. ArrayList 생성)
		 * 		: (R, T) -> R 		= 누산자. 요소를 대상에 추가 (ex. List.add(..))
		 * 		: (R, R) -> void	= 결합자. 두 객체를 하나로 병합 (ex. List.addAll(..))
		 * - R collect(Collector<T, ?, R>)
		 */
		// 반복자로 집계
		Iterator<Contact> contactIterator = contacts.stream().iterator();
		
		// 배열로 집계
		Object[] objectArray = contacts.stream().toArray();
		Contact[] contactArray = contacts.stream().toArray(Contact[]::new);
		
		// Stream API(collect(공급자, 누산자, 결합자)) + Lambda expressions
		List<String> cities = contacts.stream()
									.map(contact -> contact.getCity())		// contact -> city 로 스트림 값 변경
									.collect(() -> new ArrayList<>()					// 대상 객체 생성 : left
											, (list, city) -> list.add(city)			// 요소를 대상 객체와 동일한 객체에 추가 : right
											, (left, right) -> left.addAll(right));		// 두 객체를 병합 : left + right
		
		// Stream API(collect(공급자, 누산자, 결합자)) + Method Reference
		cities = contacts.stream()
						.map(contact -> contact.getCity())
						.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		
		// Stream API(collect(Collector interface)) + Method Reference
		cities = contacts.stream()
						.map(contact -> contact.getCity())
						.distinct()		// 중복 제거
						.collect(Collectors.toList());
		/*
		 * Collectors : 공통 컬렉터용 팩토리 메소드를 제공
		 */
		// Collection 타입으로 요소를 모을 때
		List<Contact> list = contacts.stream().collect(Collectors.toList());
		Set<Contact> set = contacts.stream().collect(Collectors.toSet());
		TreeSet<Contact> treeSet = contacts.stream().collect(Collectors.toCollection(TreeSet::new));
		
		// Map 타입으로 요소를 모을 때 (K,V)
		Map<String, String> nameBirthMap = contacts.stream().collect(Collectors.toMap(Contact::getName, Contact::getBirthday));
		Map<String, Contact> nameObjMap = contacts.stream().collect(Collectors.toMap(Contact::getName, Function.identity()));
		
		// 스트림에 있는 모든 문자열을 서로 연결해서 모을 때
		String joinString = contacts.stream().map(Contact::getName).collect(Collectors.joining());
		String joinStringClf = contacts.stream().map(Contact::getName).collect(Collectors.joining("|"));
	}

	public static void main(String[] args) {

	}

}
