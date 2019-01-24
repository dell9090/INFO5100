package edu.neu.app;

import java.security.MessageDigest;

public class MD5Security {
    public static String MD5(String s) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        char[] cArray = s.toCharArray();
        byte[] bArray = new byte[cArray.length];

        for (int i = 0; i < cArray.length; i++)
            bArray[i] = (byte) cArray[i];

        byte[] md5Bytes = md5.digest(bArray);

        StringBuffer hex = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hex.append("0");
            hex.append(Integer.toHexString(val));
        }
        return hex.toString();
    }

    public static String encrypt(String s) {
        // String s = new String(inStr);
        char[] a = s.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 'l');
        }
        return new String(a);
    }

    public static String decrypt(String s) {
        char[] a = s.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 'l');
        }
        return new String(a);
    }
}
