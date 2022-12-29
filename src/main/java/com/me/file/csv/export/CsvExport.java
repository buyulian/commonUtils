package com.me.file.csv.export;

import java.io.File;
import java.util.List;

/**
 * @author buyulian
 * @date 2019/11/29
 */
public class CsvExport {

    public static File createCsvFile(File file, List<DataCell> tableDefinitionList, List dataList) {

        try {
            CSVExportUtils.flushCsvToFile(file,tableDefinitionList,dataList);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("创建 csv 文件错误",e);
        }

        return file;
    }

}
