package cc.mrbird.febs.cos.controller.information;

import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.service.UserService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/cos/information")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InformationController {


    private final UserService userService;

    private final RedisService redisService;
    private final IUserInfoService userInfoService;

    private final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");

    /**
     * 更新用户基本信息
     * @param session
     * @param request
     * @return
     */
    @ResponseBody
    @PutMapping("/user/data")
    public R userInfoEdit(HttpSession session, HttpServletRequest request) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user != null) {
            if (!StrUtil.hasEmpty(request.getParameter("name"))) {
                user.setName(request.getParameter("name"));
            }
            if (!StrUtil.hasEmpty(request.getParameter("phone"))) {
                user.setPhone(request.getParameter("phone"));
            }
            if (!StrUtil.hasEmpty(request.getParameter("address"))) {
                user.setAddress(request.getParameter("address"));
            }
            userInfoService.updateById(user);
            // 更新session
            session.setAttribute("user", userInfoService.getById(user.getId()));
            return R.ok(true);
        } else {
            return R.ok(false);
        }
    }

    /**
     * 进入个人信息页面
     * @param userId
     * @return
     */
    @GetMapping("/home")
    public R home(@RequestParam("userId") Integer userId) {
        // 是否绑定手机号码
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserId, userId));
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("user", null);
            }
        };
        return R.ok(result);
    }

    /**
     * 系统用户验证邮箱地址
     * @param mail
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("/verify/mail")
    public R mailVerify(@RequestParam("mail") String mail, @RequestParam("userId") Integer userId) throws Exception {
        // 判断邮箱是否已经注册
        Integer count = userService.count(Wrappers.<User>lambdaQuery().eq(User::getEmail, mail));
        if (count > 0) {
            return R.error("该邮箱地址已被注册");
        } else {
            // 绘制随机字符
            String random = "";
            for (int i = 1; i <= 50; i++) {
                random = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            }
            //1：将随机生成的验证码放入redis中
            redisService.set(mail, random);
            User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserId, userId));
            return R.ok(true);
        }
    }

    /**
     * 验证识别码更换邮箱
     * @param mail
     * @param verifyCode
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("/check/mail")
    public R checkMailVerify(@RequestParam("mail") String mail, @RequestParam("verifyCode") String verifyCode, @RequestParam("userId") Integer userId) throws Exception {
        //1:获取redis里面的验证码信息
        String code = redisService.get(mail);
        if (code != null) {
            //2:判断验证码是否正确
            if (!StringUtils.isEmpty(verifyCode)) {
                verifyCode = verifyCode.toLowerCase();
                code = code.toLowerCase();
                if (verifyCode.equals(code)) {
                    redisService.del(mail);
                    // 修改用户绑定邮箱
                    return R.ok(userService.update(Wrappers.<User>lambdaUpdate().set(User::getEmail, mail).eq(User::getUserId, userId)));
                } else {
                    return R.error("验证码错误！");
                }
            } else {
                return R.error("验证码为空！");
            }
        } else {
            return R.error("出现异常，请稍后在试！");
        }
    }


}
