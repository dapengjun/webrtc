package cn.itear.perm.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itear.common.action.BaseAction;
import cn.itear.common.util.ShowByPage;
import cn.itear.perm.po.UserPo;
import cn.itear.perm.service.IUserService;

@Scope("prototype")
@Controller("userAction")
@Namespace("/perm")
@ParentPackage("struts-shops")
@Results({
	@Result(name = "success", location = "/perm/success.jsp"),
    @Result(name = "failure", location = "/perm/success.jsp"),
    @Result(name = "json", type = "json", params = {"root", "result"})
})
@Action(value = "user")
public class UserAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    @Autowired
    private IUserService userService;
    
    public UserAction() {
        page = new ShowByPage();
    }

    private UserPo userInfo;

    public UserPo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserPo userInfo) {
        this.userInfo = userInfo;
    }

    public String execute() {
//        userService.insertUser(userInfo);
        return "success";
    }

    public String selectUser() {
        try{
            userInfo = userService.selectOne(userInfo.getId());
            result = gson.toJson(userInfo);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return "json";
    }

    public String insert() {
        try{
            userService.insert(userInfo);
            result = "1";
        }catch(Exception e) {
            e.printStackTrace();
            result = "0";
        }
        result = "{\"result\":\""+result+"\"}";
        return "json";
    }

    public String update() {
        try{
            userService.update(userInfo);
            result = "1";
        }catch(Exception e) {
            e.printStackTrace();
            result = "0";
        }
        result = "{\"result\":\""+result+"\"}";
        return "json";
    }

    public String delete() {
        try{
            userService.delete(userInfo.getId());
            result = "1";
        }catch(Exception e) {
            e.printStackTrace();
            result = "0";
        }
        result = "{\"result\":\""+result+"\"}";
        return "json";
    }

    private String result;

    public String getResult() {
        return result;
    }
    
    private ShowByPage page;
    
    public void setRows(int rows) {
        page.setRows(rows);
    }
    
    public void setPage(int page) {
        this.page.setPage(page);
    }
    
    public void setParam(String param) {
        page.setParam(param);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String selectAjaxList() {
        Map map = null;
        List<UserPo> list = null;
        int total;
        int currPage;
        Map param = null;
        String userName = null;
        try {
            param = new HashMap();
            userName = page.getParam();
            if (userName != null && userName.length() > 0) {
                param.put("name", "%"+userName+"%");
            }
            currPage = (page.getPage() - 1) * page.getRows();
            currPage = currPage < 0 ? 0 : currPage;
            param.put("currRow", currPage);
            param.put("rowNum", page.getRows());
            list = userService.selectList(param);
            total = userService.selectCnt(param);
            map = new HashMap();
            map.put("rows", list);
            map.put("total", total);
            result = gson.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "json";
    }
}
