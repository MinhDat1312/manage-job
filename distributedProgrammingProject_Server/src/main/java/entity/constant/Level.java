package entity.constant;

import java.io.Serializable;

public enum Level implements Serializable {
	COLLEGE("Cao đẳng"), UNIVERSITY("Đại học"), ENGINEER("Kỹ sư"), SCHOOL("THPT");
	
	private final String value;

	private Level(String value){
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}
