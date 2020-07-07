package br.com.navita.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
    
    private static final String KEY = "cpmprojectnavita";
    
    public static String encryptPassword(String password) {
	try {
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY.getBytes(), "AES"));
	    byte[] cipherPassword = cipher.doFinal(password.getBytes());
	    return Base64.getEncoder().encodeToString(cipherPassword);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return "";
    }

}
