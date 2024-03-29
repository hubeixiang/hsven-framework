package org.framework.hsven.file.excel.writeexcel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 123 on 2015/11/17.
 */
public class WriteExcel2003Util {
    // 标题样式
    private Map<Integer, HSSFCellStyle> titleStyleMap = new HashMap<Integer, HSSFCellStyle>();

    // 行信息内容样式
    private Map<Integer, HSSFCellStyle> contentStyleMap = new HashMap<Integer, HSSFCellStyle>();

    /**
     * 写excel文件
     *
     * @throws IOException
     */
    public Workbook writeExcel(String sheetName, List<String> headInfo, List<HeadType> headType, List<List<String>> contextInfo) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();// 创建新HSSFWorkbook对象
        int titleCount = headInfo.size();// 列数

        //执行样式初始化
        setExcelStyle(wb, titleCount, headType);

        HSSFSheet sheet = wb.createSheet(sheetName);// 创建新的sheet对象

        HSSFRow titleRow = sheet.createRow(0);//创建第一行

        titleRow.setHeightInPoints(20);//20像素
        // 写标题
        for (int k = 0; k < titleCount; k++) {
            //HSSFCell cell = titleRow.createCell((short) k); // 新建一个单元格
            HSSFCell cell = titleRow.createCell(k); // 新建一个单元格
            HSSFCellStyle hssfCellStyle = titleStyleMap.get(k);
            cell.setCellStyle(hssfCellStyle);//设置标题样式
            cell.setCellValue(new HSSFRichTextString(headInfo.get(k))); // 为单元格赋值
            //sheet.setColumnWidth((short) k, (short) 5000);//设置列宽
            sheet.setColumnWidth(k, 5000); //设置列宽
        }

        if (contextInfo == null || contextInfo.size() == 0) {
            return wb;
        }

        int contentCount = contextInfo.size();//总的记录数
        // 写内容
        for (int i = 0; i < contentCount; i++) {
            List<String> contents = contextInfo.get(i);
            HSSFRow row = sheet.createRow((i + 1)); // 新建一行

            for (int j = 0; j < titleCount; j++) {
                HSSFCell cell = row.createCell(j); // 新建一个单元格

                cell.setCellStyle(contentStyleMap.get(j));//设置内容样式
                String cellValue = null;
                if (contents.size() > j) {
                    cellValue = contents.get(j);
                }
                if (cellValue == null || cellValue.equals("null")) {
                    cellValue = "";
                }
                cell.setCellValue(new HSSFRichTextString(cellValue));
            }
        }
        return wb;
    }

    private void setExcelStyle(HSSFWorkbook workBook, int headCount, List<HeadType> headTypes) {
        //标题的样式
        for (int i = 0; i < headCount; i++) {
            HeadType headType = HeadTypeUtil.getHeadType(headTypes, i);
            HSSFCellStyle hssfCellStyle = HeadTypeUtil.createTitleHSSFCellStyle(workBook, headType);
            titleStyleMap.put(i, hssfCellStyle);
        }
        //数据内容的样式
        for (int i = 0; i < headCount; i++) {
            HeadType headType = HeadTypeUtil.getHeadType(headTypes, i);
            HSSFCellStyle hssfCellStyle = HeadTypeUtil.createDataHSSFCellStyle(workBook, headType);
            contentStyleMap.put(i, hssfCellStyle);
        }
    }
}
