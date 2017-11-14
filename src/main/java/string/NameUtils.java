package string;

/**
 * Created by zhangzunqiao on 2017/8/16.
 */
public class NameUtils {
    public static String toClassName(String name){
        String[] split = name.split("_");
        StringBuilder sb=new StringBuilder();
        for(String str:split){
            sb.append(str.substring(0,1).toUpperCase()+str.substring(1,str.length()));
        }
        return sb.toString();
    }
    public static String toMethodName(String name){
        String[] split = name.split("_");
        StringBuilder sb=new StringBuilder();
        int a=0;
        for(String str:split){
            if(a==0){
                a=1;
                sb.append(str);
                continue;
            }
            sb.append(str.substring(0,1).toUpperCase()+str.substring(1,str.length()));
        }
        return sb.toString();
    }
}
