package com.me.code;

import com.me.file.FileIo;
import com.me.string.NameUtils;

import java.io.File;
import java.util.List;

/**
 * @author liujiacun
 * @date 2019/3/6
 */
public class CodeUtil {

    public static void addFieldForFile(File file, String[] baseField, String[][] afterFields, String[] addField,int matchNum){
        String content = FileIo.readFile(file);
        content=addField(content,baseField,afterFields,addField,matchNum);
        FileIo.writeFile(file,content);
    }


    public static String addField(String content,String[] baseField,String[][] afterFields,String[] addField,int matchNum){
        StringBuilder sb=new StringBuilder();
        int bi=0;
        int bj=0;
        int ei=0;
        int status=0;
        for(int i=0;i<content.length();i++){
            char c=content.charAt(i);
            if(status==0){
                boolean isMatchBaseField = false;
                for(int j=0;j<matchNum;j++){
                    isMatchBaseField = content.startsWith(baseField[j], i);
                    if(isMatchBaseField){
                        bi=i;
                        i+=baseField[j].length()-1;
                        sb.append(baseField[j]);
                        status=1;
                        bj=j;
                        break;
                    }
                }
                if(!isMatchBaseField){
                    sb.append(c);
                }
            }else {
                boolean isMatchBaseField;
                for (String[] afterField : afterFields) {
                    isMatchBaseField = content.startsWith(afterField[bj], i);

                    if(isMatchBaseField){
                        ei=i;
                        String middleString = content.substring(bi+baseField[bj].length(), ei);


                        String replaceMiddlerStringFirst = replaceArray(middleString,afterField,addField);
                        sb.append(replaceMiddlerStringFirst);
                        sb.append(addField[bj]);

                        String replaceMiddlerStringSecond = replaceArray(middleString,baseField,addField);
                        sb.append(replaceMiddlerStringSecond);
                        i+=afterField[bj].length()-1;
                        sb.append(afterField[bj]);

                        status=0;
                        break;
                    }
                }

            }
        }
        return sb.toString();
    }

    private static String replaceArray(String str,String[] sources,String[] targets){
        for (int i = 0; i < sources.length; i++) {
            str=str.replace(sources[i],targets[i]);
        }
        return str;
    }
}
