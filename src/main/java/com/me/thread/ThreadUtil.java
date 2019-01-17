package com.me.thread;

import com.alibaba.fastjson.JSON;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: liujiacun
 * Date: 2019/1/11
 * Time: 9:41
 * Description: No Description
 */
public class ThreadUtil {
    private static Logger logger= LoggerFactory.getLogger(ThreadUtil.class);

    public static void methodTimeCount(){
        int n=0;
        long gap=20;
        int minute=10;
        long times=minute*60*1000/gap;
        long printLogGap=times/minute/2;
        long lastRealTime=0;
        TreeNode head=new TreeNode();
        long nowThreadId = Thread.currentThread().getId();

        while (n<times){
            ThreadMXBean mx = ManagementFactory.getThreadMXBean();
            long[] threadIds = mx.getAllThreadIds();
            ThreadInfo[] threadInfos = mx.getThreadInfo(threadIds);
            long nowRealTime=System.currentTimeMillis();
            if(lastRealTime==0){
                lastRealTime=nowRealTime;
            }
            long gapTime=nowRealTime-lastRealTime;

            for (int i=0;i<threadInfos.length;i++) {
                ThreadInfo threadInfo = threadInfos[i];
                long threadId = threadInfo.getThreadId();
                if(threadId==nowThreadId){
                    continue;
                }

                Thread thread = findThread(threadId);

                if(thread!=null){
                    if(!thread.getState().equals(Thread.State.RUNNABLE)){
                        continue;
                    }
                    TreeNode treeNode=head.getChild(threadId+"-"+thread.getName());

                    StackTraceElement[] stackTraceElements = thread.getStackTrace();
                    for (int j = stackTraceElements.length - 1; j >= 0; j--) {
                        StackTraceElement stackTraceElement = stackTraceElements[j];
                        String name = stackTraceElement.toString();
                        TreeNode treeNode2=treeNode.getChild(name);

                        if(j==0){
                            treeNode2.setTime(treeNode2.getTime()+gapTime);
                        }
                        treeNode=treeNode2;
                    }

                }

            }

            n++;
            if(n%printLogGap==0){
                head.getRealTime();
                writeFile(new File("E:\\thread\\1.txt"), JSON.toJSONString(head));
            }
            if(n%1000==0){
                logger.error(String.valueOf(n));
            }

            try {
                Thread.sleep(gap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    public static void writeFile(File file, String content) {
        createParentDir(file);
        BufferedWriter out = null;

        try {
            out = new BufferedWriter(new FileWriter(file));
            out.write(content);
            out.flush();
            out.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }


    public static void createParentDir(File file) {
        File parentPath = file.getParentFile();
        if (parentPath != null && !parentPath.exists()) {
            parentPath.mkdirs();
        }

    }

    public static void getCpuInfo(){
        int n=0;
        long gap=2000;
        int minute=10;
        long times=minute*60*1000/gap;
        long lastRealTime=System.currentTimeMillis();
        while (n++<times){
            ThreadMXBean mx = ManagementFactory.getThreadMXBean();
            long[] threadIds = mx.getAllThreadIds();
            ThreadInfo[] threadInfos = mx.getThreadInfo(threadIds);
            Map<Long,Long> map=new HashMap<>();
            int maxI=0;
            long maxTime=0;
            long allTime=0;
            List<Pair<Long,Long>> timeList=new LinkedList<>();
            long nowRealTime;
            for (int i=0;i<threadInfos.length;i++) {
                ThreadInfo threadInfo = threadInfos[i];
                long threadId = threadInfo.getThreadId();
                long time=mx.getThreadUserTime(threadId);
                long lastTime= Optional.ofNullable(map.get(threadId)).orElse(0L);
                long gapTime=time-lastTime;
                allTime+=gapTime;
                if(gapTime>maxTime){
                    maxTime=gapTime;
                    maxI=i;
                }
                map.put(threadId,time);
                timeList.add(new Pair<>(threadId,gapTime));
            }
            nowRealTime = System.currentTimeMillis();

            long gapRealTime=nowRealTime-lastRealTime;

            lastRealTime=nowRealTime;

            timeList.sort((a,b)->{
                if(a.getValue()>b.getValue()){
                    return -1;
                }else if(a.getValue()<b.getValue()){
                    return 1;
                }else {
                    return 0;
                }
            });


            int coreNum=4;
            long total=0;
            for(int j=0;j<coreNum;j++){
                total+=timeList.get(j).getValue();
            }

            double l = (double)total / (double) gapRealTime/(coreNum*1000*1000);

            ThreadInfo threadInfo = threadInfos[maxI];
            long maxSecond = maxTime / 1000 / 1000;
            logger.error("\n\n\n{} ,name {},time {},ratio {},status {}",n,threadInfo.getThreadName(), maxSecond, l,threadInfo.getThreadState());


            if(l >2.3){
                Thread thread = findThread(threadInfo.getThreadId());
                if(thread!=null){
                    for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
                        logger.error(stackTraceElement.toString());
                    }
                }

            }
            try {
                Thread.sleep(gap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static Thread findThread(long threadId) {
        long id = Thread.currentThread().getId();
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while(group != null) {
            Thread[] threads = new Thread[(int)(group.activeCount() * 1.2)];
            int count = group.enumerate(threads, true);
            for(int i = 0; i < count; i++) {
                if(threadId == threads[i].getId()) {
                    return threads[i];
                }
            }
            group = group.getParent();
        }
        return null;
    }
}
