package file.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class ExcelUtil {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * 判断Excel的版本,获取Workbook
     * @param in
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(InputStream in, File file) throws IOException{
        Workbook wb = null;
        if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * 判断文件是否是excel
     * @throws Exception
     */
    public static void checkExcelVaild(File file) throws Exception{
        if(!file.exists()){
            throw new Exception("文件不存在");
        }
        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){
            throw new Exception("文件不是Excel");
        }
    }

    private static Object getValue(Cell cell) {
        Object obj = null;
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case NUMERIC:
                double d = cell.getNumericCellValue();
                obj=String.valueOf((long) d);
                break;
            case STRING:
                obj = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return obj;
    }

    public static Object[][] getExcelSheetDate(String file,int sheetIndex){
        Object[][] rs=null;
        try {
            // 同时支持Excel 2003、2007
            File excelFile = new File(file); // 创建文件对象
            FileInputStream in = new FileInputStream(excelFile); // 文件流
            checkExcelVaild(excelFile);
            Workbook workbook = getWorkbok(in,excelFile);
            //Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel2003/2007/2010都是可以处理的

            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量

            if(sheetIndex>sheetCount){
                return rs;
            }
            Sheet sheet=workbook.getSheetAt(sheetIndex);
            rs=getSheet(sheet);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static Object[][] getSheet(Sheet sheet){
        int cowCount=sheet.getLastRowNum()+1;
        List<List> listList=new ArrayList<>(cowCount);
        for (int i=0;i<cowCount;i++) {
            Row row=sheet.getRow(i);
            if(row.getCell(0)==null){
                break;
            }
            try {
                //如果当前行没有数据，跳出循环
                if(row.getCell(0).toString().equals("")){
                    break;
                }

                //获取总列数(空格的不计算)
                int columnTotalNum = row.getPhysicalNumberOfCells();

                List list=new ArrayList(columnTotalNum);

                for(int k=0;k<columnTotalNum;k++){
                    Cell cell=row.getCell(k);
                    list.add(getValue(cell));

                }
                listList.add(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int colNum=listList.get(0).size();
        Object[][] rs=new Object[listList.size()][];
        for(int i=0;i<listList.size();i++){
            List list=listList.get(i);
            rs[i]=new Object[colNum];
            for(int j=0;j<list.size();j++){
                rs[i][j]=list.get(j);
            }
        }

        return rs;
    }


    public static Object[][][] getExcelData(String file){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Object[][][] rs=null;
        try {
            // 同时支持Excel 2003、2007
            File excelFile = new File(file); // 创建文件对象
            FileInputStream in = new FileInputStream(excelFile); // 文件流
            checkExcelVaild(excelFile);
            Workbook workbook = getWorkbok(in,excelFile);
            //Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel2003/2007/2010都是可以处理的

            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量

            rs=new Object[sheetCount][][];
            for(int i=0;i<sheetCount;i++){
                Sheet sheet=workbook.getSheetAt(i);
                rs[i]=getSheet(sheet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

}