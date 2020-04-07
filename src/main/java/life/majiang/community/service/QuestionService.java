package life.majiang.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.QuestionDto;
import life.majiang.community.exception.CustomizedErrorCode;
import life.majiang.community.exception.CustomizedException;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    public PageInfo<QuestionDto> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<QuestionDto> questionDtoList = questionMapper.listWithAvatar();

        return new PageInfo<>(questionDtoList, 5);
    }

    public PageInfo<QuestionDto> listByCreator(Integer userId, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<QuestionDto> questionDtoList = questionMapper.listByCreator(userId);

        return new PageInfo<>(questionDtoList, 5);
    }

    public QuestionDto listById(Integer id) {
        QuestionDto questionDto = questionMapper.listById(id);
        if (questionDto == null){
            throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
        }

        return questionDto;
    }

    public void createOrUpdate(Question question) {
//        QuestionDto questionDto = questionMapper.listById(question.getId());
        Question selectedQuestion = questionMapper.selectByPrimaryKey(question.getId());
        if (selectedQuestion != null) {
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            if (questionMapper.updateByExampleSelective(question, questionExample) != 1)
                throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
        } else {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        }
    }

    public void incViewCount(Integer id) {
        //增加浏览次数comment_count
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        if (questionMapper.incViewCount(question) != 1)
            throw new CustomizedException(CustomizedErrorCode.QUESTION_NOT_FOUND);
    }
}
