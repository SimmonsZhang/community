package life.majiang.community.mapper;

import java.util.List;

import life.majiang.community.dto.QuestionDto;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface QuestionMapper {
    long countByExample(QuestionExample example);

    int deleteByExample(QuestionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Question record);

    int insertSelective(Question record);

    List<Question> selectByExampleWithBLOBs(QuestionExample example);

    List<Question> selectByExample(QuestionExample example);

    Question selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByExampleWithBLOBs(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByExample(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);


    List<QuestionDto> listWithAvatar();

    List<QuestionDto> listByCreator(Integer creator);

    QuestionDto listById(Long id);

    int incViewCount(Question question);

    List<QuestionDto> selectByTag(Long id, String tag);

    void incCommentCount(Question question);
}