package life.majiang.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.QuestionDto;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
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
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        QuestionDto questionDto = questionMapper.listById(question.getId());
        if (questionDto != null) {
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        } else {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }
    }
}
