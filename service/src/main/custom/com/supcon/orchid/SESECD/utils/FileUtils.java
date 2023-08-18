package com.supcon.orchid.SESECD.utils;

import com.supcon.orchid.foundation.utils.ByteArrayMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

public class FileUtils {
    /**
     * 图片base64转file对象
     *
     * @param base64String
     * @param fileName
     * @return
     */
    public static File base64ToFile(String base64String, String fileName) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(decodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * file转byte数组
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] fileToByteArr(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        byteArrayOutputStream.close();
        inputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * file对象获取MultipartFile接口实现
     * 满足平台附件调用
     * @return
     */
    public static ByteArrayMultipartFile byteArrayMultipartFileBuild(File file) throws IOException {
        return new ByteArrayMultipartFile("file", FileUtils.fileToByteArr(file), file.getName(), Files.probeContentType(file.toPath()));
    }
}
