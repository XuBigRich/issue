package top.piao888.issue.DTO;

import lombok.Data;

import java.util.Date;
@Data
public class IssueDTO {
    private String titl;
    private String state;
    private String endDate;
    private String startDate;
    private int iid;
    /*所属用户id*/
    private int uid ;
    /*问题内容*/
    private String content;
    /*出事时间*/
    private Date openTime;
    /*问题提交的时间*/
    private Date createtime;
}
