package org.kaidzen.webscrap.common.util;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Calculator {

    private static final MessageDigest MD_5 = getMd5();

    private Md5Calculator() {
    }

    public static String calculateMd5(String... args) {
        String joinedString = String.join("", args);
        MD_5.update(joinedString.getBytes(StandardCharsets.UTF_8));
        byte[] digestBites = MD_5.digest();
        return DatatypeConverter.printHexBinary(digestBites).toUpperCase();
    }

    private static MessageDigest getMd5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new Md5Exception("Failed to instantiate MD5", e);
        }
    }
}
