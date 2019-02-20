package org.kaidzen.webscrap.license.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public class Md5Calculator {

    private static final MessageDigest MD_5 = getMd5();

    private Md5Calculator() {
    }

    public static String calculateMd5(String... args){
        String joinedString = Arrays.stream(args)
                .collect(joining(""));
        MD_5.update(joinedString.getBytes());
        byte[] digestBites = MD_5.digest();
        return DatatypeConverter.printHexBinary(digestBites).toUpperCase();
    }

    private static MessageDigest getMd5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to instantiate MD5", e);
        }
    }
}
