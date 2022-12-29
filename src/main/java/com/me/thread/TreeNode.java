package com.me.thread;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: buyulian
 * Date: 2019/1/17
 * Time: 14:15
 * Description: No Description
 */
public class TreeNode {
    private long time=0;
    private String name;
    private Map<String,TreeNode> children=new HashMap<>();
    private List<TreeNode> treeNodeList=new LinkedList<>();

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public TreeNode getChild(String name){
        TreeNode treeNode = children.get(name);
        if(treeNode==null){
            treeNode=new TreeNode();
            treeNode.setName(name);
            children.put(name,treeNode);
            treeNodeList.add(treeNode);
        }
        return treeNode;
    }

    public long getRealTime(){
        if(children.size()==0){
            return time;
        }
        long sum=0;
        for (TreeNode treeNode : treeNodeList) {
            long realTime = treeNode.getRealTime();
            sum+=realTime;
        }
        treeNodeList.sort((a,b)->{
            return (int)(b.time-a.time);
        });
        time=sum;
        return time;
    }

    public List<TreeNode> getTreeNodeList() {
        return treeNodeList;
    }

    public void setTreeNodeList(List<TreeNode> treeNodeList) {
        this.treeNodeList = treeNodeList;
    }
}
