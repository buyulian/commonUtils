package string;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
        public static String matcherInclude(String prefix,String suffix,String content){
            String regex=prefix+".*?"+suffix;
            Pattern p=Pattern.compile(regex);
            Matcher matcher=p.matcher(content);
            if(matcher.find()){
                return matcher.group();
            }
            return null;
        }
        public static String matcherExclude(String prefix,String suffix,String content){
            String regex= MessageFormat.format("(?<={0}).*(?={1})",prefix,suffix);
            Pattern p=Pattern.compile(regex);
            Matcher matcher=p.matcher(content);
            if(matcher.find()){
                return matcher.group();
            }
            return null;
        }
}
