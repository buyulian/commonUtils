package mysql.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangzunqiao on 2017/8/15.
 */
public class Table extends NameBase{
    private String comment;
    public List<Field> fieldList=new LinkedList<>();


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }
}
