package org.framework.hsven.file.fileutil;

import org.junit.Test;

import java.util.List;


public class TestPictureBase64Util {

    @Test
    public void testBase64() {
        String filepath = "D:/03Downloads/Browser/zrs-a-nav-config-file.define";
        List list = SimpleFileUtil.read2ListString(filepath);
        System.out.println(list);

        String path = "D:\\02xStaff\\image\\";
        String oriFile = "儿子的画.jpg";
        String imageContext = PictureBase64Util.generateBase64Context(path + oriFile);
//        System.out.println(imageContext);
        PictureBase64Util.generateImageFile(imageContext, path, "xxx.jpg");
    }
}
