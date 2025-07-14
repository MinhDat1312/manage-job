package entity.constant;

import java.io.Serializable;

public enum Status implements Serializable {
    NONE("Chưa nộp"), PENDING("Đang xét"), ACCEPTED("Đã duyệt"), REJECTED("Từ chối");

    private final String value;

    private Status(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
