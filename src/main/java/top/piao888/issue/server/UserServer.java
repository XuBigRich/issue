package top.piao888.issue.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.piao888.issue.converter.Issue2OResqIssue;
import top.piao888.issue.domain.Base;
import top.piao888.issue.domain.Issue;
import top.piao888.issue.domain.Issue_Deal_Users;
import top.piao888.issue.domain.Users;
import top.piao888.issue.mapper.BaseMapper;
import top.piao888.issue.mapper.IssueMapper;
import top.piao888.issue.mapper.Issue_Deal_UsersMapper;
import top.piao888.issue.mapper.UserMapper;
import top.piao888.issue.vo.ReqCreIssue;
import top.piao888.issue.vo.ResqIssue;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServer {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BaseMapper baseMapper;
    @Autowired
    private IssueMapper issueMapper;
    @Autowired
    private Issue_Deal_UsersMapper issue_deal_usersMapper;
    /*Issue变resq*/
    public List<ResqIssue> conver(List<Issue> issues){
        List<ResqIssue> resqIssues=new ArrayList<>();
        for(int i=0;i<issues.size();i++){
            Issue issue=issues.get(i);
            Base base=baseMapper.selectByid(issue.getState());
            Users users=userMapper.getUserByUid(issue.getUid());
            String state= base.getMessage();
            String username=users.getUsername();
            ResqIssue resqIssue= Issue2OResqIssue.convert(issue,username,state);
            /*对展示数据做处理*/
            String content=issue.getContent();
            /*取出前20个汉字*/
            if(content.length()>20){
                content=content.substring(0, 20);
                content+="...";
            }

            resqIssue.setContent(content);
            resqIssues.add(resqIssue);
        }
        return resqIssues;
    }
    @Transactional
    public void tjwt(ReqCreIssue reqCreIssue,Issue issue) {
        /*把问题添加上 并返回iid*/
        issueMapper.insert(issue);
        /*添加处理人*/
        List<Integer> uids =reqCreIssue.getIntegerList();
        if(uids==null){
            try {
                throw new Exception("没有选择处理人");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Issue_Deal_Users issue_deal_users=new Issue_Deal_Users();
        issue_deal_users.setIid(issue.getIid());
        for(int i=0;i<uids.size();i++){
            issue_deal_users.setUid(uids.get(i));
            issue_deal_usersMapper.insert(issue_deal_users);
        }
    }
}
