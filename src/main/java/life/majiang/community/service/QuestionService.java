package life.majiang.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.QuestionDto;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
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
}
