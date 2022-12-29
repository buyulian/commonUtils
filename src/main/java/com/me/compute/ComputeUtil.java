package com.me.compute;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: buyulian
 * Date: 2019/1/18
 * Time: 11:12
 * Description: No Description
 */
public class ComputeUtil {
    private static String numberStr="0123456789.";

    private static Map<String,Integer> priorityMap=new HashMap<>();

    private static String NOT_OPT="!";

    static {
        int priority=1;

        priorityMap.put("&&",priority);
        priorityMap.put("||",priority);
        priorityMap.put("^",priority);
        priority++;
        priorityMap.put(">",priority);
        priorityMap.put("<",priority);
        priorityMap.put(">=",priority);
        priorityMap.put("<=",priority);
        priorityMap.put("==",priority);
        priorityMap.put("!=",priority);
        priority++;
        priorityMap.put("+",priority);
        priorityMap.put("-",priority);
        priority++;
        priorityMap.put("*",priority);
        priorityMap.put("/",priority);
        priority++;
        priorityMap.put("!",priority);
    }

    static class MiddleResult{
        BigDecimal data;
        int cur;
    }

    public static BigDecimal compute(String expression){
        List<Word> wordList = splitWord(expression);
        Word[] words = wordList.toArray(new Word[wordList.size()]);
        MiddleResult rs = computeSub(words, 0);
        return rs.data;
    }

    private static MiddleResult computeSub(Word[] words,int cur){
        LinkedList<BigDecimal> numStack=new LinkedList<>();
        LinkedList<String> optStack=new LinkedList<>();

        for (;cur<words.length;cur++) {
            Word word = words[cur];
            String e = word.getData().toString();
            if(word.getType().equals(Word.Type.OPERATOR)){
                if("(".equals(e)){
                    MiddleResult middleResult = computeSub(words, cur + 1);
                    cur=middleResult.cur;
                    numStack.push(middleResult.data);
                }else if(")".equals(e)||cur==words.length-1){
                    break;
                }else if(optStack.size()==0){
                    optStack.push(e);
                }else{
                    String lastOpt = optStack.pop();
                    boolean isBreak=false;
                    while (NOT_OPT.equals(lastOpt)){
                        BigDecimal a = numStack.pop();
                        numStack.push(operation(a,null,NOT_OPT));
                        if(optStack.size()==0){
                            isBreak=true;
                            break;
                        }
                        lastOpt = optStack.pop();
                    }
                    if(isBreak){
                        optStack.push(e);
                    }else {
                        Integer ePriority = priorityMap.get(e);
                        Integer lastOptPriority = priorityMap.get(lastOpt);

                        if(ePriority<=lastOptPriority){
                            BigDecimal a = numStack.pop();
                            BigDecimal b = numStack.pop();
                            BigDecimal c = operation(a,b,lastOpt);
                            numStack.push(c);
                            optStack.push(e);
                        }else {
                            optStack.push(lastOpt);
                            optStack.push(e);
                        }
                    }

                }
            }else if(word.getType().equals(Word.Type.NUMBER)){
                numStack.push(new BigDecimal(e));
            }else if(word.getType().equals(Word.Type.FUNCTION)){
                MiddleResult middleResult = computeFunc(words, cur);
                cur=middleResult.cur;
                numStack.push(middleResult.data);
            }else if(word.getType().equals(Word.Type.COMMA)){
                break;
            }
        }
        while (optStack.size()>0){
            String opt = optStack.pop();
            BigDecimal a = numStack.pop();
            BigDecimal b=null;
            if(!NOT_OPT.equals(opt)){
                b = numStack.pop();
            }
            BigDecimal c = operation(a,b,opt);
            numStack.push(c);
        }
        MiddleResult middleResult=new MiddleResult();
        middleResult.data=numStack.pop();
        middleResult.cur=cur;
        return middleResult;
    }

    private static MiddleResult computeFunc(Word[] words,int cur){
        Word word = words[cur];
        MiddleResult middleResult = computeSub(words, cur + 2);
        BigDecimal a= middleResult.data;
        cur=middleResult.cur;
        MiddleResult middleResult2 = computeSub(words, cur + 1);
        BigDecimal b= middleResult2.data;
        cur=middleResult2.cur;

        BigDecimal c;
        if("max".equals(word.getData())){
            c=a.max(b);
        }else if("min".equals(word.getData())){
            c=a.min(b);
        }else {
            throw new RuntimeException("不支持的运算符");
        }
        MiddleResult rs=new MiddleResult();
        rs.cur=cur;
        rs.data=c;
        return rs;
    }

    private static BigDecimal operation(BigDecimal a,BigDecimal b,String opt){
        a=a.setScale(6,BigDecimal.ROUND_HALF_UP);
        if(NOT_OPT.equals(opt)){
            return BigDecimal.ONE.subtract(a);
        }
        b=b.setScale(6,BigDecimal.ROUND_HALF_UP);

        if("+".equals(opt)){
            return a.add(b);
        }else if( "-".equals(opt)){
            return b.subtract(a);
        }else if( "*".equals(opt)){
            return a.multiply(b);
        }else if( "/".equals(opt)){
            return b.divide(a,6);
        }else if( ">".equals(opt)){
            return b.compareTo(a) > 0 ?BigDecimal.ONE:BigDecimal.ZERO;
        }else if( "<".equals(opt)){
            return b.compareTo(a) < 0 ?BigDecimal.ONE:BigDecimal.ZERO;
        }else if( ">=".equals(opt)){
            return b.compareTo(a) >= 0 ?BigDecimal.ONE:BigDecimal.ZERO;
        }else if( "<=".equals(opt)){
            return b.compareTo(a) <= 0 ?BigDecimal.ONE:BigDecimal.ZERO;
        }else if( "==".equals(opt)){
            return b.compareTo(a) == 0 ?BigDecimal.ONE:BigDecimal.ZERO;
        }else if( "!=".equals(opt)){
            return b.compareTo(a) != 0 ?BigDecimal.ONE:BigDecimal.ZERO;
        }else if("^".equals(opt)){
            return b.compareTo(a) == 0?BigDecimal.ZERO:BigDecimal.ONE;
        }else if("&&".equals(opt)){
            return b.compareTo(BigDecimal.ZERO)*a.compareTo(BigDecimal.ZERO) == 0?BigDecimal.ZERO:BigDecimal.ONE;
        }else if("||".equals(opt)){
            return b.compareTo(BigDecimal.ZERO)+a.compareTo(BigDecimal.ZERO) == 0?BigDecimal.ZERO:BigDecimal.ONE;
        }
        throw new RuntimeException("不支持的运算符");
    }

    private static BigDecimal operationOne(BigDecimal a,String opt){
        if("!".equals(opt)){
            return a.add(BigDecimal.valueOf(-1));
        }
        throw new RuntimeException("不支持的运算符");
    }

    private static List<Word> splitWord(String expression){
        List<Word> wordList=new LinkedList<>();
        int i=0;
        while (i<expression.length()){
            char c=expression.charAt(i);
            if(c=='+'||c=='-'||c=='*'||c=='/'||c=='('||c==')'||c=='^'){
                String w=String.valueOf(c);
                wordList.add(new Word(w, Word.Type.OPERATOR));
                i++;
            }else if(c=='>'||c=='<'||c=='='||c=='!'){
                char c2=expression.charAt(i+1);
                if(c2=='='){
                    String w=String.valueOf(c)+String.valueOf(c2);
                    wordList.add(new Word(w, Word.Type.OPERATOR));
                    i+=2;
                }else {
                    String w=String.valueOf(c);
                    wordList.add(new Word(w, Word.Type.OPERATOR));
                    i++;
                }

            }else if(c=='&'||c=='|'){
                char c2=expression.charAt(i+1);
                String w=String.valueOf(c)+String.valueOf(c2);
                wordList.add(new Word(w, Word.Type.OPERATOR));
                i+=2;

            }else if(c=='m'){
                String w=expression.substring(i,i+3);
                i+=3;
                wordList.add(new Word(w, Word.Type.FUNCTION));
            }else if(c==' '){
                i+=1;
            }else if(c==','){
                String w=String.valueOf(c);
                wordList.add(new Word(w, Word.Type.COMMA));
                i+=1;
            }else if(numberStr.contains(String.valueOf(c))){
                StringBuilder sb=new StringBuilder();
                for(int j=i;j<expression.length()&&numberStr.contains(String.valueOf(expression.charAt(j)));j++){
                    sb.append(expression.charAt(j));
                    i++;
                }
                String w = sb.toString();
                wordList.add(new Word(w, Word.Type.NUMBER));
            }else {
                throw new RuntimeException("不支持的表达式符号");
            }
        }
        return wordList;
    }
}
