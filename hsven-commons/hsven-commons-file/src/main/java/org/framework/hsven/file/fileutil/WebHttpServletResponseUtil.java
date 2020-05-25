package org.framework.hsven.file.fileutil;

import org.framework.hsven.file.FileConstants;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class WebHttpServletResponseUtil {
    /**
     * 浏览器下载附件时,设置下载的 HttpServletResponse 头信息
     *
     * @param response web 返回信息
     * @param filename web浏览器下载的文件名  (下载文件.zip  下载文件.txt  下载文件.xlxs 等等)
     * @throws UnsupportedEncodingException
     */
    public static void setHttpServletResponseAttachmentInfo(HttpServletResponse response, String filename)
            throws UnsupportedEncodingException {
        String charSet = FileConstants.CHARACTER_SET_UTF8;
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding(charSet);
        //response.setHeader("Content-Disposition", "attachment; filename="+new String(downloadFile.getBytes("utf-8"), "ISO8859-1" ));
        //response.setHeader( "Content-Disposition", "attachment;filename=" + new String( downloadFile.getBytes("gb2312"), "ISO8859-1" ) );
        response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, charSet) + ";filename*=utf-8''" + java.net.URLEncoder.encode(filename, charSet));
    }
}
