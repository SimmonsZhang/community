package life.majiang.community.dto;

import life.majiang.community.exception.CustomizedErrorCode;
import life.majiang.community.exception.CustomizedException;
import lombok.Data;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(CustomizedErrorCode errorCode) {
        return ResultDTO.errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    private static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);

        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizedException ex) {
        return ResultDTO.errorOf(ex.getCode(), ex.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("操作成功！");
        return resultDTO;
    }
}
