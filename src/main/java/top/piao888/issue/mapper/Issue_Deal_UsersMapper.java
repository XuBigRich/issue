package top.piao888.issue.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.piao888.issue.domain.Issue_Deal_Users;
@Mapper
public interface Issue_Deal_UsersMapper {
    void insert(Issue_Deal_Users issue_deal_users);
}
