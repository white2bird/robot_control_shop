package com.it.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.it.common.Result;
import com.it.constants.MacAddress;
import com.it.constants.PublicPrivate;
import com.it.constants.SignInfo;
import com.it.util.LicenseInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import static com.it.util.PrivatePublicSignEncDec.verifySign;

/**
 * @author 
 * @date 2024/4/8 17:28
 */
public class LicenseInterceptor implements HandlerInterceptor {

    private PublicPrivate publicPrivate;

    private MacAddress macAddress;

    private SignInfo signInfo;

    public LicenseInterceptor(PublicPrivate publicPrivate, MacAddress macAddress, SignInfo signInfo){
        this.publicPrivate = publicPrivate;
        this.macAddress = macAddress;
        this.signInfo = signInfo;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        if(requestURI.startsWith("/license") || requestURI.startsWith("/system")){
            return true;
        }
        String method = request.getMethod();
        if("OPTIONS".equals(method.toUpperCase())){
            return true;
        }


        String content = signInfo.getSign();
        if(StringUtils.isEmpty(content)){
            handError(response);
            return false;
        }
        String[] infoArr = content.split(",");
        String base64Origin = infoArr[0];
        String sign = infoArr[1];
        byte[] bytes = Base64.getDecoder().decode(base64Origin);
        String json = new String(bytes);
        boolean isValid = verifySign(publicPrivate.getPublicStr(), json, sign);
        if(!isValid){
            System.out.println("验证公钥未通过");
            handError(response);
            return false;
        }
        LicenseInfo licenseInfo = JSONObject.parseObject(json, LicenseInfo.class);
        Date createDate = licenseInfo.getCreateDate();
        Long validTime = licenseInfo.getValidTime();
        if(validTime != -1 && createDate.getTime() + validTime <= System.currentTimeMillis()){
            System.out.println("验证有效期未通过");
            handError(response);
            return false;
        }
        // 验证mac地址
        String mac = licenseInfo.getMac();
        String currentMacAddress = macAddress.getMacAddress();
        if(!mac.equals(currentMacAddress)){
            System.out.println("验证地址未通过");
            handError(response);
            return false;
        }

        return true;
    }

    void handError(HttpServletResponse response){
        Result error = Result.ok(null, 200, "系统未授权");// 错误信息

        response.setStatus(HttpServletResponse.SC_OK); // 设置响应状态为500
        response.setContentType("text/plain"); // 设置响应内容类型
        response.setCharacterEncoding("UTF-8"); // 设置响应字符编码

        try {
            response.getWriter().write(JSONObject.toJSONString(error)); // 将错误信息写入响应流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
