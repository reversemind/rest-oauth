package ru.ttk.baloo.rest.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public class MD5Test {

    private final static Logger LOG = LoggerFactory.getLogger(MD5Test.class);

    @Test
    public void testGo(){
        final String value = "n.shklovsky";
        LOG.info("MD5 #0.0:" + DigestUtils.md5Hex("1qazXSW@"));
        LOG.info("MD5 #0.1:" + getMD5("1qazXSW@"));

        LOG.info("MD5 #1:" + DigestUtils.md5Hex(value));
        LOG.info("MD5 #2:" + DigestUtils.md5Hex(DigestUtils.md5Hex(value)));
        LOG.info("MD5 #3:" + DigestUtils.md5Hex(DigestUtils.md5Hex(DigestUtils.md5Hex(value))));
        LOG.info("MD5 #4:" + DigestUtils.md5Hex(DigestUtils.md5Hex(DigestUtils.md5Hex(DigestUtils.md5Hex(value)))));

        LOG.info("sha256Hex:" + DigestUtils.sha256Hex(value));
        LOG.info("sha512Hex:" + DigestUtils.sha512Hex(value));

        LOG.info("\n\n");

        final String l = "n.shklovsky";
        LOG.info("length:" + l.length());

        LOG.info("result:" + l.substring(l.length()/2, l.length()) + l.substring(0,l.length()/2) );
    }

    /**
     * Actually this is a Alex Barkov wrong implementation of MD5
     * @param s
     * @return
     */
    public static String getMD5(String s){
        if(s==null) return null;
        byte[] bytesOfMessage;
        try {
            bytesOfMessage = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] digest = md.digest(bytesOfMessage);
        BigInteger bigInt = new BigInteger(1,digest);
        return bigInt.toString(16);
    }

}
