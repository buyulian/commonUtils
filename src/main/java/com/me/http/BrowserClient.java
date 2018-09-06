package com.me.http;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrowserClient {
    private CloseableHttpClient httpclient = HttpClients.createDefault();

    private Map<String,String> defaultHeader =new HashMap<>();

    private String encoding= HTTP.UTF_8;

    {
        defaultHeader.put("Content-type", "application/x-www-form-urlencoded");
        defaultHeader.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        defaultHeader.put("Accept-Encoding","gzip,deflate,sdch");
        defaultHeader.put("Accept-Language","zh-CN,zh;q=0.8");
        defaultHeader.put("Connection","keep-alive");
        defaultHeader.put("Host", "ssa.jd.com");
        defaultHeader.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");

    }

    public String doPost(String url,List<NameValuePair> pairs,Map<String,String> header) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs,encoding));

        defaultHeader.forEach(httpPost::setHeader);

        header.forEach(httpPost::setHeader);

        CloseableHttpResponse response = httpclient.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        String body=null;
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }

    public JsonObject doPostJson(String url,List<NameValuePair> pairs,Map<String,String> header) throws Exception {

        String rs=doPost(url,pairs,header);
        if(rs==null||rs.equals("")){
            return null;
        }
        JsonObject jsonObject=new JsonParser().parse(rs).getAsJsonObject();
        return jsonObject;
    }

    public void close(){
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
