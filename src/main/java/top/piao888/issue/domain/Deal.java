package top.piao888.issue.domain;

import lombok.Data;

@Data
public class Deal {
    private int did ;
    /*问题解决人id*/
    private int uid ;
    /*问题id*/
    private int iid ;
    /*问题处理方法*/
    private  String content;
}
