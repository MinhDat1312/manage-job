package entity.constant;

import java.io.Serializable;

public enum Role implements Serializable {
    NONE("Chưa có"), ADMIN("Admin"), EMPLOYEE("Nhân viên");

    private final String value;
    Role(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
