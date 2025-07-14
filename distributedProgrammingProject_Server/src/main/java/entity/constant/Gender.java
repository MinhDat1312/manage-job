package entity.constant;

import java.io.Serializable;

public enum Gender implements Serializable {
    MALE("Nam"), FEMALE("Nữ"), OTHER("Khác");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
