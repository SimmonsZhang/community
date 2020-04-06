package life.majiang.community.exception;

public enum CustomizedErrorCode implements ICustomizedErrorCode {

    QUESTION_NOT_FOUND(2001, "没有找到该问题，换一个试试吧~");

    private String message;
    private Integer code;

    CustomizedErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
