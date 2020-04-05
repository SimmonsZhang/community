package life.majiang.community.mapper;

import life.majiang.community.dto.QuestionDto;
import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionMapper {
//    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) " +
//            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

//    @Select({"select u.avatar_url as avatar_url,q.* from question q,user u where q.creator=u.id"})
    List<QuestionDto> listWithAvatar();

//    @Select("select u.avatar_url as avatar_url,q.* from question q,user u where q.creator=#{creator} and u.id=q.creator")
    List<QuestionDto> listByCreator(Integer creator);

    QuestionDto listById(Integer id);

    void update(Question question);
}
