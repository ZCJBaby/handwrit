package com.handwrit.manage.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.handwrit.manage.entity.sys.User;
import com.handwrit.manage.login.WeixinSign;
import com.handwrit.manage.service.sys.IUserService;
import com.handwrit.manage.utils.base.BaseController;
import com.handwrit.manage.utils.base.Message;
import com.handwrit.manage.utils.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 甘小涛哇 on 2019/10/14
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 获取用户信息(分页)
     * @return
     */
    @PostMapping("/selectListPage")
    public Message selectListPage( @RequestParam Map<String, Object> param){
        try {
            int pageIndex = RequestUtil.getIntParameter(param, "pageIndex", 1);
            int pageSize = RequestUtil.getIntParameter(param, "pageSize", 1);
            PageHelper.startPage(pageIndex, pageSize);
            List<User> users = userService.selectList(param);
            PageInfo page = new PageInfo(users);
            return renderSuccess(page.getList());
        } catch (Exception e) {
            e.printStackTrace();
            return renderException(e);
        }
    }


    @PostMapping("/userInfo")
    public String getUserInformation(String code, HttpServletRequest request) {
        User User = new User();
        if (StringUtils.isEmpty(code)) {
            return "code为空";
        }
        JSONObject jsonData = WeixinSign.getAccessToken(code);
        String openid = jsonData.getString("openid");
        String access_token = jsonData.getString("access_token");
        String refresh_token = jsonData.getString("refresh_token");

        //验证access_token是否失效
        JSONObject validateData = WeixinSign.getValidateData(access_token, openid);
        if (!"0".equals(validateData.getString("errcode"))) {
            //刷新access_token
            JSONObject refreshData = WeixinSign.getRefreshToken(refresh_token);
            access_token = refreshData.getString("access_token");
        }
        JSONObject userData = null;
        try {
            //拉取用户信息
            userData = WeixinSign.getUserInfo(access_token, openid);
            Object unionid = userData.get("unionid");
            Object nickName = userData.get("nickname");
            Object headimgurl = userData.get("headimgurl");
            Object sex = userData.get("sex");
            if (!StringUtils.isEmpty(unionid)) {
                //用户是否注册过
                User = userService.findUserByName(User.getUsername());
                if (StringUtils.isEmpty(User)) {
                    User = new User();
                    if (!StringUtils.isEmpty(unionid)) {
                        User.setUnionid(unionid.toString());
                    }
                    if (!StringUtils.isEmpty(nickName)) {
                        User.setNickname(nickName.toString());
                    }
                    if (!StringUtils.isEmpty(headimgurl)) {
                        User.setHeadimgurl(headimgurl.toString());
                    }
                    if (!StringUtils.isEmpty(sex)) {
                        User.setSex(new Byte(sex.toString()));
                    }
                    User.setCreateTime(new Date());
                    User.setUnionid(unionid.toString());
                    userService.insertUser(User);
                }

            }
        } catch (Exception e) {
            return "获取用户信息异常";
        }
//        Map map = new HashMap();
//        map.put("id", User.getId());
//        map.put("unionid", User.getUnionid());
//        map.put("headimgUrl", User.getHeadimgurl());
//        map.put("nickname", User.getNickname());
//        map.put("sex", User.getSex());
//        map.put("province", userData.get("province").toString());
//        map.put("city", userData.get("city").toString());
//        map.put("openid", userData.get("openid").toString());
//        map.put("sessionid", request.getSession().getId());
        return "";
    }

}
