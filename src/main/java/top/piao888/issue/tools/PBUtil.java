package top.piao888.issue.tools;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/*分页万金油*/
public class PBUtil<T> {
    public  PageBean fy(HttpServletRequest req,List<T> t) throws Exception {
        int p=1;
        if(req.getParameter("p")!=null){
            p=Integer.parseInt(req.getParameter("p"));
        }
        PageBean pageBean=new PageBean();//pagesize默认=5
        pageBean.setData(t);//设置数据集合
        pageBean.setCount(t.size());//设置记录总条数
        pageBean.setP(p);//设置当前待显示的页码
        return pageBean;
    }
}
