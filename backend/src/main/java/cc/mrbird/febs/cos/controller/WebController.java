package cc.mrbird.febs.cos.controller;

import cc.mrbird.febs.common.utils.HttpContextUtil;
import cc.mrbird.febs.common.utils.MD5Util;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private final IPaymentRecordService paymentRecordService;
    private final IRepairInfoService repairInfoService;
    private final IStaffEvaluationService staffEvaluationService;
    private final ISerialInfoService serialInfoService;

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
        model.addAttribute("proInfo", productInfoService.list(Wrappers.<ProductInfo>lambdaQuery().last("limit 3")));
        return "index";
    }

    /**
     * 新增工单信息
     *
     * @param orderInfo 工单信息
     * @return 结果
     */
    @ResponseBody
    @PostMapping("/order")
    public R save(OrderInfo orderInfo, HttpSession session) {
        // 查询用户信息
        UserInfo user = (UserInfo) session.getAttribute("user");
        orderInfo.setCustomerId(user.getId());

        // 根据序列号获取产品信息
        SerialInfo serialInfo = serialInfoService.getOne(Wrappers.<SerialInfo>lambdaQuery().eq(SerialInfo::getSerialNumber, orderInfo.getSerialNumber()));
        if (serialInfo == null) {
            return R.error("未找到此序列号");
        }
        orderInfo.setProductId(serialInfo.getProductId());
        orderInfo.setOrderCode("ORD-" + System.currentTimeMillis());
        orderInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        orderInfo.setStatus(0);
        return R.ok(orderInfoService.save(orderInfo));
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
        model.addAttribute("messList", bulletinInfoService.page(pagehelper, Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1)));
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
        pagehelper.setSize(9);

        ProductInfo productInfo = new ProductInfo();
        model.addAttribute("proList", productInfoService.selectProductPage(pagehelper, productInfo));
        model.addAttribute("key", key);
        model.addAttribute("type", type);
        return "product";
    }

    /**
     * 新增员工评价信息
     *
     * @param staffEvaluation 员工评价信息
     * @return 结果
     */
    @ResponseBody
    @PostMapping("/evaluate")
    public R saveEvaluate(StaffEvaluation staffEvaluation, HttpSession session) {
        OrderInfo orderInfo = orderInfoService.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getOrderCode, staffEvaluation.getOrderCode()));
        UserInfo user = (UserInfo) session.getAttribute("user");
        staffEvaluation.setStaffId(orderInfo.getStaffId());
        staffEvaluation.setUserId(user.getId());
        BigDecimal allScore = staffEvaluation.getRepairScore().add(staffEvaluation.getScheduleScore()).add(staffEvaluation.getServiceScore());
        staffEvaluation.setScore(allScore.divide(BigDecimal.valueOf(3), 0, RoundingMode.HALF_UP));
        staffEvaluation.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(staffEvaluationService.save(staffEvaluation));
    }

    /**
     * 我的工单页面
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
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setSysUserId(user.getId());
        model.addAttribute("orderList", orderInfoService.selectOrderPage(pageHelper, orderInfo));
        return "order";
    }

    /**
     * 缴费记录页面
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "system/{pageNo}", method = RequestMethod.GET)
    public String getRecordList(@PathVariable(value = "pageNo") Integer pageNo, Model model, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        Page pageHelper = new Page();
        pageHelper.setCurrent(pageNo == null ? 1 : pageNo);
        pageHelper.setSize(10);

        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setSysUserId(user.getId());
        model.addAttribute("orderList", paymentRecordService.selectPaymentRecordPage(pageHelper, paymentRecord));
        return "system";
    }

    /**
     * 维修记录页面
     * @param pageNo
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "message/{pageNo}", method = RequestMethod.GET)
    public String getRepairRecordList(@PathVariable(value = "pageNo") Integer pageNo, Model model, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        Page pageHelper = new Page();
        pageHelper.setCurrent(pageNo == null ? 1 : pageNo);
        pageHelper.setSize(10);

        RepairInfo repairInfo = new RepairInfo();
//        repairInfo.setSysUserId(2);
        repairInfo.setSysUserId(user.getId());
        model.addAttribute("orderList", repairInfoService.selectRepairPage(pageHelper, repairInfo));
        return "message";
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
     * 新增缴费记录信息
     *
     * @param paymentRecord 缴费记录信息
     * @return 结果
     */
    @PostMapping("paymentRecord")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public R save(PaymentRecord paymentRecord, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        paymentRecord.setUserId(user.getId());
        // 获取工单信息
        OrderInfo orderInfo = orderInfoService.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getOrderCode, paymentRecord.getOrderCode()));
        paymentRecord.setMoney(orderInfo.getMoney());
        paymentRecord.setServerType(orderInfo.getServerType());
        paymentRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 更新工单状态为正在维修
        orderInfoService.update(Wrappers.<OrderInfo>lambdaUpdate().set(OrderInfo::getStatus, 3).eq(OrderInfo::getOrderCode, orderInfo.getOrderCode()));
        return R.ok(paymentRecordService.save(paymentRecord));
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
