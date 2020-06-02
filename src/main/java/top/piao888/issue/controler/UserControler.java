package top.piao888.issue.controler;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.piao888.issue.DTO.IssueDTO;
import top.piao888.issue.constant.StatusConstant;
import top.piao888.issue.converter.Issue2OResqIssue;
import top.piao888.issue.converter.ReqCreDeal2ODeal;
import top.piao888.issue.converter.ReqCreIssue2OIssue;
import top.piao888.issue.converter.ReqCreIssue2OIssueDTO;
import top.piao888.issue.domain.*;
import top.piao888.issue.mapper.*;
import top.piao888.issue.server.UserServer;
import top.piao888.issue.tools.CookieUtil;
import top.piao888.issue.tools.PBUtil;
import top.piao888.issue.tools.PageBean;
import top.piao888.issue.vo.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.valueOf;

@Controller
public class UserControler {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IssueMapper issueMapper;
    @Autowired
    private DealMapper dealMapper;
    @Autowired
    private Issue_Deal_UsersMapper issue_deal_usersMapper;
    @Autowired
    private BaseMapper baseMapper;
    @Autowired
    private UserServer userServer;
    @PostMapping("/login")
    public String login(ReqUser reqUser, Model model, HttpServletResponse httpServletResponse) throws Exception {
        Users users = userMapper.login(reqUser);
        if(users ==null) {
           throw new Exception("账号或密码错误");
        }
        Cookie cookie = new Cookie("uid", String.valueOf(users.getUid()));
        httpServletResponse.addCookie(cookie);
        List<Issue> issueList =issueMapper.getAll();
        model.addAttribute("issues",issueList);
        return "redirect:cxall";
    }
    /*模糊查询提交的问题*/
    @PostMapping("/mhcx")
    public String mhcx(HttpServletRequest req,ReqIssue reqIssue, Model model) throws Exception {
        IssueDTO issueDTO=ReqCreIssue2OIssueDTO.convert(reqIssue);
        List<Issue> issues=issueMapper.mhcx(issueDTO);
        List<ResqIssue> resqIssues=userServer.conver(issues);
        PBUtil<ResqIssue> issuePBUtil=new PBUtil<>();
        PageBean issuespb=issuePBUtil.fy(req,resqIssues);
        model.addAttribute("pb",issuespb);
        return "allv";
    }
    /*查询所有问题*/
    @GetMapping("/cxall")
    public String mhcx(HttpServletRequest req, Model model) throws Exception {
        List<Issue> issues=issueMapper.getAll();
        List<ResqIssue> resqIssues=userServer.conver(issues);
        PBUtil<ResqIssue> issuePBUtil=new PBUtil<>();
        PageBean issuespb=issuePBUtil.fy(req,resqIssues);
        model.addAttribute("pb",issuespb);
        return "allv";
    }
    /*提交问题*/
    @GetMapping("/tjwt")
    public String tjwt(Model model){
        /*查出所有用户*/
        List<Users> users=userMapper.getAll();
        model.addAttribute("user",users);
        return "createissue";
    }
    /*提交一个问题*/
    @PostMapping("/createissue")
    public String createissue(ReqCreIssue reqCreIssue, HttpServletRequest req,Model model) throws Exception {
        /*获取当前登陆用户信息*/
        Integer uid=CookieUtil.getUid(req);
            if(uid.equals("")){
                throw new Exception("用户未登录");
            }
        /*将问题存入相应表中*/
        Issue issue= ReqCreIssue2OIssue.convert(reqCreIssue);
        issue.setUid(valueOf(uid));//设置问题所属人
        issue.setState(StatusConstant.DDCL);
        userServer.tjwt(reqCreIssue,issue);
        return "redirect:cxall";
    }
    /*查看自己的问题*/
    @GetMapping("wdwt")
    public String wdwt(HttpServletRequest req,Model model) throws Exception {
        Integer uid=CookieUtil.getUid(req);
        if(uid.equals("")){
            throw new Exception("用户未登录");
        }
        IssueDTO issueDTO=new IssueDTO();
        issueDTO.setUid(uid);
        List issues=issueMapper.mhcx(issueDTO);//这个地方需要一个DTO,明天写
        List<ResqIssue> resqIssues=userServer.conver(issues);
        PBUtil<ResqIssue> issuePBUtil=new PBUtil<>();
        PageBean issuespb=issuePBUtil.fy(req,resqIssues);
        model.addAttribute("pb",issuespb);
        model.addAttribute("pb",issuespb);
        return "allvs";
    }
    /*删除自己的问题*/
    @PostMapping("delse")
    public String delse(String[] message){
           for(int i=0;i<message.length;i++){
               Issue issue=issueMapper.getIssueById(valueOf(message[i]));
               if(issue.getState()==1) {
                   issueMapper.delete(valueOf(message[i]));
               }
           }
        return "redirect:wdwt";
    }
    /*更改自己的问题*/
    /*
    * 更改自己的问题 先 通过 查询把这些问题查询出来放入编辑框
    * */
    @GetMapping("beforupdateissue")
    public String beforupdateissue(String iid,Model model){

        IssueDTO issueDTO=new IssueDTO();
        issueDTO.setIid(valueOf(iid));
        Issue issue=issueMapper.mhcx(issueDTO).get(0);
        if(issue.getState()==1) {
            List<Users> users=userMapper.getAll();
            model.addAttribute("user",users);
            model.addAttribute("issue",issue);
            return "updateissue";
        }
        return "redirect:cxall";
    }

    /*
     * 更改自己的问题 要通过 问题的iid  与当前用户的 uid 匹配后 才可以编辑
     * */
    @PostMapping("updateissue")
    public String updateis(ReqCreIssue reqCreIssue,String iid,Model model){
        Issue issue=ReqCreIssue2OIssue.convert(reqCreIssue);
        issue.setIid(valueOf(iid));
        issueMapper.update(issue);
        return "redirect:wdwt";
    }
    /*
    * 处理问题前查询出所有自己能处理的问题
    * */
    @GetMapping("beforcl")
    public String beforcl(HttpServletRequest req,Model model) throws Exception {
        Integer uid=CookieUtil.getUid(req);
        Issue issue=new Issue();
        issue.setUid(uid);
        /*可以根据uid 查找出 所有属于自己处理的问题   也可以 根据iid查找出单个问题 */
        List<Issue> issues= issueMapper.clrcx(issue);
        List<Issue> result =issues.stream().peek(e->e.setState(1)).collect(Collectors.toList());
        List<ResqIssue> resqIssues=userServer.conver(result);
        PBUtil<ResqIssue> issuePBUtil=new PBUtil<>();
        PageBean issuespb=issuePBUtil.fy(req,resqIssues);
        model.addAttribute("pb",issuespb);
        return "allvc";
    }
    /*
     * 处理单个问题前
     * */
    @GetMapping("beforclone")
    public String beforclone(Issue issue,Model model) throws Exception {
        List<Issue> result= issueMapper.clrcx(issue);
        Issue issue1= result.get(0);
        model.addAttribute("pb",issue1);
        List<Base> base= baseMapper.getAll();
        model.addAttribute("status",base);
        return "wtcl";
    }
    /*
     * 处理问题
     * */
    @PostMapping("cl")
    public String cl(HttpServletRequest req,ReqCreDeal reqCreDeal, Model model) throws Exception {
        Integer uid=CookieUtil.getUid(req);
        Deal deal= ReqCreDeal2ODeal.convert(reqCreDeal);
        deal.setUid(uid);
        /*添加问题处理办法*/
        dealMapper.insert(deal);

        /*更改问问题状态*/
        Issue issue=new Issue();
        issue.setState(reqCreDeal.state);
        issue.setIid(reqCreDeal.getIid());
        issueMapper.state(issue);

        return "redirect:cxall";
    }

    /*
    * 查看处理的问题
    * */
    @GetMapping("cdbf")
    public String cdbf(Integer iid,Model model){
        Deal deal=dealMapper.getDealByiid(iid);
        String content=deal.getContent();
        model.addAttribute("content",content);
        return "dealcontent";
    }

    /*
    * 测试jquery
    * */
    /*
     * 查看处理的问题
     * */
    @GetMapping("clbf")
    @ResponseBody
    public Deal cdbftest(Integer iid,Model model){
        Deal deal=dealMapper.getDealByiid(iid);
        return deal;
    }
    /*
    * 查询所有问题
    * */
    @GetMapping("all")
    @ResponseBody
    public  List<Issue> all(){
        List<Issue> issueList =issueMapper.getAll();
        return  issueList;
    }

}
