package com.me.compute;

import java.util.Collection;
import java.util.HashMap;
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

    public int getChildrenSize(){
        return children.size();
    }

    public Map<String, TreeNode> getChildren() {
        return children;
    }

    public void setChildren(Map<String, TreeNode> children) {
        this.children = children;
    }

    public void addChild(TreeNode treeNode){
        children.put(treeNode.getName(),treeNode);
    }

    public TreeNode getChild(String name){
        TreeNode treeNode = children.get(name);
        if(treeNode==null){
            treeNode=new TreeNode();
            treeNode.setName(name);
            children.put(name,treeNode);
        }
        return treeNode;
    }

    public long getRealTime(){
        if(children.size()==0){
            return time;
        }
        Collection<TreeNode> values = children.values();
        long sum=0;
        for (TreeNode value : values) {
            long realTime = value.getRealTime();
            sum+=realTime;
        }
        time=sum;
        return time;
    }
}
