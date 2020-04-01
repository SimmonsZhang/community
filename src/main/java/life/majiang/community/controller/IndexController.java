package life.majiang.community.controller;

import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.QuestionDto;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String hello(HttpServletRequest request, Model model,
                        @RequestParam(required = false,defaultValue = "1",value = "pageNum") Integer pageNum,
                        @RequestParam(defaultValue = "2",value = "pageSize") Integer pageSize){

        System.out.println("当前页是："+pageNum+"显示条数是："+pageSize);

        PageInfo<QuestionDto> pageInfo = questionService.list(pageNum, pageSize);
        System.out.println("总页数："+pageInfo.getPages()+"总记录数："+pageInfo.getTotal());
        model.addAttribute("pageInfo", pageInfo);

        return "index";
    }
}
