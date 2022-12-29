package com.me.office.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class XlsxUtil {
    public static void read(String fileName){
        File file = new File(fileName);

        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            System.out.println("文件读取异常");
        } catch (InvalidFormatException e) {
            System.out.println("无效的格式");
        }
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

        int rowStart = xssfSheet.getFirstRowNum();
        int rowEnd = xssfSheet.getLastRowNum();
        for(int i=rowStart;i<=rowEnd;i++)
        {
            XSSFRow row = xssfSheet.getRow(i);
            if(null == row) continue;
            int cellStart = row.getFirstCellNum();
            int cellEnd = row.getLastCellNum();

            for(int k=cellStart;k<=cellEnd;k++) {
                XSSFCell cell = row.getCell(k);
                if (null == cell) continue;

                System.out.print(cell.getRawValue() + " ");
            }
            System.out.print("\n");
        }
    }

    public static void write(String fileName){
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Sheet 1");
            final int NUM_OF_ROWS = 3;
            final int NUM_OF_COLUMNS = 10;

            // Create a row and put some cells in it. Rows are 0 based.
            Row row;
            Cell cell;
            for (int rowIndex = 0; rowIndex < NUM_OF_ROWS; rowIndex++) {
                row = sheet.createRow((short) rowIndex);
                for (int colIndex = 0; colIndex < NUM_OF_COLUMNS; colIndex++) {
                    cell = row.createCell((short) colIndex);
                    cell.setCellValue(colIndex * (rowIndex + 1));
                }
            }

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 15);

            Chart chart = drawing.createChart(anchor);
            ChartLegend legend = chart.getOrCreateLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);

            ScatterChartData data = chart.getChartDataFactory().createScatterChartData();

            ValueAxis bottomAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.BOTTOM);
            ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

            ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, 0, 0, NUM_OF_COLUMNS - 1));
            ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, 1, 0, NUM_OF_COLUMNS - 1));
            ChartDataSource<Number> ys2 = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(2, 2, 0, NUM_OF_COLUMNS - 1));


            data.addSerie(xs, ys1);
            data.addSerie(xs, ys2);

            chart.plot(data, bottomAxis, leftAxis);

            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                wb.write(fileOut);
            } catch (IOException e) {
                System.out.println("保存文件失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
