package com.it.util;

import com.alibaba.fastjson.JSONObject;


import javax.crypto.Cipher;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PrivatePublicSignEncDec {


    public static final String PUBLIC_KEY = "public";
    public static final String PRIVATE_KEY = "private";
    public static final int KEY_SIZE = 1024;
    public static final Base64.Decoder DECODER64 = Base64.getDecoder();
    public static final Base64.Encoder ENCODER64 = Base64.getEncoder();
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGN_ALGORITHM = "SHA256withRSA";
    public static final String SIGN_STR = "signStr";
    public static final String ENCODE_DATA = "encodeData";

    /**
     * 生成公私钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, String> createPrivatePublicKey() throws NoSuchAlgorithmException {
        Map<String, String> keyMap = new HashMap<>();
        KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        generator.initialize(KEY_SIZE, new SecureRandom());
        KeyPair keyPair = generator.generateKeyPair();

        String privateKeyStr = ENCODER64.encodeToString(keyPair.getPrivate().getEncoded());
        String publicKeyStr = ENCODER64.encodeToString(keyPair.getPublic().getEncoded());
        keyMap.put(PRIVATE_KEY, privateKeyStr);
        keyMap.put(PUBLIC_KEY, publicKeyStr);
        return keyMap;
    }

    /**
     * 获取公钥
     *
     * @param publicKeyStr
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodeKey = DECODER64.decode(publicKeyStr.getBytes());
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(new X509EncodedKeySpec(decodeKey));
    }

    /**
     * 获取私钥
     *
     * @param privateKeyStr
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String privateKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodeKey = DECODER64.decode(privateKeyStr.getBytes());
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodeKey));
    }


    /**
     * 私钥对数据签名结果
     *
     * @param privateKeyStr
     * @param data
     * @return
     */
    public static String signByPrivateKey(String privateKeyStr, String data) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        byte[] sign = signature.sign();
        String signStr = ENCODER64.encodeToString(sign);
        return signStr;
    }

    /**
     * 通过公钥验证私钥对数据的签名
     *
     * @param publicKeyStr
     * @param data
     * @param signStr
     * @return
     */
    public static boolean verifySign(String publicKeyStr, String data, String signStr) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Signature verifySign = Signature.getInstance(SIGN_ALGORITHM);
        verifySign.initVerify(publicKey);
        verifySign.update(data.getBytes());
        return verifySign.verify(DECODER64.decode(signStr));
    }


    /**
     * 通过公钥加密
     *
     * @param str
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static String encrypt(String str, String publicKeyStr) throws Exception {
        byte[] decodePublic = DECODER64.decode(publicKeyStr);
        RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decodePublic));
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        String outStr = ENCODER64.encodeToString(cipher.doFinal(str.getBytes()));
        return outStr;
    }


    /**
     * 将str通过私钥解密
     *
     * @param str
     * @param privateKeyStr
     * @return
     */
    public static String decrypt(String str, String privateKeyStr) throws Exception {
        byte[] decodeInput = DECODER64.decode(str.getBytes());
        byte[] decodePrivate = DECODER64.decode(privateKeyStr.getBytes());
        RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decodePrivate));
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        String outStr = new String(cipher.doFinal(decodeInput));
        return outStr;
    }

    /**
     * 生成license加密信息
     *
     * @return
     */
    public static Map<String, String> generateLicense() {
        try {
            Map<String, String> keyPairMap = createPrivatePublicKey();
            String privateKeyStr = keyPairMap.get(PRIVATE_KEY);
            String publicKeyStr = keyPairMap.get(PUBLIC_KEY);

            LicenseInfo licenseInfo = new LicenseInfo();  // 设置一些license信息
            licenseInfo.setCreateDate(new Date());
            licenseInfo.setLinceseCde("chw");
            licenseInfo.setValidTime(7 * 24 * 3600 * 1000L);

            String data = JSONObject.toJSONString(licenseInfo);

            String signStr = signByPrivateKey(privateKeyStr, data);

            // 这个map里面的东西就是给到使用方的整体license信息，使用方可以用对应的公钥，签名信息，加密的数据进行
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put(PUBLIC_KEY, publicKeyStr);
            resultMap.put(SIGN_STR, signStr);

            // 生成license的话可以是不用publicKey加密license信息，使用其他加密算法，后用私钥进行签名就行，如果这样，就不要对外暴露私钥，暴露私钥会给使用方人员篡改data的
            resultMap.put(PRIVATE_KEY, privateKeyStr);

            // 下面是使用
            boolean result = verifySign(publicKeyStr, data, signStr);
            System.out.println("签名验证结果result:" + result);
            if (result) {
//                String decodeData = decrypt(encodeData, privateKeyStr);
//                System.out.println("解密后的data:" + decodeData);
                LicenseInfo decodeObj = JSONObject.parseObject(data, LicenseInfo.class);
                System.out.println("decodeObj:" + decodeObj);
            }
            return resultMap;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * A和B连接的时候需要通知对方自己的公钥
     * A给B发消息，A使用B的公钥加密数据，A使用自己的私钥签名加密后的数据，B拿到加密后的数据和签名信息后，B用A的公钥进行签名信息的验证，然后B用自己的私钥进行数据解密。
     * B给A发消息，B使用A的公钥加密数据，B使用自己的私钥签名加密后的数据，A拿到加密后的数据和签名信息后，A用B的公钥进行签名信息的验证，然后A用自己的私钥进行数据解密。
     * 两者的消息互发是同理的都分为四个步骤
     */
    public static void sendClentAClientB() throws Exception {
        Map<String, String> kepMapOfA = createPrivatePublicKey();
        Map<String, String> keyMapOfB = createPrivatePublicKey();
        String privateKeyStrA = kepMapOfA.get(PRIVATE_KEY);
        String publicKeyStrA = kepMapOfA.get(PUBLIC_KEY);
        String privateKeyStrB = keyMapOfB.get(PRIVATE_KEY);
        String publicKeyStrB = keyMapOfB.get(PUBLIC_KEY);

        // a发给b的消息用b的公钥加密，用a自己的私钥签名
        String msgAToB = "哈哈哈哈哈这是A发送给B";
        // 步骤1，用对方的公钥加密
        String encodeData = encrypt(msgAToB, publicKeyStrB);
        // 步骤2，用自己的私钥签名
        String signStr = signByPrivateKey(privateKeyStrA, encodeData);

        //b收到消息后，用a的公钥验证签名，然后用b自己的私钥解密
        // 步骤3，用对方的公钥验证签名
        boolean validResult = verifySign(publicKeyStrA, encodeData, signStr);
        if (validResult) {
            System.out.println("验证签名成功");
            // 步骤4，用自己的私钥解密数据
            String decodeData = decrypt(encodeData, privateKeyStrB);
            System.out.println("decodeData:" + decodeData);
        }
        else {
            System.out.println("验证失败");
        }

    }

    /**
     * 公私钥总体流程： 公钥加密，私钥签名，公钥签名验证，私钥解密
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQAqsracQM5fc1KFHqBpRgjTIF5tmmsbePM2WpjJTTopHQGzr76o1uAKzsMVqHZ3/a1anBp3/ubqjCr0DFFOvCcVB6cShY6so5AAh5mF3bL1wLw+w8vp7nNFJMoV9EdVci6mYTodRkdIh5yAmVBTKf53oCiQnTQutt2Tu4XmjsxQIDAQAB";
        String trim = "eyJjcmVhdGVEYXRlIjoxNzEyMzYxNjAwMDAwLCJtYWMiOiJhYWFhYWFjYyIsInZhbGlkVGltZSI6ODY0MDAwMDB9,RWLsE6Yew6zir6XlJFYjXSasLWfSSlNnbj+UDuuv6aUwadxMOMx4QUoGEuez+gAHpwXa64xa3tD1pTgc3q+kqTBnvrUJr8/kA9hsab2YqMgic0D9etw91hgcVA39hRMw5aOLgHBdTbLYHr2SO7hLy4cZmJ3nh1C9x5A2DswLNos=\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000".trim();
        String[] split = trim.split(",");
        String base64Origin = split[0];
        String sign = split[1];
        byte[] bytes = Base64.getDecoder().decode(base64Origin);
        String json = new String(bytes);
        boolean b = verifySign(pub, json, sign);
        System.out.println(b);
    }
}

