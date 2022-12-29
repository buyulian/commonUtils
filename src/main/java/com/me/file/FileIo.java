package com.me.file;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * Created by ljc on 2017/8/18.
 */
public class FileIo {
    public static String  readFile(File file){
        return readFileCommon(file, null);
    }

    /**
     *
     * @param file
     * @param charset
     * @see StandardCharsets
     * @return
     */
    public static String  readFileWithCharset(File file, Charset charset){
        return readFileCommon(file, charset);
    }

    /**
     *
     * @param file
     * @param charset
     * @see StandardCharsets
     * @return
     */
    public static String  readFileCommon(File file, Charset charset){
        BufferedReader bufferedReader=null;
        StringBuilder sb=new StringBuilder();
        try {
            bufferedReader = getBufferedReader(file, null);
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


    private static BufferedReader getBufferedReaderDefault(File file) throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        return bufferedReader;
    }

    private static BufferedReader getBufferedReaderCharset(File file, Charset charset) throws FileNotFoundException {
        FileInputStream fileInputStream=new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
        BufferedReader bufferedReader =new BufferedReader(inputStreamReader);
        return bufferedReader;
    }

    private static BufferedReader getBufferedReader(File file, Charset charset) throws FileNotFoundException {
        if (charset == null) {
            return getBufferedReaderDefault(file);
        } else {
            return getBufferedReaderCharset(file, charset);
        }
    }

    public static String  readFileLine(File file, int startLineIndex, int lineNum){
        BufferedReader bufferedReader=null;
        StringBuilder sb=new StringBuilder();
        try {
            bufferedReader = getBufferedReader(file, null);
            String str;
            int lineNumCnt = 1;
            while ((str=bufferedReader.readLine())!=null){
                if (lineNumCnt <= startLineIndex) {
                    continue;
                }
                if (lineNumCnt > startLineIndex + lineNum) {
                    break;
                }
                sb.append(str).append("\n");
                lineNumCnt++;
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
    public static int getTotalNum(File file){
        BufferedReader bufferedReader=null;
        int lineNumCnt = 0;
        try {
            bufferedReader = getBufferedReader(file, null);
            String str;
            while ((str=bufferedReader.readLine())!=null){
                lineNumCnt++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lineNumCnt;
        }
    }
    public static void splitCsv(File file, int pageNum) {
        int totalNum = getTotalNum(file);
        int pageSize = (totalNum - 1)/pageNum;
        String absolutePath = file.getAbsolutePath();
        String name = file.getName();

        BufferedReader bufferedReader=null;
        StringBuilder sb=new StringBuilder();
        try {
            bufferedReader = getBufferedReader(file, null);
            String str;
            int lineNumCnt = 0;
            int page = 0;
            while ((str=bufferedReader.readLine())!=null){
                if ((lineNumCnt - 1) % pageSize == 0) {
                    page++;
                    FileIo.writeFile(new File(getPathname(absolutePath, page)), sb.toString());
                    sb.delete(0, sb.length());
                }
                sb.append(str);
                lineNumCnt++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private static String getPathname(String absolutePath, int page) {
        return absolutePath.split(".")[0] + "page" + page + ".csv";
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

    public static void writeFile(File file, String content, String charset){
        createParentDir(file);
        BufferedWriter out=null;
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, charset);
            out=new BufferedWriter(outputStreamWriter);
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


    public static int writeBytes(InputStream inputStream,OutputStream outputStream,byte[] buffer) throws IOException {
        int sizeAll=0;
        int bytesRead;
        int size=buffer.length;
        while ((bytesRead = inputStream.read(buffer, 0, size)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            sizeAll+=bytesRead;
        }
        return sizeAll;
    }

    public static void mergeFile(File a,File b,byte[] buffer) throws Exception {
        FileOutputStream outputStream=new FileOutputStream(a,true);
        FileInputStream inputStream=new FileInputStream(b);
        try {
            writeBytes(inputStream,outputStream,buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

    public static Map<String,Object> readYaml(File file) throws Exception {
        return new Yaml().load(new FileInputStream(file));
    }

    @SuppressWarnings("unchecked")
    public static Object getObjectFromYamlMap(Map<String,Object> prop,String key){
        String[] split = key.split("\\.");
        Object result=prop;
        Map<String,Object> curProp=null;
        for (String str:split){
            curProp= (Map<String, Object>) result;
            result=curProp.get(str);
        }
        return result;
    }

    public static String getStringFromYamlMap(Map<String,Object> prop,String key){
        return getObjectFromYamlMap(prop,key).toString();
    }

    @SuppressWarnings("unchecked")
    public static Map<String,Object> getMapFromYamlMap(Map<String,Object> prop,String key){
        return (Map<String, Object>) getObjectFromYamlMap(prop,key);
    }

    @SuppressWarnings("unchecked")
    public static Map<String,String> getStringMapFromYamlMap(Map<String,Object> prop,String key){
        return (Map<String, String>) getObjectFromYamlMap(prop,key);
    }

    public static int replaceFolderFileContent(File file,String source,String target){
        int count=0;
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files!=null){
                for (File file1 : files) {
                    count+=replaceFolderFileContent(file1,source,target);
                }
            }
        } else {
            String content = readFile(file);
            if(content.contains(source)){
                content=content.replace(source,target);
                writeFile(file,content);
                count++;
            }
        }

        String name = file.getName();
        if(name.contains(source)){
            name=name.replace(source,target);
            boolean b = file.renameTo(new File(file.getParent()+"/"+name));
            count++;
        }
        return count;
    }
}
