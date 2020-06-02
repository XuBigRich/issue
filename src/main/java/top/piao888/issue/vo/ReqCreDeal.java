package top.piao888.issue.vo;

import lombok.Data;

@Data
public class ReqCreDeal {
    /*问题id*/
    private int iid ;
    /*问题处理方法*/
    private  String content;
    /*是否能处理标示 3.能处理  4.不能处理*/
    public int state;

}
