package com.it.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.it.common.Result;
import com.it.constants.PublicPrivate;
import com.it.constants.SignInfo;
import com.it.req.LicenseReqDTO;
import com.it.service.LicenseGeneratorService;
import com.it.util.DownLoadUtils;
import com.it.util.LicenseInfo;
import com.it.util.PrivatePublicSignEncDec;
import com.it.util.WriterFileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import static com.it.util.PrivatePublicSignEncDec.verifySign;

/**
 *
 * @date 2024/4/8 15:21
 */
@Service
public class LicenseGeneratorServiceImpl implements LicenseGeneratorService {


    @Resource
    private SignInfo signInfo;


    @Resource
    private PublicPrivate publicPrivate;

    @Override
    public void generateLicense(LicenseReqDTO licenseReqDTO) throws Exception {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        // 设置认证生成逻辑
        LicenseInfo licenseInfo = new LicenseInfo();
        licenseInfo.setCreateDate(licenseReqDTO.getStartDate());
        licenseInfo.setMac(licenseReqDTO.getMac());
        licenseInfo.setValidTime(licenseReqDTO.getExpireDay() == -1 ? -1 : licenseReqDTO.getExpireDay().longValue() * 1000 * 60 * 60 * 24);

        String needToSign = JSONObject.toJSONString(licenseInfo);
        String sign = PrivatePublicSignEncDec.signByPrivateKey(publicPrivate.getPrivateStr(), needToSign);
        sign = Base64.getEncoder().encodeToString(needToSign.getBytes("utf-8")) +"," + sign;

        String fileName = "license.txt";
        // 设置信息给客户端不解析
        String type = new MimetypesFileTypeMap().getContentType(fileName);
        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
        response.setHeader("Content-type",type);
        // 设置编码
        String code = new String(fileName.getBytes("utf-8"), "iso-8859-1");
        // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
        response.setHeader("Content-Disposition", "attachment;filename=" + code);
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        DownLoadUtils.download(fileName, response, sign);
    }

    @Override
    public Result uploadLicense(String licence) throws IOException {
        if(!StringUtils.hasText(licence)){
            throw new RuntimeException("上传凭证错误");
        }
        File file = ResourceUtils.getFile("license.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(licence);
        fileWriter.flush();
        signInfo.setSign(licence);
//        WriterFileUtils.writeFile("verify", "license.txt", licence.trim());
        return Result.ok(null, 200, "写入成功");
    }

    @Override
    public void verify() throws Exception{
        File file = ResourceUtils.getFile("license.txt");
        FileReader fileReader = new FileReader(file);
        String content = IOUtils.toString(fileReader);
        String[] infoArr = content.split(",");
        String base64Origin = infoArr[0];
        String sign = infoArr[1];
        byte[] bytes = Base64.getDecoder().decode(base64Origin);
        String json = new String(bytes);
        boolean b = verifySign(publicPrivate.getPublicStr(), json, sign);
        System.out.println(b);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String encode = Base64.getEncoder().encodeToString("hello word".getBytes("utf-8"));
        System.out.println(encode);
        byte[] decode = Base64.getDecoder().decode(encode);
        System.out.println(new String(decode));
    }
}
