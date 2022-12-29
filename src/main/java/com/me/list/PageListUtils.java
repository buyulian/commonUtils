package com.me.list;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author buyulian
 * @date 2019/11/25
 */
public class PageListUtils {

    /**
     * 按页切分 list
     * @param sourceList
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitListByPage(List<T> sourceList, int pageSize){

        if (pageSize <= 0){
            throw new IndexOutOfBoundsException("splitListByPage pageSize <= 0");
        }

        List<List<T>> resultList = new LinkedList<List<T>>();

        if (CollectionUtils.isEmpty(sourceList)){
            return resultList;
        }

        int size = sourceList.size();


        if (size <= pageSize) {
            resultList.add(sourceList);
            return resultList;
        }

        Iterator<T> iterator = sourceList.iterator();
        for (int i = 0; i < size; i += pageSize) {
            List<T> newList = new LinkedList<T>();
            int beginCur = i;
            int endCur = i + pageSize;
            for (int j = beginCur; j < endCur && j < size; j++){
                T item = iterator.next();
                newList.add(item);
            }
            resultList.add(newList);
        }

        return resultList;
    }

}
