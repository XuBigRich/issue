package top.piao888.issue.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResqIssue {
    private int iid;
    private String titl;
    private String content;
    private String state;
    private Date createtime;
    private Date opentime;
    private String username;
}
