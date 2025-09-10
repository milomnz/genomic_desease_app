package org.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class ChecksumUtil {

    public static String calculateMD5(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }

            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error calculando MD5", e);
        }
    }
}
