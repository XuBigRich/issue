package top.piao888.issue.converter;

import org.springframework.beans.BeanUtils;
import top.piao888.issue.DTO.IssueDTO;
import top.piao888.issue.domain.Issue;
import top.piao888.issue.vo.ReqCreIssue;
import top.piao888.issue.vo.ReqIssue;

import java.util.Date;

public class ReqCreIssue2OIssueDTO {
    public static IssueDTO convert(ReqIssue reqCreIssue) {
        IssueDTO issueDTO=new IssueDTO();
        BeanUtils.copyProperties(reqCreIssue,issueDTO);
        return issueDTO;
    }
}
