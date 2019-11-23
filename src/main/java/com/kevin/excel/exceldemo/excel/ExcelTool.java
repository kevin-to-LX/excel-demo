package com.kevin.excel.exceldemo.excel;

import com.kevin.excel.exceldemo.common.CommonExceptionCode;
import com.kevin.excel.exceldemo.date.DateUtils;
import com.kevin.excel.exceldemo.i18n.I18nUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Jinyugai
 * @description: excel 工具类
 * @date: Create in 17:15 2019/11/23
 * @modified By:
 */
public class ExcelTool {

    private static final Logger log = LoggerFactory.getLogger(ExcelTool.class);

    public static <T> byte[] createExcel(String template, List<T> tList){
        return createExcel(template,tList,null,ExcelTypeEnum.NOMERGE,null);
    }
    public static <T> String createExcelFile(String template,List<T> tList,ExcelMethodCallBack excelMethodCallBack,ExcelTypeEnum excelTypeEnum,ParamVo paramVo){
        String filePath=null;
        Workbook workbook=null;
        FileOutputStream fileOutputStream=null;
        try {
            workbook=coverWorkbook(template,tList,excelMethodCallBack,excelTypeEnum,paramVo);
            Long time=System.currentTimeMillis();
            filePath=getAbsolutePath(TemplateConst.filePath+time);
            File file=new File(filePath);
            if(!file.getParentFile().exists()){
                if(!file.getParentFile().mkdirs()){
                    log.error("create path:"+file.getParentFile().getPath()+"failure！");
                }
            }
            fileOutputStream=new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
        }catch (Exception e){
            throw new LepException(e.getMessage());
        }finally {
            if(workbook!=null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error("workbook close error");
                }
            }
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error("outputStream close error");
                }
            }
        }
        return filePath;
    }

    public static String storeTempExcel(Workbook workbook){
        String filePath=null;
        FileOutputStream fileOutputStream=null;
        try {
            Long time=System.currentTimeMillis();
            filePath=getAbsolutePath(TemplateConst.filePath+time);
            File file=new File(filePath);
            if(!file.getParentFile().exists()){
                if(!file.getParentFile().mkdirs()){
                    log.error("create path:"+file.getParentFile().getPath()+"failure！");
                }
            }
            fileOutputStream=new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
        }catch (Exception e){
            throw new LepException(e.getMessage());
        }finally {
            if(workbook!=null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error("workbook close error");
                }
            }
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error("outputStream close error");
                }
            }
        }
        return filePath;
    }

    public static <T> byte[] createExcel(String template,List<T> tList,ExcelMethodCallBack excelMethodCallBack,ExcelTypeEnum excelTypeEnum,ParamVo paramVo){
        byte[] bytes;
        Workbook workbook=null;
        ByteArrayOutputStream outputStream=null;
        try {
            workbook=coverWorkbook(template,tList,excelMethodCallBack,excelTypeEnum,paramVo);
            outputStream=new ByteArrayOutputStream();
            workbook.write(outputStream);
            bytes=outputStream.toByteArray();
        }catch (Exception e){
            throw new LepException(e.getMessage());
        }finally {
            if(workbook!=null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error("workbook close error");
                }
            }
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("outputStream close error");
                }
            }
        }
        return bytes;
    }

    /**
     * 根据名称创建模板
     * @param templateName
     * @throws Exception
     */
    public static void createExcel(String templateName)throws Exception {
        createExcel(ExcelXmlTemplateFactory.getTemplate(templateName),null);
    }

    /**
     * 根据名称和限制值创建模板
     * @param templateName
     * @param checkListMap
     * @throws Exception
     */
    public static void createExcel(String templateName, Map<String,List<String>> checkListMap)throws Exception {
        createExcel(ExcelXmlTemplateFactory.getTemplate(templateName),checkListMap);
    }

    /**
     * 创建根据模板文件创建模板
     * @param excelTemplate
     */
    public static void createExcel(ExcelTemplate excelTemplate)throws Exception {
        createExcel(excelTemplate,null);
    }

    private static String refers2Formula(String name,int column,int row){
        //加64转化为大写字母  只支持1-26转化为A-Z
        char c=(char)(column+65);
        String cc=String.valueOf(c);
        return name+"!$"+cc+"$1:$"+cc+"$"+row;
    }

    /**
     * 根据模板文件和限制字段创建模板
     * @param excelTemplate
     * @param checkListMap
     * @throws Exception
     */
    public static void createExcel(ExcelTemplate excelTemplate,Map<String,List<String>> checkListMap)throws Exception {
        if(excelTemplate==null){
            throw new LepException(ExceptionCodeConstant.EXCEL_EXECELTEMPLATE_NULL_EXCEPTION);
        }
        Workbook workbook=createWorkbook(excelTemplate.getType());
        if(workbook==null){
            log.error("excel create failure:workbook is null");
            return;
        }
        ExcelSupport excelSupport=new ExcelSupport();
        excelSupport.setExcelTemplate(excelTemplate);
        Sheet sheet=createSheet(excelSupport, workbook);
        /*//设置格式
        CellStyle defaultStyle=createDefaultStyle(workbook);
        CellStyle cellStyle=coverCellStyle(workbook, excelTemplate);
        //这里sheet，row 都默认将第一个 然后直接写cell
        Sheet sheet=workbook.createSheet();
        Map<String,Integer> name2ColumnMap=new HashMap<>();
        List<ExcelRow> excelRows=excelTemplate.getRows();
        for(int r=0;r<excelRows.size();r++){
            ExcelRow excelRow=excelRows.get(r);
            Row row=sheet.createRow(excelRow.getRowNum());
            row.setHeight(excelRow.getHeight());
            List<ExcelCell> excelCells=excelRow.getCells();
            int i=0;
            for(;i<excelCells.size();i++){
                sheet.setDefaultColumnStyle(i,defaultStyle);
                Cell cell;
                ExcelCell excelCell=excelCells.get(i);
                cell=row.createCell(i);
                name2ColumnMap.put(excelCell.getName(),i);
                sheet.setColumnWidth(i, excelCell.getWidth());
                coverCell(cell, excelCell, cellStyle);
            }
            while(i<excelTemplate.getColumnNum()){
                row.createCell(i);
                i++;
            }
        }*/
        if(checkListMap!=null&&checkListMap.size()>0){
            String CHECKSHEET="checkSheet";
            Sheet checkSheet=workbook.createSheet(CHECKSHEET);
            workbook.setSheetHidden(1,true);
            Set<String> keySet=checkListMap.keySet();
            List<String[]> list=new ArrayList<>();
            for(String key:keySet){
                List<String> checkList=checkListMap.get(key);
                for(int i=0;i<checkList.size();i++){
                    String[] tempArray;
                    if(list.size()>i){
                        tempArray=list.get(i);
                    }else {
                        tempArray=new String[excelTemplate.getColumnNum()];
                        list.add(tempArray);
                    }
                    tempArray[excelSupport.getName2ColumnMap().get(key)]=checkList.get(i);
                }
            }
            for(int j=0;j<list.size();j++){
                Row checkRow=checkSheet.createRow(j);
                String[] checkTempArray=list.get(j);
                for(String k:keySet){
                    int column=excelSupport.getName2ColumnMap().get(k);
                    if(StringUtils.isBlank(checkTempArray[column])){
                        continue;
                    }

                    Cell cell=checkRow.createCell(column);
                    cell.setCellValue(checkTempArray[column]);
                }
            }
            for(String key:keySet){
                String formula=refers2Formula(CHECKSHEET, excelSupport.getName2ColumnMap().get(key), checkListMap.get(key).size());

                // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
                CellRangeAddressList addressList = new CellRangeAddressList(excelTemplate.getRows().size(), 65535, excelSupport.getName2ColumnMap().get(key), excelSupport.getName2ColumnMap().get(key));
                DataValidation dataValidation=createDataValidation(excelTemplate.getType(),addressList, formula,sheet);
                //HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
                sheet.addValidationData(dataValidation);
            }

            /*Set<String> keySet=checkListMap.keySet();
            for(String key:keySet){
                String[] str=new String[checkListMap.get(key).size()];
                checkListMap.get(key).toArray(str);
                DVConstraint dvConstraint=DVConstraint.createExplicitListConstraint(str);
                CellRangeAddressList cellRangeAddressList=new CellRangeAddressList(1,65535,name2ColumnMap.get(key),name2ColumnMap.get(key));
                HSSFDataValidation checkList= new HSSFDataValidation(cellRangeAddressList,dvConstraint);
                sheet.addValidationData(checkList);
            }*/
        }
        String path=excelTemplate.getPath();
        if(StringUtils.isEmpty(path)){
            log.debug("ExcelTool createExcel path is null");
            throw new LepException(ExceptionCodeConstant.EXCEL_PATH_NULL_EXCEPTION);
        }
        FileOutputStream out=null;
        try {
            String absolutePath=getAbsolutePath(path);
            File file=new File(absolutePath);
            if(!file.getParentFile().exists()){
                if(!file.getParentFile().mkdirs()){
                    log.error("create path:"+file.getParentFile().getPath()+"failure！");
                }
            }
            out=new FileOutputStream(absolutePath);
            workbook.write(out);
        } catch (IOException e) {
            log.debug("ExcelTool createExcel io exception",e);
            throw new LepException(ExceptionCodeConstant.EXCEL_FILE_IO_EXCEPTION);
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.debug("ExcelTool exportExcel os close exception",e);
                }
            }
        }

    }

    public static String getAbsolutePath(String path){
        String defaultPath="/opt/template/";
        String absolutePath;
        try{
            absolutePath= ClassUtils.getDefaultClassLoader().getResource("").getPath();
            log.info("excel xmlTemplate path:"+absolutePath);
            File tempFile=new File(absolutePath).getParentFile().getParentFile().getParentFile();
            absolutePath=tempFile.getPath();
            log.info("excel xmlTemplate exe jar path:"+absolutePath);
        }catch (Exception e){
            log.info("get absolute path error，use defaultPath：/opt/template/");
            absolutePath=defaultPath;
        }
        while(path.startsWith("/")){
            path=path.substring(1);
        }
        String[] strs=path.split("/");
        for(String str:strs){
            absolutePath=absolutePath+File.separator+str;
        }
        //去除路径前的file：
        if(absolutePath.startsWith("file:")){
            absolutePath=absolutePath.substring(5);
        }
        log.info("absolutePath info:"+absolutePath);
        return absolutePath;
    }

    /**
     * 构建单元格
     * @param cell
     * @param excelCell
     * @param cellStyle
     */
    public static String coverCell(Cell cell,ExcelCell excelCell,CellStyle cellStyle){
        if(StringUtils.isNotBlank(excelCell.getValue())){
            String value=excelCell.getValue();
            try{
                value= I18nUtil.getTextValue(value);
            }catch (Exception e){
                log.debug("coverCell I18nUtil exception",e);
                value=excelCell.getValue();
            }
            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);
            return value;
        }
        return "";
    }

    /**
     * 创建模板样式
     * @param workbook
     * @param excelTemplate
     * @return
     */
    public static CellStyle coverCellStyle(Workbook workbook, ExcelTemplate excelTemplate){
        CellStyle cellStyle=workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if(excelTemplate.getColor()!=null){
            cellStyle.setFillForegroundColor(excelTemplate.getColor());
            cellStyle.setFillPattern(FillPatternType.forInt(excelTemplate.getPattern()));
        }
        Font font = workbook.createFont();
        font.setFontName(excelTemplate.getFont());
        font.setFontHeightInPoints(excelTemplate.getFontSize());//设置字体大小
        font.setColor(excelTemplate.getFontColor());
        font.setBold(true); //粗体显示
        cellStyle.setFont(font);//选择需要用到的字体格式
        return cellStyle;
    }

    public static CellStyle createDefaultStyle(Workbook workbook){
        CellStyle cellStyle=workbook.createCellStyle();
        DataFormat format=workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        return cellStyle;
    }

    /**
     * 根据类型创建workbook
     * @param type
     * @return
     */
    public static Workbook createWorkbook(String type){
        Workbook workbook=null;
        if(type.equals(TemplateConst.XLS)){
            workbook=new HSSFWorkbook();
        }else if(type.equals(TemplateConst.XLSX)){
            workbook=new SXSSFWorkbook();
        }else{
            throw new LepException(ExceptionCodeConstant.EXCEL_WORKBOOK_NULL_EXCEPTION);
        }
        return workbook;
    }

    public static DataValidation createDataValidation(String type,CellRangeAddressList cellRangeAddressList,String formula,Sheet sheet){
        DataValidation dataValidation=null;
        if(type.equals(TemplateConst.XLS)){
            DVConstraint constraint = DVConstraint.createFormulaListConstraint(formula);
            dataValidation=new HSSFDataValidation(cellRangeAddressList,constraint);
        }else if(type.equals(TemplateConst.XLSX)){
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet)sheet);
            DataValidationConstraint dataValidationConstraint=dvHelper.createFormulaListConstraint(formula);
            dataValidation=dvHelper.createValidation(dataValidationConstraint,cellRangeAddressList);
        }
        return dataValidation;
    }

    /**
     * 根据文件生成workbook
     * @param file
     * @param fileName
     * @return
     * @throws Exception
     */
    public static Workbook createWorkBookByFile(File file,String fileName)throws Exception {
        String name;
        if(file==null){
            log.debug("ExcelTool createWorkbookByFile file is null");
            throw new LepException(ExceptionCodeConstant.EXCEL_FILE_NULL_EXCEPTION);
        }
        if(StringUtils.isNotBlank(fileName)){
            name=fileName;
        }else {
            name=file.getName();
        }
        FileInputStream fileInputStream=new FileInputStream(file);
        return createWorkBookByInputStream(fileInputStream,name);
    }

    /**
     * 根据输入流创建workbook
     * @param is
     * @param fileName
     * @return
     * @throws Exception
     */
    public static Workbook createWorkBookByInputStream(InputStream is,String fileName) throws Exception {
        Workbook workbook=null;
        try{
            if(fileName.endsWith(TemplateConst.XLS)){
                workbook=new HSSFWorkbook(is);
            }else if(fileName.endsWith(TemplateConst.XLSX)){
                workbook=new XSSFWorkbook(is);
            }
        }catch(Exception e){
            log.debug("ExcelTool createWorkBookByInputStream Workbook exception",e);
            throw new LepException(ExceptionCodeConstant.EXCEL_FILE_IO_EXCEPTION);
        }finally {
            if(is!=null){
                is.close();
            }
        }
        return workbook;
    }


    /**
     * 解析Excel文件
     * @param t
     * @param fileName
     * @param templateName
     * @return recordNum 作为行号写入map中
     */
    public static <T> List<Map<String,String>> analyticalExcel(T t,String fileName,String templateName)throws Exception {
        List<Map<String,String>> excelList=new ArrayList<>();
        try{
            ExcelTemplate excelTemplate= ExcelXmlTemplateFactory.getTemplate(templateName);
            if(excelTemplate==null){
                log.debug("ExcelTool analyticalExcel excelTemplate is Null");
                throw new LepException(ExceptionCodeConstant.EXCEL_EXECELTEMPLATE_NULL_EXCEPTION);
            }
            List<ExcelRow> excelRows=excelTemplate.getRows();
            List<ExcelCell> excelCells=excelRows.get(excelRows.size()-1).getCells();
            if(excelCells==null){
                log.debug("ExcelTool analyticalExcel excelCells is Null");
                throw new LepException(ExceptionCodeConstant.EXCEL_EXCELCELLS_NULL_EXCEPTION);
            }

            Workbook workbook=null;
            if(InputStream.class.isAssignableFrom(t.getClass())) {
                workbook=createWorkBookByInputStream((InputStream) t, fileName);
            }else if(File.class.isAssignableFrom(t.getClass())){
                workbook=createWorkBookByFile((File)t, fileName);
            }
            if(workbook==null){
                log.debug("ExcelTool analyticalExcel workbook is null");
                throw new LepException(ExceptionCodeConstant.EXCEL_WORKBOOK_NULL_EXCEPTION);
            }
            Sheet sheet=workbook.getSheetAt(TemplateConst.SHEETNUM);
            if(sheet==null){
                log.debug("ExcelTool analyticalExcel sheet is null");
                throw new LepException(ExceptionCodeConstant.EXCEL_SHEET_NULL_EXCEPTION);
            }
            int lastRowNum=sheet.getLastRowNum();
            checkExcelTemplate(sheet,excelTemplate,lastRowNum);
            //遍历Excel文件 从第一行开始，第0行为标题行
            for(int i=1;i<=lastRowNum;i++){
                Map<String,String> recordMap=new HashMap<>();
                //表示Excel中的行号
                recordMap.put(TemplateConst.RECNUM, String.valueOf(i + 1));
                Row row=sheet.getRow(i);
                int nullNum=0;
                for(int j=0;j<excelCells.size();j++){
                    Cell cell=row.getCell(j);
                    if(cell==null){
                        nullNum++;
                        continue;
                    }
                    cell.setCellType(CellType.STRING);
                    String temp=cell.getStringCellValue();
                    if(StringUtils.isBlank(temp)){
                        nullNum++;
                        continue;
                    }
                    recordMap.put(excelCells.get(j).getName(),temp.trim());
                }
                if(nullNum<excelCells.size()){
                    excelList.add(recordMap);
                }
            }
        }catch(LepException e){
            throw e;
        }
        catch(Exception e){
            log.debug("ExcelTool analyticalExcel exception",e);
            throw new LepException(ExceptionCodeConstant.EXCEL_FILE_ANALYSIS_EXCEPTION);
        }
        return excelList;
    }

    /**
     * 校验ExcelTemplate
     * @param excelTemplate
     * @param count
     */
    public static void checkExcelTemplate(Sheet sheet,ExcelTemplate excelTemplate,Integer count){
        /*if(excelTemplate.getCount()!=null
                &&excelTemplate.getCount()>=0){
            if(count>excelTemplate.getCount()){
                log.debug("ExcelTool checkExcelTemplate count over constraint");
                throw new LepException(ExceptionCodeConstant.EXCEL_COUNT_OVER_EXCEPTION);
            }
        }*/
        try{
            List<ExcelRow> excelRows=excelTemplate.getRows();
            for(ExcelRow excelRow:excelRows){
                Row row =sheet.getRow(excelRow.getRowNum());
                List<ExcelCell> excelCells=excelRow.getCells();
                for(int i=0;i<excelCells.size();i++){
                    ExcelCell excelCell=excelCells.get(i);
                    String value=excelCell.getValue();
                    try{
                        value= I18nUtil.getTextValue(value);
                    }catch (Exception e){
                        log.debug("coverCell I18nUtil exception",e);
                        value=excelCell.getValue();
                    }
                    if(!value.equals(row.getCell(i).getStringCellValue())){
                        throw new LepException(ExceptionCodeConstant.EXCEL_EXECELTEMPLATE_WRONG_FORMAT_EXCEPTION);
                    }
                }
            }
        }catch(Exception e){
            throw new LepException(ExceptionCodeConstant.EXCEL_EXECELTEMPLATE_WRONG_FORMAT_EXCEPTION);
        }
    }

    /**
     * 导出Excel文件
     * @param template
     * @param response
     * @param tList
     * @param <T>
     * @throws Exception
     */
    public static <T> void exportExcel(String template, HttpServletResponse response, List<T> tList){
        exportExcel(null,template,response,tList);
    }

    public static <T> void exportExcel(String template,
                                       HttpServletResponse response,
                                       List<T> tList,
                                       ExcelMethodCallBack excelMethodCallBack,
                                       ExcelTypeEnum excelTypeEnum,
                                       ParamVo paramVo) {
        exportExcel(null,template,response,tList,excelMethodCallBack,excelTypeEnum,paramVo);
    }

    public static <T> void exportExcel(String template,
                                       HttpServletResponse response,
                                       List<T> tList,
                                       ExcelTypeEnum excelTypeEnum,
                                       String commonValue) {
        ParamVo paramVo=new ParamVo();
        paramVo.setCommonValue(commonValue);
        exportExcel(null,template,response,tList,null,excelTypeEnum,paramVo);
    }
    public static <T> void exportExcel(String template,
                                       HttpServletResponse response,
                                       List<T> tList,
                                       ExcelMethodCallBack excelMethodCallBack,
                                       ExcelTypeEnum excelTypeEnum,
                                       String scheme) {
        ParamVo paramVo=new ParamVo();
        paramVo.setScheme(scheme);
        exportExcel(null, template, response, tList, excelMethodCallBack, excelTypeEnum, paramVo);
    }


    /**
     * 导出Excel文件，并命名文件
     * @param fileNameCus
     * @param template
     * @param response
     * @param tList
     * @param <T>
     */
    public static <T> void exportExcel(String fileNameCus,String template,HttpServletResponse response,List<T> tList)  {
        exportExcel(fileNameCus,template,response,tList,null,ExcelTypeEnum.NOMERGE,null);
    }

    private static void checkExportExcel(String template){
        if(StringUtils.isBlank(template)){
            throw new LepException(ExceptionCodeConstant.EXCEL_EXECELTEMPLATE_NULL_EXCEPTION);
        }
        ExcelTemplate excelTemplate= ExcelXmlTemplateFactory.getTemplate(template);
        if(excelTemplate==null){
            throw new LepException(ExceptionCodeConstant.EXCEL_EXECELTEMPLATE_NULL_EXCEPTION);
        }
        List<ExcelRow> excelRows=excelTemplate.getRows();
        if(excelRows==null||excelRows.size()<=0){
            throw new LepException(ExceptionCodeConstant.EXCEL_EXCELROES_NULL_EXCEPTION);
        }
        for(ExcelRow excelRow:excelRows){
            List<ExcelCell> excelCells=excelRow.getCells();
            if(excelCells==null){
                throw new LepException(ExceptionCodeConstant.EXCEL_EXCELCELLS_NULL_EXCEPTION);
            }
        }
    }
    private static Sheet createSheet(ExcelSupport excelSupport,Workbook workbook){
        ExcelTemplate excelTemplate=excelSupport.getExcelTemplate();
        if(excelSupport.getSheetNum()>0){
            Sheet preSheet=workbook.getSheetAt(excelSupport.getSheetNum() - 1);
            doMergedRegion(preSheet,excelSupport.getExcelTypeEnum(),excelSupport.getPreRowNum(),excelSupport.getRowNum());
        }
        //创建样式风格
        CellStyle cellStyle=coverCellStyle(workbook, excelTemplate);
        excelSupport.setSubtrahend(excelSupport.getSheetNum() * TemplateConst.maxSheetCount);
        excelSupport.setMaxSize(excelSupport.getMaxSize() + TemplateConst.maxSheetCount);
        excelSupport.setSheetNum(excelSupport.getSheetNum() + 1);
        Sheet sheet=workbook.createSheet(TemplateConst.sheetName + excelSupport.getSheetNum());
        List<ExcelRow> excelRows=excelTemplate.getRows();
        HashMap<Integer,Integer> startMap=new HashMap<>();
        HashMap<Integer,Integer> endMap=new HashMap<>();
        for(ExcelRow excelRow:excelRows){
            List<ExcelCell> excelCells=excelRow.getCells();
            Row row=sheet.createRow(excelRow.getRowNum());
            row.setHeight(excelRow.getHeight());
            int i=0;
            int columnNo=0;
            int startColumn=0;
            int endColumn=0;
            for(;i<excelCells.size();i++){
                for(int m=0;m<excelCells.get(i).getSize();m++){
                    if(m==0){
                        startColumn=columnNo;
                    }
                    if(m==excelCells.get(i).getSize()-1){
                        endColumn=columnNo;
                    }
                    if(excelCells.get(i).isStartRow()){
                        startMap.put(columnNo,excelRow.getRowNum());
                    }
                    if(excelCells.get(i).isEndRow()){
                        endMap.put(columnNo,excelRow.getRowNum());
                    }
                    Cell cell=row.createCell(columnNo);
                    excelSupport.getName2ColumnMap().put(excelCells.get(i).getName(),columnNo);
                    columnNo++;

                    sheet.setColumnWidth(i,excelCells.get(i).getWidth());
                    coverCell(cell,excelCells.get(i),cellStyle);
                    if(excelCells.get(i).isGroup()){
                        excelSupport.setPreGroupStr("");
                        excelSupport.setPreRowNum(excelRows.size());
                    }
                }
                if(startColumn<endColumn){
                    CellRangeAddress cra =new CellRangeAddress(excelRow.getRowNum(), excelRow.getRowNum(), startColumn, endColumn); // 起始行, 终止行, 起始列, 终止列
                    sheet.addMergedRegion(cra);
                }
            }
            while(columnNo<excelTemplate.getColumnNum()){
                row.createCell(columnNo);
                columnNo++;
            }
        }
        Set<Integer> keySet=startMap.keySet();
        for(Integer key:keySet){
            Integer startRow=startMap.get(key);
            Integer endRow=endMap.get(key);
            CellRangeAddress cra =new CellRangeAddress(startRow, endRow, key, key); // 起始行, 终止行, 起始列, 终止列
            sheet.addMergedRegion(cra);
        }
        return sheet;
    }

    private static<T,C> Object coverValue(ExcelCell excelCell,T t,ParamVo paramVo){
        String methodGet=excelCell.getMethod();
        if(StringUtils.isBlank(methodGet)){
            String name=excelCell.getName();
            methodGet=cover2Get(name);
        }
        Method method=null;
        Class<?> clazz;
        Object object;
        if(excelCell.isCommon()){
            return paramVo.getCommonValue();
        }else{
            clazz=t.getClass();
            object=t;
        }
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodGet) ;
                break;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        Object o= null;
        try {
            if(method!=null){
                o = method.invoke(object);
            }
        } catch (IllegalAccessException e) {
            log.debug("ExcelTool exportExcel IllegalAccessException", e);
        }catch (InvocationTargetException e) {
            log.debug("ExcelTool exportExcel InvocationTargetException", e);
        }
        return o;
    }

    private static Cell createCell(Row row,int cellNum,Object objValue,ExcelCell excelCell){
        Cell cell=row.createCell(cellNum);
        if(objValue!=null){
            String value= String.valueOf(objValue);
            if(StringUtils.isNotBlank(excelCell.getValueType())){
                if(excelCell.getValueType().equals("Date")){
                    Date date=(Date)objValue;
                    value= DateUtils.parseDate2String(date,DateUtils.DATE_FULL_STR);
                }
            }
            if(StringUtils.isNotBlank(value)){
                cell.setCellValue(value);
            }
        }else{
            if(StringUtils.isNotBlank(excelCell.getDef())){
                cell.setCellValue(excelCell.getDef());
            }
        }
        return cell;
    }

    private static void doMergedRegion(Sheet sheet,ExcelTypeEnum excelTypeEnum,int startRow,int endRow){
        if(endRow>startRow){
            switch (excelTypeEnum){
                case FLUXMERGE:
                    for(int s=0;s<excelTypeEnum.getColumn();s++){
                        CellRangeAddress cra =new CellRangeAddress(startRow, endRow, s, s); // 起始行, 终止行, 起始列, 终止列
                        sheet.addMergedRegion(cra);
                    }
                    break;
                case ONOFFLINESTATUS:
                    for(int s=0;s<excelTypeEnum.getColumn();s++){
                        CellRangeAddress cra =new CellRangeAddress(startRow, endRow, s, s); // 起始行, 终止行, 起始列, 终止列
                        sheet.addMergedRegion(cra);
                    }
                    break;
                case NOMERGE:
                    break;
            }
        }
    }

    private static void mergedRegion(ExcelSupport excelSupport,Cell cell,Sheet sheet){
        ExcelTypeEnum excelTypeEnum=excelSupport.getExcelTypeEnum();
        int rowNum=excelSupport.getRowNum();
        int preRowNum=excelSupport.getPreRowNum();
        String preGroupStr=excelSupport.getPreGroupStr();
        if(StringUtils.isNotBlank(cell.getStringCellValue())){
            String groupStr=cell.getStringCellValue();
            if(!groupStr.equals(preGroupStr)){
                doMergedRegion(sheet,excelTypeEnum,preRowNum,rowNum-1);
                excelSupport.setPreRowNum(rowNum);
                excelSupport.setPreGroupStr(groupStr);
            }else{
                if(excelSupport.getRecordNum()+1>=excelSupport.getAllNum()){
                    doMergedRegion(sheet,excelTypeEnum,preRowNum,rowNum);
                }
            }
        }
    }
    private static<T> Row createRow(ExcelSupport excelSupport,Sheet sheet,T t,ExcelMethodCallBack excelMethodCallBack,ParamVo paramVo){
        ExcelTemplate excelTemplate=excelSupport.getExcelTemplate();
        int rowNum=excelSupport.getRowNum();
        List<ExcelRow> excelRows=excelTemplate.getRows();
        List<ExcelCell> excelCells=excelRows.get(excelRows.size()-1).getCells();
        Row row=sheet.createRow(rowNum);
        for(int m=0;m<excelCells.size();m++){
            ExcelCell excelCell=excelCells.get(m);
            Object objValue=coverValue(excelCell, t, paramVo);
            if(excelCell.isGroupPath()){
                if(objValue!=null&&excelMethodCallBack!=null){
                    objValue=excelMethodCallBack.findGroupPath((Long)objValue,paramVo.getScheme());
                }
            }
            Cell cell=createCell(row, m, objValue, excelCell);
            if(excelCell.isGroup()){
                mergedRegion(excelSupport,cell,sheet);
            }
        }
        return row;
    }
    private static<T> Workbook coverWorkbook(String template,List<T> tList,ExcelMethodCallBack excelMethodCallBack,ExcelTypeEnum excelTypeEnum,ParamVo paramVo){
        ExcelTemplate excelTemplate= ExcelXmlTemplateFactory.getTemplate(template);
        //创建workbook
        Workbook workbook=createWorkbook(excelTemplate.getType());

        ExcelSupport excelSupport=new ExcelSupport();
        excelSupport.setExcelTemplate(excelTemplate);
        excelSupport.setExcelTypeEnum(excelTypeEnum);
        excelSupport.setAllNum(tList.size());
        Sheet sheet=createSheet(excelSupport, workbook);
        for(int j=0;j<tList.size();j++){
            if(j>=excelSupport.getMaxSize()){
                log.info("create new sheet:"+j);
                sheet=createSheet(excelSupport,workbook);
            }
            int rowNum=j+excelTemplate.getRows().size()-excelSupport.getSubtrahend();
            excelSupport.setRowNum(rowNum);
            excelSupport.setRecordNum(j);
            T t=tList.get(j);
            createRow(excelSupport, sheet, t, excelMethodCallBack,paramVo);
            if(excelTemplate.isUpdateStatus()&&excelMethodCallBack!=null&&paramVo!=null&&paramVo.getRecordId()!=null){
                Integer status=(j+1)*100/tList.size();
                if(status>excelSupport.getStatus()||status==100){
                    excelSupport.setStatus(status);
                    excelMethodCallBack.updateExportStatus(status,paramVo.getRecordId(),paramVo.getScheme());
                }
            }
        }
        return workbook;
    }
    public static<T> void coverWorkbookByPage(ExcelSupportPage<T> excelSupportPage,ExcelMethodCallBack excelMethodCallBack,ParamVo paramVo){
        Workbook workbook=excelSupportPage.getWorkbook();
        ExcelSupport excelSupport=excelSupportPage.getExcelSupport();
        excelSupport.setAllNum(excelSupportPage.getList().getTotalCount());
        ExcelTemplate excelTemplate=excelSupport.getExcelTemplate();
        Sheet sheet;
        if(workbook==null){
            excelTemplate= ExcelXmlTemplateFactory.getTemplate(excelSupportPage.getTemplate());
            excelSupport.setExcelTemplate(excelTemplate);
            //创建workbook
            workbook=createWorkbook(excelTemplate.getType());
            excelSupportPage.setWorkbook(workbook);
            sheet=createSheet(excelSupport,workbook);
        }else{
            sheet=workbook.getSheetAt(excelSupport.getSheetNum()-1);
        }
        int prePageNum=excelSupportPage.getPrePageNum();

        for(int j=0;j<excelSupportPage.getList().size();j++){
            //log.info("deal No:{} record",prePageNum+j);
            if(prePageNum+j>=excelSupport.getMaxSize()){
                log.info("create new sheet:"+j);
                sheet=createSheet(excelSupport,workbook);
            }
            int rowNum=prePageNum+j+excelTemplate.getRows().size()-excelSupport.getSubtrahend();
            excelSupport.setRowNum(rowNum);
            excelSupport.setRecordNum(prePageNum+j);
            T t=excelSupportPage.getList().get(j);
            createRow(excelSupport, sheet, t, excelMethodCallBack,paramVo);
            if(excelTemplate.isUpdateStatus()&&excelMethodCallBack!=null&&paramVo!=null&&paramVo.getRecordId()!=null){
                Integer status=(prePageNum+j+1)*100/excelSupportPage.getList().getTotalCount();
                if(status>excelSupport.getStatus()||status==100){
                    excelSupport.setStatus(status);
                    excelMethodCallBack.updateExportStatus(status,paramVo.getRecordId(),paramVo.getScheme());
                }
            }
        }
        excelSupportPage.setPrePageNum(prePageNum+excelSupportPage.getList().size());
    }

    private static void doExport(Workbook workbook,String fileNameCus,String template,HttpServletResponse response){
        ExcelTemplate excelTemplate= ExcelXmlTemplateFactory.getTemplate(template);
        OutputStream os=null;
        try {
            String fileName = new String((template + "-" + DateUtils.getNowTime() + "."+excelTemplate.getType()).getBytes("gb2312"), "ISO8859-1");
            if(StringUtils.isNotBlank(fileNameCus)){
                fileName=new String((fileNameCus + "."+excelTemplate.getType()).getBytes("gb2312"), "ISO8859-1");
            }
            response.setCharacterEncoding(CommonConstant.CHARSET_UTF_8);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName);
            os=response.getOutputStream();
            workbook.write(os);
        } catch (IOException e) {
            log.debug("ExcelTool exportExcel IO Exception",e);
            throw new LepException(ExceptionCodeConstant.EXCEL_WORKBOOK_IO_EXCEPTION);
        }finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    log.debug("ExcelTool exportExcel os close exception",e);
                }
            }
        }
    }

    public static <T> void exportExcel(String fileNameCus,
                                       String template,
                                       HttpServletResponse response,
                                       List<T> tList,
                                       ExcelMethodCallBack excelMethodCallBack,
                                       ExcelTypeEnum excelTypeEnum,
                                       ParamVo paramVo){
        checkExportExcel(template);
        //如果导出数据为空，直接下载模板
        if(CollectionUtils.isEmpty(tList)){
            downloadTemplate(template, response);
            return;
        }
        Workbook workbook=coverWorkbook(template,tList,excelMethodCallBack,excelTypeEnum,paramVo);
        doExport(workbook,fileNameCus,template,response);
    }


    /**
     * 转化为get方法
     * @param name
     * @return
     */
    public static String cover2Get(String name){
        char[] cs=name.toCharArray();
        cs[0]-= TemplateConst.DEVIATION;
        return TemplateConst.METHODGET+ String.valueOf(cs);
    }

    /**
     * 下载模板
     * @param templateName
     * @param response
     * @throws IOException
     */
    public static void downloadTemplate(String templateName, HttpServletResponse response) {
        downloadTemplate(null,templateName,response);
    }

    /**
     * 下载模板，并自定义文件名
     * @param fileNameCus
     * @param templateName 模板名称
     * @throws IOException
     */
    public static void downloadTemplate(String fileNameCus,String templateName, HttpServletResponse response){
        OutputStream os = null;
        BufferedInputStream inputStream = null;
        try {
            ExcelTemplate excelTemplate = ExcelXmlTemplateFactory.getTemplate(templateName);
            //Resource resource = new ClassPathResource(excelTemplate.getPath());
            String absolutePath=getAbsolutePath(excelTemplate.getPath());
            log.info("download absolutePath"+absolutePath);
            Resource resource= new FileSystemResource(absolutePath);
            File file = resource.getFile();
            inputStream = new BufferedInputStream(new FileInputStream(file));
            String fileName = new String((templateName + "-"+DateUtils.getNowTime()+"."+excelTemplate.getType()).getBytes(CommonConstant.CHARSET_GB2312), CommonConstant.CHARSET_ISO_8859_1);//为了解决中文名称乱码问题
            if(StringUtils.isNotBlank(fileNameCus)){
                fileName=new String((fileNameCus + "."+excelTemplate.getType()).getBytes(CommonConstant.CHARSET_GB2312), CommonConstant.CHARSET_ISO_8859_1);
            }
            response.setCharacterEncoding(CommonConstant.CHARSET_UTF_8);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            os = response.getOutputStream();
            byte[] b = new byte[2048];
            int byteRead;
            while(-1!=(byteRead=inputStream.read(b))){
                os.write(b,0,byteRead);
            }
        } catch (IOException e){
            log.error("downloadTemplate error:",e);
            throw new LepException(CommonExceptionCode.UNKNOWN_EXCEPTION);
        } finally{
            try {
                if(os!=null){
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                log.debug("ExcelTool downloadTemplate os close exception",e);
            }
            try {
                if(inputStream!=null){
                    inputStream.close();
                }
            } catch (IOException e) {
                log.debug("ExcelTool downloadTemplate inputStream close exception",e);
            }
        }
    }

    /**
     * 下载重新创建的模板，限制可填写的内容
     * @param templateName
     * @param checkListMap
     * @param response
     * @throws Exception
     */
    public static void downloadTemplateReCreate(String templateName,Map<String,List<String>> checkListMap,HttpServletResponse response) throws Exception {
        downloadTemplateReCreate(null,templateName,checkListMap,response);
    }

    /**
     *
     * @param fileNameCus
     * @param templateName
     * @param checkListMap
     * @param response
     * @throws Exception
     */
    public static void downloadTemplateReCreate(String fileNameCus,String templateName,Map<String,List<String>> checkListMap,HttpServletResponse response) throws Exception{
        createExcel(templateName,checkListMap);
        downloadTemplate(fileNameCus,templateName,response);
    }
}
