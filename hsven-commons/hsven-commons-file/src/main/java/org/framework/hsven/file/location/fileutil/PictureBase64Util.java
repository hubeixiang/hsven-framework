package org.framework.hsven.file.location.fileutil;

import org.framework.hsven.file.location.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片转换为base64编码的转换工具
 */
public class PictureBase64Util {
    private static Logger logger = LoggerFactory.getLogger(PictureBase64Util.class);

    /**
     * 将指定的图片文件内容转换为base64编码字符串
     *
     * @param absolutePathFile D:\\02xStaff\\image\\abc.jpg
     * @return base64编码字符串
     */
    public static String generateBase64Context(String absolutePathFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        //待处理的图片
        if (StringUtils.isEmpty(absolutePathFile)) {
            return null;
        }
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(absolutePathFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            logger.error(String.format("PictureBase64Util.generateBase64Context Exception:%s", e.getMessage()), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    /**
     * 将base64编码字符串转换为对应的图片文件
     *
     * @param imageBase64Context  base64字符串的内容
     * @param absolutePathFileDir 要转换的文件存放目录
     * @param imageFileName       存放的image文件名,带文件名后缀   xxxxxx.png,yyyyy.jpg
     * @return 生成的图片文件
     */
    public static File generateImageFile(String imageBase64Context, String absolutePathFileDir, String imageFileName) {
        //对字节数组字符串进行Base64解码并生成图片
        //图像数据为空
        if (StringUtils.isEmpty(imageBase64Context))
            return null;

        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imageBase64Context);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    //调整异常数据
                    b[i] += 256;
                }
            }
            //创建目录
            SimpleFileUtil.createNewDir(absolutePathFileDir, false);

            String imageFile = absolutePathFileDir + File.separator + imageFileName;

            File file = new File(imageFile);
            out = new FileOutputStream(file);
            out.write(b);
            out.flush();
            return file;
        } catch (Exception e) {
            logger.error(String.format("PictureBase64Util.generateImageFile Exception:%s", e.getMessage()), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
