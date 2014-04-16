package org.springframework.security.authentication.encoding;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * This BullShitPasswordEncoder - is a temp stub because of a WRONG implementation of MD5 inside a CRM (((((
 * !!!
 * People just use library even form Apache Commons
 */
public class BullShitPasswordEncoder extends PlaintextPasswordEncoder {

    private boolean ignorePasswordCase = false;

    public boolean isIgnorePasswordCase() {
        return ignorePasswordCase;
    }

    @Override
    public String encodePassword(String rawPass, Object salt) {
        return wrongMD5(rawPass);
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        String pass1 = encPass + "";

        // Strict delimiters is false because pass2 never persisted anywhere
        // and we want to avoid unnecessary exceptions as a result (the
        // authentication will fail as the encodePassword never allows them)
        String pass2 = encodePassword(rawPass, salt);

        if (ignorePasswordCase) {
            // Note: per String javadoc to get correct results for Locale insensitive, use English
            pass1 = pass1.toLowerCase(Locale.ENGLISH);
            pass2 = pass2.toLowerCase(Locale.ENGLISH);
        }
        return PasswordEncoderUtils.equals(pass1, pass2);
    }

    @Override
    public String[] obtainPasswordAndSalt(String password) {
        return new String[]{password};
    }

    /**
     * Actually this is a Alex Barkoff wrong implementation of MD5 - but we should use it 'cause approx 1M users and all passwords are wrong encoded
     *
     * @param s
     * @return
     */
    public static String wrongMD5(String s) {
        return s;
//        if (s == null) return null;
//        byte[] bytesOfMessage;
//        try {
//            bytesOfMessage = s.getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//
//        MessageDigest md;
//        try {
//            md = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//        byte[] digest = md.digest(bytesOfMessage);
//        BigInteger bigInt = new BigInteger(1, digest);
//        return bigInt.toString(16);
    }
}
