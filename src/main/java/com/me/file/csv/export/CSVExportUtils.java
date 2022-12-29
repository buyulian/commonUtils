package com.me.file.csv.export;


import com.alibaba.fastjson.JSON;
import com.me.list.PageListUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class CSVExportUtils {

    private static final Logger log = LoggerFactory.getLogger(CSVExportUtils.class);


    /* 通用属性*/
    private static final String CHARSET = "GBK";

    public static final String GB_2312 = "GB2312";

    private static final int BATCH_SIZE = 2000;


//    public static void flushCsvToResponse(HttpServletResponse response,
//                                          String fileName,
//                                          List<DataCell> tableDefinitionList,
//                                          List dataList) throws IOException {
//
//        setHeader(response, fileName);
//
//        ServletOutputStream outputStream = response.getOutputStream();
//        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, GB_2312);
//
//        flushToOutputSream(outputStreamWriter, tableDefinitionList, dataList);
//
//    }

    public static void flushCsvToFile(File file, List<DataCell> tableDefinitionList, List dataList) throws IOException {

        if (!file.exists()) {
            throw new IllegalArgumentException("文件不存在");
        }

        OutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, GB_2312);

        flushToOutputSream(outputStreamWriter, tableDefinitionList, dataList);

    }


//    private static void setHeader(HttpServletResponse response, String fileName) {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/csv;charset="+GB_2312);
//        response.setHeader("Content-Transfer-Encoding", "binary");
//        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
//        response.setHeader("Pragma", "public");
//        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + ".csv\"");
//    }

    private static void flushToOutputSream(OutputStreamWriter outputStreamWriter,List<DataCell> tableDefinitionList, List dataList) throws IOException {
        if (CollectionUtils.isEmpty(tableDefinitionList)) {
            throw new IllegalArgumentException("表定义为空");
        }

        if (CollectionUtils.isEmpty(dataList)) {
            throw new IllegalArgumentException("数据为空");
        }

        BufferedWriter csvFileOutputStream = null;
        // GB2312使正确读取分隔符","
        try {
            csvFileOutputStream = new BufferedWriter(outputStreamWriter, 1024);
            //显示标题
            int size = tableDefinitionList.size();
            StringBuilder sb = new StringBuilder(size * 10);
            for (DataCell dataCell : tableDefinitionList) {
                sb.append(dataCell.getTitle()).append(",");
            }
            sb.append("\n");

            List<List> dataListList = PageListUtils.splitListByPage(dataList, BATCH_SIZE);

            csvFileOutputStream.write(sb.toString());

            Set<String> groupMainValueSet= new HashSet<String>(dataList.size());
            for (List itemList : dataListList) {
                StringBuilder itemStringBuilder = new StringBuilder(itemList.size()*dataListList.size()*10);

                for (Object item : itemList) {

                    List<String> haveGroupMainValueList = new LinkedList<>();

                    for (DataCell dataCell : tableDefinitionList) {
                        String value = "";
                        if (dataCell.isNeedSpace()) {
                            String groupMainValue = dataCell.getGroupMainValue(item);
                            if (!groupMainValueSet.contains(groupMainValue)) {
                                value = getValue(item, dataCell);
                                haveGroupMainValueList.add(groupMainValue);
                            }
                        } else {
                            value = getValue(item, dataCell);
                        }

                        itemStringBuilder.append(value).append(",");
                    }
                    itemStringBuilder.append("\n");
                    groupMainValueSet.addAll(haveGroupMainValueList);
                }

                csvFileOutputStream.write(itemStringBuilder.toString());
                csvFileOutputStream.flush();
            }


        } catch (Exception e) {
            throw new RuntimeException("导出异常", e);
        } finally {
            if (csvFileOutputStream != null) {
                csvFileOutputStream.close();
            }
        }
    }

    private static String getValue(Object item, DataCell dataCell){
        try {
            return dataCell.getValue(item);
        } catch (Exception e) {
            log.warn("dataCell.getValue Exception item {} dataCell {}", JSON.toJSONString(item), dataCell.getFiledName(), e);
            return "";
        }
    }

}