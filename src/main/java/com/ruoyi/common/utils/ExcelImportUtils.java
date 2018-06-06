package com.ruoyi.common.utils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * 导入Excel工具类
 */
public class ExcelImportUtils {

    /**  是否是2003的excel，返回true是2003Excel文件**/
    public  static boolean isExcel2003(String filePath){
         return filePath.matches("^.+\\.(?i)(xls)$");
    }
    /**  是否是2007以上的excel，返回true是2007Excel文件**/
    public  static boolean isExcel2007(String filePath){
         return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            return false;
        }
        return true;
    }

    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String value = "";
        if (cell != null) {
            switch(cell.getCellTypeEnum()){
                case NUMERIC:// 数字
                    value = cell.getNumericCellValue()+ " ";
                    if(HSSFDateUtil.isCellDateFormatted(cell)){
                        Date date = cell.getDateCellValue();
                        if(date != null){
                            value = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,date); //  日期格式化
                        }else{
                            value = "";
                        }
                    }else {
                        //  解析cell时候 数字类型默认是double类型的 但是想要获取整数类型 需要格式化
                        value = new DecimalFormat("0").format(cell.getNumericCellValue());
                    }
                    break;
                case STRING: //  字符串
                    value = cell.getStringCellValue();
                    break;
                case BOOLEAN:   //  Boolean类型
                    value = cell.getBooleanCellValue()+"";
                    break;
                case BLANK:   // 空值
                    value = "";
                    break;
                case ERROR: // 错误类型
                    value ="非法字符";
                    break;
                default:
                    value = "未知类型";
                    break;
            }

        }
        return value.trim();
    }
}
