package entity.constant;

import java.io.Serializable;

public enum WorkingType implements Serializable {
	ONLINE("Online"), OFFLINE("Offline"), FULLTIME("Full-time"), PARTTIME("Part-time");
	
	private final String value;

	private WorkingType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
