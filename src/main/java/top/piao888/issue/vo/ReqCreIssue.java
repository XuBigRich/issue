package top.piao888.issue.vo;


import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ReqCreIssue {
    private String titl;
    private Date opentime=new Date();
    private String content;
    private List<Integer> integerList;
}
