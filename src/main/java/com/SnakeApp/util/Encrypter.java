package com.SnakeApp.util;


import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import java.util.Base64;

/**
 * Using user pin encryption
 */
@Service
public class Encrypter {


    private static final String CIPHER_TYPE = "DESede/ECB/PKCS5Padding";
    private static final String PROVIDER_NAME = "SunJCE";
    private static final String key = "qwejda!@#dasd235412faced";



    public static String encrypt(String value)  {
        try {

            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("DESede");
            SecretKey secureKey = keyFactory.generateSecret(dks);


            Cipher ecipher = Cipher.getInstance(CIPHER_TYPE,PROVIDER_NAME);
            ecipher.init(Cipher.ENCRYPT_MODE, secureKey);

            if (value == null)
                return null;

            // Encode the string into bytes using utf-8
            byte[] utf8 = value.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return Base64.getEncoder().encodeToString(enc);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String  decrypt(String value) {
        try {

            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("DESede");
            SecretKey secureKey = keyFactory.generateSecret(dks);


            Cipher dcipher = Cipher.getInstance(CIPHER_TYPE,PROVIDER_NAME);
            dcipher.init(Cipher.DECRYPT_MODE, secureKey);

            if (value == null)
                return null;

            // Decode base64 to get bytes
            byte[] dec = Base64.getDecoder().decode(value.getBytes());

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        }catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}