package com.ruoyi.common.utils.poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.shiro.web.session.OnlineWebSessionManager;
import com.ruoyi.framework.web.domain.AjaxResult;

/**
 * Excel相关处理
 * 
 * @author ruoyi
 */
public class ExcelUtil<T>
{

    private static final Logger log = LoggerFactory.getLogger(OnlineWebSessionManager.class);

    public Class<T> clazz;

    public ExcelUtil(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    public List<T> importExcel(String sheetName, InputStream input) throws Exception
    {
        List<T> list = new ArrayList<T>();

        Workbook workbook = WorkbookFactory.create(input);
        Sheet sheet = workbook.getSheet(sheetName);
        if (!sheetName.trim().equals(""))
        {
            sheet = workbook.getSheet(sheetName); // 如果指定sheet名,则取指定sheet中的内容.
        }
        if (sheet == null)
        {
            sheet = workbook.getSheetAt(0); // 如果传入的sheet名不存在则默认指向第1个sheet.
        }
        int rows = sheet.getPhysicalNumberOfRows();

        if (rows > 0)
        {
            // 有数据时才处理
            Field[] allFields = clazz.getDeclaredFields(); // 得到类的所有field.
            Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>(); // 定义一个map用于存放列的序号和field.
            for (Field field : allFields)
            {
                // 将有注解的field存放到map中.
                if (field.isAnnotationPresent(Excel.class))
                {
                    Excel attr = field.getAnnotation(Excel.class);
                    int col = getExcelCol(attr.column());// 获得列号
                    field.setAccessible(true);// 设置类的私有字段属性可访问.
                    fieldsMap.put(col, field);
                }
            }
            for (int i = 1; i < rows; i++)
            {
                // 从第2行开始取数据,默认第一行是表头.
                Row row = sheet.getRow(i);
                int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();
                T entity = null;
                for (int j = 0; j < cellNum; j++)
                {
                    Cell cell = row.getCell(j);
                    if (cell == null)
                    {
                        continue;
                    }
                    else
                    {
                        // 先设置Cell的类型，然后就可以把纯数字作为String类型读进来了 by zhuyangyong 20171228
                        row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                        cell = row.getCell(j);
                    }

                    String c = cell.getStringCellValue();
                    if (c.equals(""))
                    {
                        continue;
                    }

                    entity = (entity == null ? clazz.newInstance() : entity);// 如果不存在实例则新建.
                    Field field = fieldsMap.get(j);// 从map中得到对应列的field.
                    // 取得类型,并根据对象类型设置值.
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType)
                    {
                        field.set(entity, String.valueOf(c));
                    }
                    else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType))
                    {
                        field.set(entity, Integer.parseInt(c));
                    }
                    else if ((Long.TYPE == fieldType) || (Long.class == fieldType))
                    {
                        field.set(entity, Long.valueOf(c));
                    }
                    else if ((Float.TYPE == fieldType) || (Float.class == fieldType))
                    {
                        field.set(entity, Float.valueOf(c));
                    }
                    else if ((Short.TYPE == fieldType) || (Short.class == fieldType))
                    {
                        field.set(entity, Short.valueOf(c));
                    }
                    else if ((Double.TYPE == fieldType) || (Double.class == fieldType))
                    {
                        field.set(entity, Double.valueOf(c));
                    }
                    else if (Character.TYPE == fieldType)
                    {
                        if ((c != null) && (c.length() > 0))
                        {
                            field.set(entity, Character.valueOf(c.charAt(0)));
                        }
                    }
                    else if (java.util.Date.class == fieldType)
                    {
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            cell.setCellValue(sdf.format(cell.getNumericCellValue()));
                            c = sdf.format(cell.getNumericCellValue());
                        }
                        else
                        {
                            c = cell.getStringCellValue();
                        }
                    }
                    else if (java.math.BigDecimal.class == fieldType)
                    {
                        c = cell.getStringCellValue();
                    }
                }
                if (entity != null)
                {
                    list.add(entity);
                }
            }
        }

        return list;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param sheetName 工作表的名称
     */
    public AjaxResult exportExcel(List<T> list, String sheetName)
    {
        Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段
        List<Field> fields = new ArrayList<Field>();
        // 得到所有field并存放到一个list中.
        for (Field field : allFields)
        {
            if (field.isAnnotationPresent(Excel.class))
            {
                fields.add(field);
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象
        // excel2003中每个sheet中最多有65536行
        int sheetSize = 65536;
        double sheetNo = Math.ceil(list.size() / sheetSize);// 取出一共有多少个sheet.
        for (int index = 0; index <= sheetNo; index++)
        {
            HSSFSheet sheet = workbook.createSheet();// 产生工作表对象
            if (sheetNo == 0)
            {
                workbook.setSheetName(index, sheetName);
            }
            else
            {
                workbook.setSheetName(index, sheetName + index);// 设置工作表的名称.
            }
            HSSFRow row;
            HSSFCell cell; // 产生单元格

            row = sheet.createRow(0); // 产生一行
            // 写入各个字段的列头名称
            for (int i = 0; i < fields.size(); i++)
            {
                Field field = fields.get(i);
                Excel attr = field.getAnnotation(Excel.class);
                int col = getExcelCol(attr.column()); // 获得列号
                cell = row.createCell(col); // 创建列
                cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列中写入内容为String类型
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                if (attr.name().indexOf("注：") >= 0)
                {
                    HSSFFont font = workbook.createFont();
                    font.setColor(HSSFFont.COLOR_RED);
                    cellStyle.setFont(font);
                    cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
                    sheet.setColumnWidth(i, 6000);
                }
                else
                {
                    HSSFFont font = workbook.createFont();
                    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗体显示
                    cellStyle.setFont(font); // 选择需要用到的字体格式
                    cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
                    // 设置列宽
                    sheet.setColumnWidth(i, 3766);
                }
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                cellStyle.setWrapText(true);
                cell.setCellStyle(cellStyle);

                cell.setCellValue(attr.name());// 写入列名

                // 如果设置了提示信息则鼠标放上去提示.
                if (!attr.prompt().trim().equals(""))
                {
                    setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col); // 这里默认设了2-101列提示.
                }
                // 如果设置了combo属性则本列只能选择不能输入
                if (attr.combo().length > 0)
                {
                    setHSSFValidation(sheet, attr.combo(), 1, 100, col, col); // 这里默认设了2-101列只能选择不能输入.
                }
            }

            int startNo = index * sheetSize;
            int endNo = Math.min(startNo + sheetSize, list.size());
            // 写入各条记录,每条记录对应excel表中的一行
            HSSFCellStyle cs = workbook.createCellStyle();
            cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            for (int i = startNo; i < endNo; i++)
            {
                row = sheet.createRow(i + 1 - startNo);
                T vo = (T) list.get(i); // 得到导出对象.
                for (int j = 0; j < fields.size(); j++)
                {
                    Field field = fields.get(j); // 获得field.
                    field.setAccessible(true); // 设置实体类私有属性可访问
                    Excel attr = field.getAnnotation(Excel.class);
                    try
                    {
                        // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                        if (attr.isExport())
                        {
                            cell = row.createCell(getExcelCol(attr.column()));// 创建cell
                            cell.setCellStyle(cs);
                            try
                            {
                                if (String.valueOf(field.get(vo)).length() > 10)
                                    throw new Exception("长度超过10位就不用转数字了");
                                // 如果可以转成数字则导出为数字类型
                                BigDecimal bc = new BigDecimal(String.valueOf(field.get(vo)));
                                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                cell.setCellValue(bc.doubleValue());
                            }
                            catch (Exception e)
                            {
                                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                if (vo == null)
                                {
                                    cell.setCellValue(""); // 如果数据存在就填入,不存在填入空格.
                                }
                                else
                                {
                                    cell.setCellValue(field.get(vo) == null ? "" : String.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.
                                }

                            }
                        }
                    }
                    catch (Exception e)
                    {
                        log.error("导出Excel失败{}", e);
                    }
                }
            }
        }
        try
        {
            String filename = encodingFilename(sheetName);
            OutputStream out = new FileOutputStream(getfile() + filename);
            workbook.write(out);
            out.close();
            return AjaxResult.success(filename);
        }
        catch (Exception e)
        {
            log.error("关闭flush失败{}", e);
            return AjaxResult.error("导出Excel失败，请联系网站管理员！");
        }
    }

    /**
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
     * 
     * @param col
     */
    public static int getExcelCol(String col)
    {
        col = col.toUpperCase();
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
        int count = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++)
        {
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return count;
    }

    /**
     * 设置单元格上提示
     * 
     * @param sheet 要设置的sheet.
     * @param promptTitle 标题
     * @param promptContent 内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol 开始列
     * @param endCol 结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle, String promptContent, int firstRow,
            int endRow, int firstCol, int endCol)
    {
        // 构造constraint对象
        DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);
        data_validation_view.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(data_validation_view);
        return sheet;
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     * 
     * @param sheet 要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol 开始列
     * @param endCol 结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFValidation(HSSFSheet sheet, String[] textlist, int firstRow, int endRow,
            int firstCol, int endCol)
    {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(data_validation_list);
        return sheet;
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename)
    {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xls";
        return filename;
    }

    public String getfile() throws FileNotFoundException
    {
        return ResourceUtils.getURL("classpath:").getPath() + "static/file/";
    }

}