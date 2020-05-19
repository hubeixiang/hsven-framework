package org.framework.hsven.file.location.fileutil;

import org.junit.Test;


public class TestPictureBase64Util {

    @Test
    public void testBase64() {
        String path = "D:\\02xStaff\\image\\";
        String oriFile = "儿子的画.jpg";
        String imageContext = PictureBase64Util.generateBase64Context(path + oriFile);
        System.out.println(imageContext);
        PictureBase64Util.generateImageFile(imageContext, path, "xxx.jpg");
    }
}
