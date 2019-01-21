package com.me.compute;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liujiacun
 * Date: 2019/1/18
 * Time: 11:12
 * Description: No Description
 */
public class ComputeUtil {
    private static String numberStr="0123456789.";

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
                }else if("+".equals(e)||"-".equals(e)){
                    String lastOpt = optStack.pop();
                    BigDecimal a = numStack.pop();
                    BigDecimal b = numStack.pop();
                    BigDecimal c = operation(a,b,lastOpt);
                    numStack.push(c);
                    optStack.push(e);

                }else if("*".equals(e)||"/".equals(e)){
                    String lastOpt = optStack.peekLast();
                    if("*".equals(lastOpt)||"/".equals(lastOpt)){
                        BigDecimal a = numStack.pop();
                        BigDecimal b = numStack.pop();
                        BigDecimal c = operation(a,b,lastOpt);
                        numStack.push(c);
                        optStack.pop();
                        optStack.push(e);

                    }else {
                        optStack.push(e);
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
            BigDecimal b = numStack.pop();
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
        if("+".equals(opt)){
            return a.add(b);
        }else if( "-".equals(opt)){
            return b.subtract(a);
        }else if( "*".equals(opt)){
            return a.multiply(b);
        }else if( "/".equals(opt)){
            return b.divide(a,6);
        }
        throw new RuntimeException("不支持的运算符");
    }

    private static List<Word> splitWord(String expression){
        List<Word> wordList=new LinkedList<>();
        int i=0;
        while (i<expression.length()){
            char c=expression.charAt(i);
            if(c=='+'||c=='-'||c=='*'||c=='/'||c=='('||c==')'){
                String w=String.valueOf(c);
                wordList.add(new Word(w, Word.Type.OPERATOR));
                i++;
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
