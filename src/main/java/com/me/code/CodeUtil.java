package com.me.code;

import com.me.file.FileIo;
import com.me.string.NameUtils;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author buyulian
 * @date 2019/3/6
 */
public class CodeUtil {

    public static void addFieldForFile(File file, String[] baseField, String[][] afterFields, String[][] addFields,int matchNum){
        String content = FileIo.readFile(file);
        content=addField(content,baseField,afterFields,addFields,matchNum);
        FileIo.writeFile(file,content);
    }


    public static String addField(String content,String[] baseField,String[][] afterFields,String[][] addFields,int matchNum){
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
                    isMatchBaseField = isMatchField(content, i, baseField[j]);
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
                    isMatchBaseField = isMatchField(content, i, afterField[bj]);

                    if(isMatchBaseField){
                        ei=i;
                        String middleString = content.substring(bi+baseField[bj].length(), ei);

                        String[] beforeTemp = new String[]{"@%&$sd1@","@%&ssdfd1@"};
                        String[] afterTemp = new String[]{"@%&$S:1@","@%&ss$d1@"};

                        middleString = replaceArray(middleString,baseField,beforeTemp);
                        middleString = replaceArray(middleString,afterField,afterTemp);

                        String[] curAfters = addFields[0];
                        String[] curBefores = baseField;

                        for (int i1 = 0; i1 < addFields.length; i1++) {
                            String[] addField = addFields[i1];
                            String replaceMiddlerStringFirst = replaceArray(middleString,afterTemp,addField);
                            replaceMiddlerStringFirst = replaceArray(replaceMiddlerStringFirst,beforeTemp,curBefores);
                            sb.append(replaceMiddlerStringFirst);
                            sb.append(addField[bj]);

                            if (i1 == addFields.length - 1) {
                                curAfters = afterField;
                            } else {
                                curAfters = addFields[i1+1];
                            }
                            curBefores = addFields[i1];
                        }

                        String replaceMiddlerStringSecond = replaceArray(middleString,curAfters,afterField);
                        replaceMiddlerStringSecond = replaceArray(replaceMiddlerStringSecond,beforeTemp,curBefores);
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

    private static boolean isMatchField(String content, int i, String prefix) {
        boolean startsWith = content.startsWith(prefix, i);

        if (startsWith) {
            char c = content.charAt(i - 1);
            char c1 = content.charAt(i + prefix.length());

            if (beforeAfterMatch(c) && beforeAfterMatch(c1)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    private static boolean beforeAfterMatch(char c) {
        boolean b = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || (c == '_');
        if (b) {
            return false;
        }
        return true;
    }

    private static String replaceArray(String str,String[] sources,String[] targets){
        for (int i = 0; i < sources.length; i++) {
            str=str.replace(sources[i],targets[i]);
        }
        return str;
    }
}
