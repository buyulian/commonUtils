package file;

import java.io.*;
import java.util.Properties;

/**
 * Created by ljc on 2017/8/18.
 */
public class FileIo {
    public static String  readFile(File file){
        BufferedReader bufferedReader=null;
        StringBuilder sb=new StringBuilder();
        try {
            FileReader fileReader=new FileReader(file);
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
    public static Properties readProperties(File file){
        Properties prop = new Properties();
        try{
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream (new FileInputStream(file));
            prop.load(in);     ///加载属性列表
            in.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return prop;
    }
    public static void writeFile(File file,String content){
        createParentDir(file);
        BufferedWriter out=null;
        try {
            out=new BufferedWriter(new FileWriter(file));
            out.write(content);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createParentDir(File file){
        File parentPath=file.getParentFile();
        if(parentPath!=null&&!parentPath.exists()){
            parentPath.mkdirs();
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
