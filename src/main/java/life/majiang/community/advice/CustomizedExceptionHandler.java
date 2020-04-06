package life.majiang.community.advice;

import life.majiang.community.exception.CustomizedException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

//@EnableWebMvc
@ControllerAdvice
public class CustomizedExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model) {
        HttpStatus status = getStatus(request);
        ModelAndView mv = new ModelAndView("error");
        if (ex instanceof CustomizedException) {
//            System.out.println(ex.getMessage());
            mv.addObject("message", ex.getMessage());
        }else {
            mv.addObject("message", "服务冒烟了，请稍候再试吧~");
        }
        return mv;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
