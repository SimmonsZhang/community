package life.majiang.community.service;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() != 0) {
            User updateUser = new User();
            updateUser.setToken(user.getToken());
            updateUser.setGmtModified(user.getGmtModified());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());

            userMapper.updateByExampleSelective(updateUser, userExample);
        }
        else
            userMapper.insert(user);
    }
}
