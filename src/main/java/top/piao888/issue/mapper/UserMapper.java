package top.piao888.issue.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.piao888.issue.domain.Users;
import top.piao888.issue.vo.ReqUser;

import java.util.List;


@Mapper
@Component
public interface UserMapper {
     List<Users> getAll();
     void delete(int id);
     void insert(Users users);
     Users login(ReqUser users);
     Users getUserByUid(Integer uid);
}
