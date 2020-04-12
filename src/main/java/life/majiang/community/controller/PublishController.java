package life.majiang.community.controller;

import life.majiang.community.cache.TagCache;
import life.majiang.community.dto.QuestionDto;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    /**
     * 浏览器发来/publish请求，跳转到publish.html GET方式
     */
    @GetMapping("publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    /**
     * 将publish.html页面的内容提交到服务器 POST方式
     * 可以是 发布 或 编辑
     * @param id questionId 发布时为null，编辑时由前端页面传过来
     * @param title question title
     * @param description question content
     * @param tag question tags
     * @return 发布或编辑成功 返回首页
     */
    @PostMapping("publish")
    public String doPublish(@RequestParam("questionId") Long id,
                            @RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model){
        User user;

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        if (title == null || title.equals("")){
            model.addAttribute("error", "问题标题不能为空!");
            return "publish";
        }
        if (description == null || description.equals("")){
            model.addAttribute("error", "问题补充不能为空!");
            return "publish";
        }
        if (tag == null || tag.equals("")){
            model.addAttribute("error", "问题标签不能为空!");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)){
            model.addAttribute("error", "输入包含非法标签: "+invalid);
            return "publish";
        }

        //获取cookie，判断用户是否已登录
        user = (User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error", "用户未登录！");
            return "publish";
        }

        //user不为null，已登录，执行下面操作
        Question question = new Question();
        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());

        questionService.createOrUpdate(question);

        return "redirect:/";

    }

    /**
     * 编辑功能
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDto questionDto = questionMapper.listById(id);
        model.addAttribute("title", questionDto.getTitle());
        model.addAttribute("description", questionDto.getDescription());
        model.addAttribute("tag", questionDto.getTag());
        model.addAttribute("questionId", id);
        //内置可供选择的tag
        model.addAttribute("tags", TagCache.get());

        return "publish";
    }
}
