package life.majiang.community.controller;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.QuestionDto;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.service.CommentService;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @GetMapping("question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        questionService.incViewCount(id);
        QuestionDto questionDto = questionService.listById(id);
        List<CommentDTO> commentDTOs = commentService.listByParentId(Long.valueOf(id), CommentTypeEnum.QUESTION);

        model.addAttribute("questionDto",questionDto);
        model.addAttribute("commentDTOs",commentDTOs);

        return "question";
    }

}
