package com.me;

import com.alibaba.fastjson.JSON;
import com.me.code.CodeUtil;
import com.me.compute.ComputeUtil;
import com.me.file.FileIo;
import com.me.gson.Apple;
import com.me.http.BrowserClient;
import com.me.json.GsonUtil;
import com.me.pdf.PdfUtils;
import org.apache.http.NameValuePair;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: buyulian
 * Date: 2019/1/10
 * Time: 15:44
 * Description: No Description
 */
public class CommonTest {

    @Test
    public void testSimple(){
        String source = "";
        String target = "";
        File file = new File("");
        int n = FileIo.replaceFolderFileContent(file, source, target);
        System.out.println(n);

    }

    @Test
    public void testJsonFormat(){
        Map<String, Object> item = new HashMap<>();
        item.put("id", 12);
        item.put("name", "xm");
        item.put("childrenId", Arrays.asList(16,17,18));
        Map<String, Object> item2 = new HashMap<>();
        item2.put("id", 1);
        item2.put("name", "dm");
        item2.put("childrenId", Arrays.asList(12,13,14));
        item.put("father", item2);
        String jsonString = JSON.toJSONString(item);
        String jsonString1 = JSON.toJSONString(item, true);
        System.out.println(jsonString);
        System.out.println(jsonString1);

    }

    @Test
    public void testSplitCsv(){
        File file = new File("");
        FileIo.splitCsv(file, 10);

    }

    @Test
    public void testGson(){
        String appleJson="{\"name\":{}}";
        Apple apple = GsonUtil.jsonToObject(appleJson, Apple.class);
        System.out.println(apple.getName());
    }

    @Test
    public void testAddField(){
        String filePath="";
        File file=new File(filePath);
        String content = FileIo.readFile(file);
        String contentAfter = CodeUtil.addField(content
                , new String[]{
                        "bill_type",
                        "billType",
                }
                , new String[][]{
                        new String[]{
                                "business_time",
                                "businessTime",
                        },
                }
                , new String[][]{
                        new String[]{
                                "bill_detail_type",
                                "billDetailType",
                        },
        },
                2);
        FileIo.writeFile(file,contentAfter);
        System.out.println(contentAfter);
    }

    @Test
    public void testAddFieldTmplate(){
        String filePath="";
        File file=new File(filePath);
        String content = FileIo.readFile(file);
        String contentAfter = CodeUtil.addField(content
                , new String[]{
                        "standard_exp_no",
                        "standardExpNo",
                }
                , new String[][]{
                        new String[]{
                                "biz_no",
                                "bizNo",
                        },
                }
                , new String[][]{
                        new String[]{
                                "detail_type",
                                "detailType",
                        }
        },
                2);
        FileIo.writeFile(file,contentAfter);
        System.out.println(contentAfter);
    }

    @Test
    public void testCompute(){
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("!(3==2)"),new BigDecimal(1)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("!(3==6/2)"),new BigDecimal(0)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("!1||!!1"),new BigDecimal(1)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("!1||!!!1"),new BigDecimal(0)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("3.5==7/2"),new BigDecimal(1)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("3==2&&2<=3"),new BigDecimal(0)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("3==2&&2<=3"),new BigDecimal(0)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("3==2||2<=3"),new BigDecimal(1)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("3.5==7/2&&2<=3&&2>=1"),new BigDecimal(0)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("1==1"),new BigDecimal(1)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("1!=1"),new BigDecimal(0)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("1<=1"),new BigDecimal(1)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("1<2"),new BigDecimal(1)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("3!=3"),new BigDecimal(0)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("max(min(2,6),4)"),new BigDecimal(4)));

        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("(17-5)"),new BigDecimal(12)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("(3+1)*(7-5)*2-3*5"),new BigDecimal(1)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("4/(7-5)+2+3*5"),new BigDecimal(19)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("2+3*5"),new BigDecimal(17)));
        Assert.assertTrue(bigDicimalEquals(ComputeUtil.compute("12+103"),new BigDecimal(115)));

    }

    private static boolean bigDicimalEquals(BigDecimal a,BigDecimal b){
        BigDecimal scale=new BigDecimal(0.000000001);
        int compareTo = a.subtract(b).compareTo(scale);
        int compareTo2 = a.subtract(b).compareTo(scale.multiply(BigDecimal.valueOf(-1)));
        if(compareTo<0&&compareTo2>0){
            return true;
        }
        return false;
    }

    @Test
    public void testHttp(){
        String url="http://localhost:8080/test";
        List<NameValuePair> nameValuePairs = new LinkedList<>();
        Map<String, String> map = new HashMap<>();

        int n=100;
        Thread[] threads=new Thread[n];
        for (int i = 0; i < n; i++) {
            try {
                final int index=i;
                Thread thread = new Thread(() -> {
                    System.out.println(index);
                    String result = null;
                    try {
                        BrowserClient client=new BrowserClient();
                        result = client.doPost(url, nameValuePairs, map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(index+" "+result);
                });
                threads[index]=thread;
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end");
    }

    @Test
    public void testAgent() {
        BrowserClient client = new BrowserClient();
        String content = null;
        try {
            content = client.doGet("", new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(content);
    }

    @Test
    public void testPdf() {
        try {
            FileOutputStream outputStream = new FileOutputStream("");
            FileInputStream inputStreamParam = new FileInputStream("");
            PdfUtils.splitPdf(inputStreamParam,
                    outputStream,
                    1,30
            );

            FileOutputStream outputStream2 = new FileOutputStream("");
            FileInputStream inputStreamParam2 = new FileInputStream("");
            PdfUtils.splitPdf(inputStreamParam2,
                    outputStream2,
                    31,61
            );
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMergePdf() {
        try {
            FileInputStream inputStreamParam = new FileInputStream("");
            FileInputStream inputStreamParam2 = new FileInputStream("");


            List<InputStream> inputStreamList = Arrays.asList(inputStreamParam, inputStreamParam2);
            FileOutputStream outputStreamMerge = new FileOutputStream("");
            PdfUtils.mergePdf(inputStreamList,outputStreamMerge);
            outputStreamMerge.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
