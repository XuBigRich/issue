package top.piao888.issue.converter;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import top.piao888.issue.domain.Issue;
import top.piao888.issue.vo.ReqCreIssue;

import java.util.Date;

public class ReqCreIssue2OIssue {
    public static Issue convert(ReqCreIssue reqCreIssue) {
        Issue issue=new Issue();
        BeanUtils.copyProperties(reqCreIssue,issue);
        issue.setCreatetime(new Date());
        return issue;
    }
}
