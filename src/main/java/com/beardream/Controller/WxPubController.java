package com.beardream.Controller;

import com.beardream.Utils.TextUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by beardream on 2017/5/10.
 * 微信服务器将发送GET请求到你填写的服务器地址URL上，GET请求携带四个参数，
 * 我们需要将其中三个参数做字典排序、SHA-1加密，然后拿它与另一个参数作对比，
 * 相同则证明该信息来自于微信服务器，即校验通过
 */
@RestController
@RequestMapping("/")
public class WxPubController {

    private String token = "jue";

    @GetMapping
    public String checkName(@RequestParam(name = "signature") String signature,
                          @RequestParam(name = "timestamp") String timestamp,
                          @RequestParam(name = "nonce") String nonce,
                          @RequestParam(name = "echostr") String echostr){
        System.out.println("------开始校验----------");
        //排序
        String sort = sort(token, timestamp, nonce);
        // 加密
        String secret = sha1(sort);
        if (!TextUtil.isBlank(secret) && secret.equals(signature)){
            System.out.println("-----------校验通过-------------");
            return echostr;
        }else {
            System.out.println("---------校验失败------------");
            return "";
        }
    }

    private String sort(String token, String timestamp, String nonce){
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }
        return sb.toString();
    }

    private String sha1(String str){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
