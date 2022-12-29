package com.me.mock;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author buyulian
 * @date 2019/8/7
 */
public class AssertUtils {

    public static <T> void assertListEquals(List<T> aList, List<T> bList, Comparator<T> comparator){
        for (T t : aList) {
            boolean isHave=false;
            for (T t1 : bList) {
                int compare = comparator.compare(t, t1);
                if(compare==0){
                    isHave=true;
                    break;
                }
            }
            if(!isHave){
                throw new RuntimeException("两个 list 不相等");
            }
        }
    }

    public static <T> void assertListStrictEquals(List<T> aList, List<T> bList, Comparator<T> comparator){
        if(aList==null&&bList==null){
            return;
        }
        if(aList==null||bList==null){
            throw new RuntimeException("其中一个 list 为空");
        }
        if(aList.size()!= bList.size()){
            throw new RuntimeException("list size 不相同");
        }

        Iterator<T> aIterator = aList.iterator();
        Iterator<T> bIterator = bList.iterator();

        while (aIterator.hasNext()){
            T a = aIterator.next();
            T b = bIterator.next();
            int compare = comparator.compare(a, b);
            if(compare!=0){
                throw new RuntimeException("list 不想等");
            }
        }
    }

}
