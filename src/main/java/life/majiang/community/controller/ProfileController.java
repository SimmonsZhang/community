package life.majiang.community.controller;

import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.QuestionDto;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    QuestionService questionService;

    @Autowired
    UserMapper userMapper;

    @GetMapping("profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize,
                          HttpServletRequest request,
                          Model model){

        if ("questions".equals(action)){
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
        }else if ("replies".equals(action)){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) return "redirect:/";

        PageInfo<QuestionDto> pageInfo = questionService.listByCreator(user.getId(), pageNum, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "profile";
    }
}
