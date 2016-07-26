package dummy;

import java.util.Objects;

public class Contact {
	private String state;
	private Gender gender;
	private int age;
	private String email;
	private String city;
	private String name;
	private String birthday;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void call() {
		
	}

	public static Contact empty() {
		return new Contact();
	}

	public boolean equalsToState(String state) {
		return Objects.equals(state, getState());
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "Contact [state=" + state + ", gender=" + gender + ", age=" + age + "]";
	}

}
