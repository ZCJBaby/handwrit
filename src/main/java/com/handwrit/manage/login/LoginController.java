package com.handwrit.manage.login;

import com.alibaba.fastjson.JSONObject;
import com.handwrit.manage.entity.sys.User;
import com.handwrit.manage.service.sys.IUserService;
import com.handwrit.manage.service.sys.impl.UserService;
import com.handwrit.manage.utils.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class LoginController extends BaseController {

    @Autowired
    private IUserService userService;
    /**
     * 微信浏览器获取用户信息
     * @param code
     * @param state
     * @return
     */
    @PostMapping("/userInfo")
    public Map getUserInformation(String code, String state, HttpServletRequest request) {
        User User = new User();
        Map map1 = new HashMap();
        if (StringUtils.isEmpty(code)) {
            map1.put("result","code为空");
            return map1;
        }
        JSONObject jsonData = WeixinSign.getAccessToken(code);
        String openid = jsonData.getString("openid");
        String access_token = jsonData.getString("access_token");
        String refresh_token = jsonData.getString("refresh_token");
        HttpSession session = request.getSession();

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
//                    String userId = UUID.randomUUID().toString().replace("-", "");
//                    User.setId(userId);
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
//            logger.error("获取用户信息异常：" + e.getMessage());
            map1.put("result","获取用户信息异常");
            return map1;
        }
        Map map = new HashMap();
        map.put("id", User.getId());
        map.put("unionid", User.getUnionid());
        map.put("headimgUrl", User.getHeadimgurl());
        map.put("nickname", User.getNickname());
        map.put("sex", User.getSex());
        map.put("province", userData.get("province").toString());
        map.put("city", userData.get("city").toString());
        map.put("openid", userData.get("openid").toString());
        map.put("sessionid", request.getSession().getId());
        return map;
    }
}
