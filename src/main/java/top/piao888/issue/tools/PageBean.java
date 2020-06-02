package top.piao888.issue.tools;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageBean {
    private int pagesize;//每页显示的记录条数
    private int count;//记录总条数
    private int total;//总页数
    private int p;//当前页码
    private List data;//数据集合
    private int up;//上一页
    private int down;//下一页
    private List show; //要显示的 列表

    public PageBean(){
        pagesize=5;//默认每页显示5条记录
        data=new ArrayList();
        show=new ArrayList();
    }

    //设置记录总条数（其次调用）
    public void setCount(int count) {
        this.count = count;  //一共有多少条记录
        total=(int)(Math.ceil(count*1.0/pagesize));   //一共多少页
    }
    //设置待显示的页码，参数：从页面提交过来的页码（最后调用）
    public void setP(int p){
        if(p<1)this.p=1;
        else if(p>total)this.p=total;
        else this.p = p;
        if(this.total==0)return;
        setShow(this.p);
        setUp(p-1);
        setDown(p+1);
    }

    public void setShow(int p) {
        int c=p*pagesize-pagesize;
        for(int i=0;i<pagesize;i++){
            if(c>data.size()-1){
                break;
            }
            Object user=data.get(c);
            c++;
            show.add(user);
        }
    }
}
