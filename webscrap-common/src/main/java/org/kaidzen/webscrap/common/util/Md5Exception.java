package org.kaidzen.webscrap.common.util;

import java.security.NoSuchAlgorithmException;

class Md5Exception extends RuntimeException {
    Md5Exception(String message, NoSuchAlgorithmException e) {
    }
}
