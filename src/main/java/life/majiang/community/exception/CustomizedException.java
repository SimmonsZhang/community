package life.majiang.community.exception;

public class CustomizedException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizedException(ICustomizedErrorCode customizedErrorCode) {
        this.message = customizedErrorCode.getMessage();
        this.code = customizedErrorCode.getCode();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
