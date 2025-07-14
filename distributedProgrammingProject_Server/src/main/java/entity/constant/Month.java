package entity.constant;

import java.io.Serializable;

public enum Month implements Serializable {
	JANUARY("Tháng 1"), FEBRUARY("Tháng 2"), MARCH("Tháng 3"), APRIL("Tháng 4"),
	MAY("Tháng 5"), JUNE("Tháng 6"), JULY("Tháng 7"), AUGUST("Tháng 8"),
	SEPTEMBER("Tháng 9"), OCTOBER("Tháng 10"), NOVEMBER("Tháng 11"), DECEMBER("Tháng 12");;
	
	private final String value;
	Month(String value) {
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}
