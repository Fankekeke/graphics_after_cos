package cc.mrbird.febs.cos.controller;

import cc.mrbird.febs.common.utils.HttpContextUtil;
import cc.mrbird.febs.common.utils.MD5Util;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.BulletinInfo;
import cc.mrbird.febs.cos.entity.ProductInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web")
@AllArgsConstructor
public class WebController {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    private final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");

    private final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final IOrderInfoService orderInfoService;

    private final TemplateEngine templateEngine;

    private final IMailService iMailService;

    private final IUserInfoService userInfoService;

    private final IBulletinInfoService bulletinInfoService;

    private final IProductInfoService productInfoService;

    /**
     * 页面跳转
     *
     * @param
     * @return
     */
    @RequestMapping("{page}")
    public String showPage(@PathVariable("page") String page) {
        return page;
    }

    /**
     * 项目主页面
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "index.html")
    public String init(Model model, HttpSession session) {
        // 添加访问记录
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        model.addAttribute("proInfo", null);
        return "index";
    }

    /**
     * 公告页面
     *
     * @param pageNo
     * @param type
     * @param model
     * @return
     */
    @RequestMapping(value = "news.html/{pageNo}", method = RequestMethod.GET)
    public String goMessageInfo(@PathVariable(value = "pageNo") Integer pageNo, @RequestParam(required = false) Integer type, Model model) {
        Page pagehelper = new Page();
        pagehelper.setCurrent(pageNo == null ? 1 : pageNo);
        pagehelper.setSize(6);
        model.addAttribute("messList", bulletinInfoService.page(pagehelper, Wrappers.<BulletinInfo>lambdaQuery()
                .eq(type != null, BulletinInfo::getType, type)));
        model.addAttribute("messType", type);
        return "news";
    }

    /**
     * 公告页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "notice.html/{id}", method = RequestMethod.GET)
    public String goMessageShow(@PathVariable(value = "id") Integer id, Model model) {
        BulletinInfo messageInfo = bulletinInfoService.getById(id);
        model.addAttribute("message", messageInfo);
        return "notice";
    }

    /**
     * 项目列表
     *
     * @param pageNo
     * @param key
     * @param type
     * @param model
     * @return
     */
    @RequestMapping(value = "product.html", method = RequestMethod.GET)
    public String goProductInfo(@RequestParam(value = "pageNo") Integer pageNo, @RequestParam(required = false) String key, @RequestParam(required = false) Integer type, Model model) {
        Page pagehelper = new Page();
        pagehelper.setCurrent(pageNo == null ? 1 : pageNo);
        pagehelper.setSize(8);
//        model.addAttribute("proList", productInfoService.getProjectPage(pagehelper, key, type));
        model.addAttribute("key", key);
        model.addAttribute("type", type);
        return "product";
    }

    /**
     * 历史订单页面
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "order/{pageNo}", method = RequestMethod.GET)
    public String getOrderList(@PathVariable(value = "pageNo") Integer pageNo, Model model, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        Page pageHelper = new Page();
        pageHelper.setCurrent(pageNo == null ? 1 : pageNo);
        pageHelper.setSize(10);
//        model.addAttribute("orderList", orderInfoService.orderListByUser(pageHelper, user.getCode()));
        return "order";
    }

    /**
     * 项目详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "project.html/{id}", method = RequestMethod.GET)
    public String goProjectInfo(@PathVariable(value = "id") Integer id, Model model) {
        // 增加项目访问记录
        ProductInfo projectInfo = productInfoService.getById(id);
        if (projectInfo != null) {
            model.addAttribute("proInfo", productInfoService.getById(id));
        } else {
            model.addAttribute("proInfo", null);
        }
        return "project";
    }

    /**
     * 注册发送验证码
     *
     * @param email
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "verificationCode", method = RequestMethod.POST)
    public R verificationCode(@RequestParam("email") String email, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 判断邮箱是否已被注册
        Integer emailVerify = userInfoService.count(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getEmail, email));
        if (emailVerify == 0) {
            // 绘制随机字符
            String randomString = "";
            for (int i = 1; i <= 50; i++) {
                randomString = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            }
            //1：将随机生成的验证码放入Cookie中
            Cookie cookie = new Cookie("code", randomString);
            response.addCookie(cookie);
            //2：将随机生成的验证码放入session中
//            String sessionid = request.getSession().getId();
//            request.getSession().setAttribute(sessionid + "code", randomString);
            Context context = new Context();
            context.setVariable("today", sdf1.format(new Date()));
            context.setVariable("custom", randomString);
            String emailContent = templateEngine.process("registerEmail", context);
            iMailService.sendHtmlMail(email, "EVA-登入验证", emailContent);
            // 添加消息通知
            return R.ok(true);
        } else {
            return R.ok(false);
        }
    }

    /**
     * 验证码校验
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "checkTcode", method = RequestMethod.POST)
    @ResponseBody
    public R checkTcode(HttpServletRequest request, HttpServletResponse response) {
        String validateCode = request.getParameter("code");
        String code = null;
        //1:获取cookie里面的验证码信息
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("code".equals(cookie.getName())) {
                code = cookie.getValue();
                break;
            }
        }
        //2:判断验证码是否正确
        if (!StringUtils.isEmpty(validateCode)) {
            validateCode = validateCode.toLowerCase();
            code = code.toLowerCase();
            if (validateCode.equals(code)) {
                return R.ok(true);
            }
        }
        return R.ok(false);
    }

    /**
     * 用户注册
     *
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "registered", method = RequestMethod.POST)
    @ResponseBody
    public R registered(@RequestBody UserInfo userInfo) {
        // 判断邮箱是否被注册
        Integer emailVerify = userInfoService.count(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getEmail, userInfo.getEmail()));
        if (emailVerify == 0 && !StrUtil.hasEmpty(userInfo.getAccount())
                && !StrUtil.hasEmpty(userInfo.getEmail())
                && !StrUtil.hasEmpty(userInfo.getPassword())) {
            // 密码加密
            userInfo.setPassword(MD5Util.encrypt(userInfo.getEmail(), userInfo.getPassword()));
            // 用户邀请码
            userInfo.setCode("U-" + System.currentTimeMillis());
            userInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            userInfo.setStatus(1);
            return R.ok(userInfoService.save(userInfo));
        } else {
            return R.ok(false);
        }
    }

    @RequestMapping(value = "login.html")
    public String beforeLogin(HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user != null) {
            return "index";
        } else {
            return "login";
        }
    }

    /**
     * 用户登录
     *
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "dologin", method = RequestMethod.POST)
    public String login(HttpSession session, HttpServletRequest request) {
        if (!StrUtil.hasEmpty(request.getParameter("email")) && !StrUtil.hasEmpty(request.getParameter("password"))) {
            UserInfo user = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery()
                    .eq(UserInfo::getEmail, request.getParameter("email"))
                    .eq(UserInfo::getPassword, MD5Util.encrypt(request.getParameter("email"), request.getParameter("password"))));
            if (user != null) {
                // 保存用户信息到SESSION
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(10800);
                return "redirect:/web/index.html";
            } else {
                request.setAttribute("error", "账户或密码填写错误！");
                return "login";
            }
        } else {
            request.setAttribute("error", "出现错误！");
            return "login";
        }
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "login";
    }
}
