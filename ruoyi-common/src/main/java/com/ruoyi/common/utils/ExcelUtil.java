package com.ruoyi.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.config.Global;

/**
 * Excel相关处理
 * 
 * @author ruoyi
 */
public class ExcelUtil<T>
{
    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    public Class<T> clazz;

    public ExcelUtil(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    /**
     * 对excel表单默认第一个索引名转换成list
     * 
     * @param input 输入流
     * @return 转换后集合
     */
    public List<T> importExcel(InputStream input) throws Exception
    {
        return importExcel(StringUtils.EMPTY, input);
    }

    /**
     * 对excel表单指定表格索引名转换成list
     * 
     * @param sheetName 表格索引名
     * @param input 输入流
     * @return 转换后集合
     */
    public List<T> importExcel(String sheetName, InputStream input) throws Exception
    {
        List<T> list = new ArrayList<T>();

        Workbook workbook = WorkbookFactory.create(input);
        Sheet sheet = null;
        if (StringUtils.isNotEmpty(sheetName))
        {
            // 如果指定sheet名,则取指定sheet中的内容.
            sheet = workbook.getSheet(sheetName);
        }
        else
        {
            // 如果传入的sheet名不存在则默认指向第1个sheet.
            sheet = workbook.getSheetAt(0);
        }

        if (sheet == null)
        {
            throw new IOException("文件sheet不存在");
        }

        int rows = sheet.getPhysicalNumberOfRows();

        if (rows > 0)
        {
            // 默认序号
            int serialNum = 0;
            // 有数据时才处理 得到类的所有field.
            Field[] allFields = clazz.getDeclaredFields();
            // 定义一个map用于存放列的序号和field.
            Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
            for (int col = 0; col < allFields.length; col++)
            {
                Field field = allFields[col];
                // 将有注解的field存放到map中.
                if (field.isAnnotationPresent(Excel.class))
                {
                    // 设置类的私有字段属性可访问.
                    field.setAccessible(true);
                    fieldsMap.put(++serialNum, field);
                }
            }
            for (int i = 1; i < rows; i++)
            {
                // 从第2行开始取数据,默认第一行是表头.
                Row row = sheet.getRow(i);
                int cellNum = serialNum;
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
                        // 先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
                        row.getCell(j).setCellType(CellType.STRING);
                        cell = row.getCell(j);
                    }

                    String c = cell.getStringCellValue();
                    if (StringUtils.isEmpty(c))
                    {
                        continue;
                    }

                    // 如果不存在实例则新建.
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // 从map中得到对应列的field.
                    Field field = fieldsMap.get(j + 1);
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
                        if (cell.getCellTypeEnum() == CellType.NUMERIC)
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
     * @param list 导出数据集合
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public AjaxResult exportExcel(List<T> list, String sheetName)
    {
        OutputStream out = null;
        HSSFWorkbook workbook = null;
        try
        {
            // 得到所有定义字段
            Field[] allFields = clazz.getDeclaredFields();
            List<Field> fields = new ArrayList<Field>();
            // 得到所有field并存放到一个list中.
            for (Field field : allFields)
            {
                if (field.isAnnotationPresent(Excel.class))
                {
                    fields.add(field);
                }
            }

            // 产生工作薄对象
            workbook = new HSSFWorkbook();
            // excel2003中每个sheet中最多有65536行
            int sheetSize = 65536;
            // 取出一共有多少个sheet.
            double sheetNo = Math.ceil(list.size() / sheetSize);
            for (int index = 0; index <= sheetNo; index++)
            {
                // 产生工作表对象
                HSSFSheet sheet = workbook.createSheet();
                if (sheetNo == 0)
                {
                    workbook.setSheetName(index, sheetName);
                }
                else
                {
                    // 设置工作表的名称.
                    workbook.setSheetName(index, sheetName + index);
                }
                HSSFRow row;
                HSSFCell cell; // 产生单元格

                // 产生一行
                row = sheet.createRow(0);
                // 写入各个字段的列头名称
                for (int i = 0; i < fields.size(); i++)
                {
                    Field field = fields.get(i);
                    Excel attr = field.getAnnotation(Excel.class);
                    // 创建列
                    cell = row.createCell(i);
                    // 设置列中写入内容为String类型
                    cell.setCellType(CellType.STRING);
                    HSSFCellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    if (attr.name().indexOf("注：") >= 0)
                    {
                        HSSFFont font = workbook.createFont();
                        font.setColor(HSSFFont.COLOR_RED);
                        cellStyle.setFont(font);
                        cellStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
                        sheet.setColumnWidth(i, 6000);
                    }
                    else
                    {
                        HSSFFont font = workbook.createFont();
                        // 粗体显示
                        font.setBold(true);
                        // 选择需要用到的字体格式
                        cellStyle.setFont(font);
                        cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_YELLOW.getIndex());
                        // 设置列宽
                        sheet.setColumnWidth(i, (int) ((attr.width() + 0.72) * 256));
                        row.setHeight((short) (attr.height() * 20));
                    }
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyle.setWrapText(true);
                    cell.setCellStyle(cellStyle);

                    // 写入列名
                    cell.setCellValue(attr.name());

                    // 如果设置了提示信息则鼠标放上去提示.
                    if (StringUtils.isNotEmpty(attr.prompt()))
                    {
                        // 这里默认设了2-101列提示.
                        setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, i, i);
                    }
                    // 如果设置了combo属性则本列只能选择不能输入
                    if (attr.combo().length > 0)
                    {
                        // 这里默认设了2-101列只能选择不能输入.
                        setHSSFValidation(sheet, attr.combo(), 1, 100, i, i);
                    }
                }

                int startNo = index * sheetSize;
                int endNo = Math.min(startNo + sheetSize, list.size());
                // 写入各条记录,每条记录对应excel表中的一行
                HSSFCellStyle cs = workbook.createCellStyle();
                cs.setAlignment(HorizontalAlignment.CENTER);
                cs.setVerticalAlignment(VerticalAlignment.CENTER);
                for (int i = startNo; i < endNo; i++)
                {
                    row = sheet.createRow(i + 1 - startNo);
                    // 得到导出对象.
                    T vo = (T) list.get(i);
                    for (int j = 0; j < fields.size(); j++)
                    {
                        // 获得field.
                        Field field = fields.get(j);
                        // 设置实体类私有属性可访问
                        field.setAccessible(true);
                        Excel attr = field.getAnnotation(Excel.class);
                        try
                        {
                            // 设置行高
                            row.setHeight((short) (attr.height() * 20));
                            // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                            if (attr.isExport())
                            {
                                // 创建cell
                                cell = row.createCell(j);
                                cell.setCellStyle(cs);
                                if (vo == null)
                                {
                                    // 如果数据存在就填入,不存在填入空格.
                                    cell.setCellValue("");
                                    continue;
                                }

                                String dateFormat = attr.dateFormat();
                                String readConverterExp = attr.readConverterExp();
                                if (StringUtils.isNotEmpty(dateFormat))
                                {
                                    cell.setCellValue(DateUtils.parseDateToStr(dateFormat, (Date) field.get(vo)));
                                }
                                else if (StringUtils.isNotEmpty(readConverterExp))
                                {
                                    cell.setCellValue(convertByExp(String.valueOf(field.get(vo)), readConverterExp));
                                }
                                else
                                {
                                    cell.setCellType(CellType.STRING);
                                    // 如果数据存在就填入,不存在填入空格.
                                    cell.setCellValue(StringUtils.isNull(field.get(vo)) ? attr.defaultValue() : field.get(vo) + attr.suffix());
                                }
                            }
                        }
                        catch (Exception e)
                        {
                            log.error("导出Excel失败{}", e.getMessage());
                        }
                    }
                }
            }
            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            workbook.write(out);
            return AjaxResult.success(filename);
        }
        catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
            return AjaxResult.error("导出Excel失败，请联系网站管理员！");
        }
        finally
        {
            if (workbook != null)
            {
                try
                {
                    workbook.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
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
        HSSFDataValidation dataValidationView = new HSSFDataValidation(regions, constraint);
        dataValidationView.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(dataValidationView);
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
        HSSFDataValidation dataValidationList = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(dataValidationList);
        return sheet;
    }
    
    /**
     * 解析导出值 0=男,1=女,2=未知
     * 
     * @param propertyValue 参数值
     * @param converterExp 翻译注解
     * @return 解析后值
     * @throws Exception
     */
    public static String convertByExp(String propertyValue, String converterExp) throws Exception
    {
        try
        {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource)
            {
                String[] itemArray = item.split("=");
                if (itemArray[0].equals(propertyValue))
                {
                    return itemArray[1];
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        return propertyValue;
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename)
    {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xls";
        return filename;
    }

    /**
     * 获取下载路径
     * 
     * @param filename 文件名称
     */
    public String getAbsoluteFile(String filename)
    {
        String downloadPath = Global.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }
}