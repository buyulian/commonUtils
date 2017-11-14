package file;

import java.io.*;
import java.util.Properties;

/**
 * Created by ljc on 2017/8/18.
 */
public class FileIo {
    public static String  readFile(String fileName){
        BufferedReader bufferedReader=null;
        StringBuilder sb=new StringBuilder();
        try {
            FileReader fileReader=new FileReader(fileName);
            bufferedReader=new BufferedReader(fileReader);
            String str;
            while ((str=bufferedReader.readLine())!=null){
                sb.append(str).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }
    public static Properties readProperties(String fileName){
        Properties prop = new Properties();
        try{
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream (new FileInputStream(fileName));
            prop.load(in);     ///加载属性列表
            in.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return prop;
    }
    public static void writeFile(String fileName,String content){
        createParentDir(fileName);
        BufferedWriter out=null;
        try {
            out=new BufferedWriter(new FileWriter(fileName));
            out.write(content);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createParentDir(String fileName){
        File file=new File(fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
    }

    public static boolean deleteFile(File file){
        if(file.isDirectory()){
            File[] files=file.listFiles();
            for(File f:files){
                deleteFile(f);
            }
        }
        return file.delete();
    }
}
