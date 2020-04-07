package life.majiang.community.controller;

import life.majiang.community.dto.QuestionDto;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        questionService.incViewCount(id);
        QuestionDto questionDto = questionService.listById(id);
        model.addAttribute("questionDto",questionDto);

        return "question";
    }

}
