package top.piao888.issue.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Issue {
    private int iid;
    /*所属用户id*/
    private int uid ;
    /*问题标题*/
    private String titl ;
    /*问题内容*/
    private String content;
    /*1.等待处理、2.正在处理、3.已处理、4.无法处理*/
    private Integer state;
    /*出事时间*/
    private Date opentime;
    /*问题提交的时间*/
    private Date createtime;

    /*问题处理人 联查时使用*/
    private List<Users> clusers;
}
