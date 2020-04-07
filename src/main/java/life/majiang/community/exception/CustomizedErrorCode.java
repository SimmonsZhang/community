package life.majiang.community.exception;

public enum CustomizedErrorCode implements ICustomizedErrorCode {

    QUESTION_NOT_FOUND(2001, "没有找到该问题，换一个试试吧~"),
    NOT_LOGIN(2002, "请先登录在进行该操作~"),     //页面提示
    CONTENT_IS_EMPTY(2003, "评论内容不能为空~"),    //页面提示
    TARGET_PARAM_NOT_FOUND(2004,"未选中任何问题或评论进行回复"),
    TYPE_PARAM_WRONG(2005, "评论类型错误~"),
    COMMENT_NOT_FOUND(2006, "你回复的评论不存在啦~"),
    SYS_ERROR(2007, "服务冒烟了，等一会再来吧~");

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
