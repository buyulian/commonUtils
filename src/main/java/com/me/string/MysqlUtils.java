package com.me.string;

import com.me.file.FileIo;

import java.io.File;
import java.util.Optional;

public class MysqlUtils {

    public static String arrayToInsert(Object[][] data,String tableName,String outFile){
        StringBuilder sb=new StringBuilder();
        Object[] titles=data[0];
        for (int i = 1; i < data.length; i++) {
            Object[] a = data[i];

            sb.append("INSERT INTO `").append(tableName).append("` (");
            for (int j = 0; j < titles.length; j++) {
                sb.append("`").append(titles[j]).append("`");
                if(j!=titles.length-1){
                    sb.append(",");
                }
            }

            sb.append(" )\n VALUES (");
            for(int k=0;k<titles.length;k++){
                String s= Optional.ofNullable(a[k]).orElse("").toString();
                sb.append("'").append(s).append("'");
                if(k!=titles.length-1){
                    sb.append(",");
                }
            }
            sb.append("'2018-08-01 00:00:00');\n\n");
        }

        String sql=sb.toString();
        FileIo.writeFile(new File(outFile),sql);
        return sql;
    }
}
