package cc.mrbird.febs.cos.controller.information;

import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.regexp.internal.RE;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.util.Date;

@Controller
@RequestMapping("/business")
@AllArgsConstructor
public class UserBusinessController {

    private final IUserInfoService userInfoService;

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
}
