package top.piao888.issue.converter;

import org.springframework.beans.BeanUtils;
import top.piao888.issue.domain.Deal;
import top.piao888.issue.domain.Issue;
import top.piao888.issue.vo.ReqCreDeal;
import top.piao888.issue.vo.ResqIssue;

public class Issue2OResqIssue {
    public static ResqIssue convert(Issue issue,String username,String state) {
        ResqIssue resqIssue=new ResqIssue();
        BeanUtils.copyProperties(issue,resqIssue);
        resqIssue.setState(state);
        resqIssue.setUsername(username);
        return resqIssue;
    }
}
