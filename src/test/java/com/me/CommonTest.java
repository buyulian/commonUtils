package com.me;

import com.me.code.CodeUtil;
import com.me.compute.ComputeUtil;
import com.me.file.FileIo;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: liujiacun
 * Date: 2019/1/10
 * Time: 15:44
 * Description: No Description
 */
public class CommonTest {

    @Test
    public void testSimple(){
        String source = "com.jd.clbs.price";
        String target = "com.jd.clbs.universal.price";
        File file = new File("E:\\workcode\\clbs\\clbs-universal-price");
        int n = FileIo.replaceFolderFileContent(file, source, target);
        System.out.println(n);

    }

    @Test
    public void testAddField(){
        String filePath="E:\\workcode\\clbs\\clbs-universal-price\\clbs-universal-price-support\\src\\main\\resources\\mapper\\strategy\\StrategyDao.xml";
        File file=new File(filePath);
        String content = FileIo.readFile(file);
        String contentAfter = CodeUtil.addField(content
                , new String[]{
                        "target_no",
                        "targetNo",
                }
                , new String[][]{
                        new String[]{
                                "strategy_type",
                                "strategyType",
                        },
                }
                , new String[]{
                        "target_name",
                        "targetName",
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
}
