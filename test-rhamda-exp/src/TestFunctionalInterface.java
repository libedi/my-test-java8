
@FunctionalInterface
public interface TestFunctionalInterface<T> {
	public T function1(T op1, T op2);
	
	/*
	 * @FunctionalInterface 을 사용하면 인터페이스가 추상 메서드를
	 * 두개 이상 갖을 경우 컴파일 오류 발생! 
	 */
//	public T function2(T op1, T op2);
}
