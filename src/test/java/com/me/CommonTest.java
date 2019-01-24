package com.me;

import com.me.compute.ComputeUtil;
import org.junit.Assert;
import org.junit.Test;

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
    }

    @Test
    public void testCompute(){
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
