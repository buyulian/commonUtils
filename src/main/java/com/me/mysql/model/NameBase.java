package com.me.mysql.model;

import com.me.string.NameUtils;

/**
 * Created by zhangzunqiao on 2017/8/17.
 */
public class NameBase {
    public String name;
    private String cName;
    private String mName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCName() {
        if(cName==null){
            cName= NameUtils.toClassName(name);
        }
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getMName() {
        if(mName==null){
            mName=NameUtils.toMethodName(name);
        }
        return mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }
}
