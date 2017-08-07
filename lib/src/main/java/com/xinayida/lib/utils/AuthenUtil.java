package com.xinayida.lib.utils;

/**
 * Created by stephan on 16/8/15.
 */

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.spec.X509EncodedKeySpec;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AuthenUtil
{

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    private AuthenUtil()
    {
        throw new AssertionError();
    }

    /**
     * encode By MD5
     *
     * @param str
     * @return String
     */
    public static String encodeByMD5(String str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return new String(encodeHex(messageDigest.digest())).toLowerCase(Locale.CHINA);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     *
     * @param data a byte[] to convert to Hex characters
     * @return A char[] containing hexadecimal characters
     */
    protected static char[] encodeHex(final byte[] data)
    {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++)
        {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return out;
    }

    public static final String KEY_ALGORITHM = "RSA";

    private static final int MAX_ENCRYPT_BLOCK = 117;

    private static String pkey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBolhnVmwKZQIraMbLY+b8ji" +
            "j/Sm7CuDGSr/Ls/FB7EjyMmfi3b+kGdloTJky1vBlhz7IaOjeySgV2W5CofhhH2SV73RKUHdBXER3j8rkR/pCLro" +
            "tALtkKq8vfNXUMNO9pu7uXTLRraRCO09K/gObs391HzFzszBp+6vq+BbzoTQIDAQAB";

    public static String encryptByPublicKeys(String value)
            throws Exception {
        byte[] data = value.getBytes();
        byte[] keyBytes = Base64Utils.decode(pkey);
//        byte[] keyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(pkey.getBytes());
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        /* 对数据加密 */
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicK);

        /* 由于RSA加密算法对加密内容长度有限制，所以需要分段加密
        *  此处获取待加密内容的字节数组长度，方便后续分段
        *  */
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        /* 对数据分段加密 */
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        String encrypt = Base64Utils.encode(encryptedData);
        out.close();
        return encrypt;
    }

    /**
     * DES加密
     *
     * @param key 服务器“密钥获取”接口的后八位
     * @param src 原文
     * @return 密文
     */
    public static String encryptByDes(String key, String src)
    {

        byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        String ALGORITHM = "DES/CBC/PKCS5Padding";
        String ALGORITHM_KEY = "DES";
        String encrypt = "";
        try
        {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), ALGORITHM_KEY), zeroIv);
            byte[] encryptByte = cipher.doFinal(src.getBytes());
            encrypt = Base64Utils.encode(encryptByte);
            return encrypt;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // NoSuchPaddingException 忽略
            // UnsupportedEncodingException 忽略
        }
        return encrypt;
    }
}