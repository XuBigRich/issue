package top.piao888.issue.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.piao888.issue.DTO.IssueDTO;
import top.piao888.issue.domain.Issue;
import top.piao888.issue.domain.Users;
import top.piao888.issue.vo.ReqIssue;

import java.util.List;
@Mapper
public interface IssueMapper {
    List<Issue> getAll();
    Issue getIssueById(Integer iid);
    void delete(int iid);
    Integer insert(Issue issue);
    List<Issue> mhcx(IssueDTO issueDTO);
    void update(Issue issue);
    /*处理人查询*/
    List<Issue>  clrcx(Issue issue);
    /*更改问题状态*/
    void state(Issue issue);
}
