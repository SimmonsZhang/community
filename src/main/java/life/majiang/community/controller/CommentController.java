package life.majiang.community.controller;

import life.majiang.community.dto.CommentCreateDTO;
import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizedErrorCode;
import life.majiang.community.model.Comment;
import life.majiang.community.model.User;
import life.majiang.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     * 包括一级评论和二级评论
     * @param commentCreateDTO 根据页面comment封装的对象
     *                         包含parentId、content和type信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        //评论之前先登录
        //不抛出异常，只在页面提示
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizedErrorCode.NOT_LOGIN);
        }
        //评论内容不能为空
        //不抛出异常，只在页面提示
        if (StringUtils.isEmpty(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizedErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setCommentator(1);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        //被评论内容不存在、评论类型异常
        //这些应该作为异常抛出
        commentService.insertSelective(comment);

        return ResultDTO.okOf();
    }

    /**
     * 展示一级评论下的二级评论
     * @param id parentId 一级评论的id
     * @return ResultDTO对象的json，data域中存着所有二级评论
     */
    @ResponseBody
    @RequestMapping(value = "comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List> subComment(@PathVariable(value = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByParentId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
