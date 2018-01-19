/*
package com.wizinno.apollo.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

*/
/**
 * Created by HP on 2017/10/10.
 *//*

public class ExcelUtils {

    Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
// TODO Auto-generated method stub
        Date date = new Date();
        String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
        HSSFSheet sheet;
        HSSFCell cell;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");
        int sheetNum=1;
        sheet = workbook.createSheet(sheetNum+"");



        List<String> titles = (List<String>) model.get("titles");
        int len = titles.size();
        HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont headerFont = workbook.createFont(); //标题字体
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short)11);
        headerStyle.setFont(headerFont);
        short width = 20,height=25*20;
        sheet.setDefaultColumnWidth(width);
        for(int i=0; i<len; i++){ //设置标题
            String title = titles.get(i);
            cell = getCell(sheet, 0, i);
            cell.setCellStyle(headerStyle);
            setText(cell,title);
        }
        sheet.getRow(0).setHeight(height);

        HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        List<PageData> varList = (List<PageData>) model.get("varList");
        int varCount = varList.size();
        for(int i=0; i<varCount; i++){
            PageData vpd = varList.get(i);
            if(i%65535==0){
                sheet=null;
                sheetNum+=1;
                sheet=workbook.createSheet(sheetNum+"");
            }
            for(int j=0;j<len;j++){
                String varstr = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
                cell = getCell(sheet, i+1, j);
                cell.setCellStyle(contentStyle);
                setText(cell,varstr);


            }

        }

    }

}
*/
