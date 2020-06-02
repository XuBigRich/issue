package top.piao888.issue.tools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
    public static Integer getUid(HttpServletRequest req){
        String uid="";
        Cookie[] cookies=req.getCookies();
        /*遍历cookie*/
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("uid")){
                uid=cookie.getValue();
            }
        }
        return Integer.valueOf(uid);
    }
}
