package top.piao888.issue.converter;

import org.springframework.beans.BeanUtils;
import top.piao888.issue.domain.Deal;
import top.piao888.issue.domain.Issue;
import top.piao888.issue.vo.ReqCreDeal;
import top.piao888.issue.vo.ReqCreIssue;

import java.util.Date;

public class ReqCreDeal2ODeal {
    public static Deal convert(ReqCreDeal reqCreDeal) {
        Deal deal=new Deal();
        BeanUtils.copyProperties(reqCreDeal,deal);
        return deal;
    }
}
