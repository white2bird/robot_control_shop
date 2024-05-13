package com.it.service;

import com.it.common.Result;
import com.it.req.LicenseReqDTO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;


/**
 * @author
 * @date 2024/4/8 15:07
 */
public interface LicenseGeneratorService {

    void generateLicense(LicenseReqDTO licenseReqDTO) throws Exception;


    Result uploadLicense(String licence) throws IOException;

    void verify() throws Exception;
}
