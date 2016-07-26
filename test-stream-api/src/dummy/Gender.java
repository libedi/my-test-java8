package dummy;

public enum Gender {
	Male("남성"),
	Female("여성")
	;
	
	private final String value;

	private Gender(String value) {
		this.value = value;
	}

}
