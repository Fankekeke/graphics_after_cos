package cc.mrbird.febs.cos.controller.information;

import cc.mrbird.febs.common.utils.FileUtil;
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

    /**
     * 文件
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping("/fileUpload")
    public String upload(@RequestParam("avatar") MultipartFile file){
        // 1定义要上传文件 的存放路径
        String localPath = "G:/Project/售后管理系统/file";
        // 2获得文件名字
        String fileName = file.getOriginalFilename();
        // 2上传失败提示
        String warning = "";
        String newFileName = FileUtil.upload(file, localPath, fileName);
        if (newFileName != null) {
            //上传成功
            warning = newFileName;

        } else {
            warning = "上传失败";
        }
        System.out.println(warning);
        return warning;
    }
}
