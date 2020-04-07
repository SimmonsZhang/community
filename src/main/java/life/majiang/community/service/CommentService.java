package life.majiang.community.service;

import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizedErrorCode;
import life.majiang.community.exception.CustomizedException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public void insertSelective(Comment comment) {
        //parentId为null 或 0 (不合法)
        //parentId指的是被评论的question/comment的id
        if (comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizedException(CustomizedErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        /*评论类型(commentType)错误*/
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizedException(CustomizedErrorCode.TYPE_PARAM_WRONG);
        }
        /* 假设是对comment的评论 */
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())){
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null){     //parentId合法但不存在
                throw new CustomizedException(CustomizedErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }else {     /* 对question的评论 */
            Question dbQuestion = questionMapper.selectByPrimaryKey(Math.toIntExact(comment.getParentId()));
            if (dbQuestion == null){
                throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            Question question = new Question();
            question.setCommentCount(1);
            question.setId(dbQuestion.getId());
            questionMapper.incCommentCount(question);
        }
    }
}
