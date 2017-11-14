package database.mysql.model;

/**
 * Created by zhangzunqiao on 2017/8/15.
 */
public class Field extends NameBase{
    private String type;
    private String JdbcType;
    private String comment;
    private String typeNum;
    private String jdbcTypeAndNum;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(String typeNum) {
        this.typeNum = typeNum;
    }

    public String getJdbcType() {
        if(JdbcType!=null){
            return JdbcType;
        }
        switch (type){
            case "Integer":
            case "int":
                JdbcType="INTEGER";
                break;
            case "String":
                JdbcType="VARCHAR";
                break;
            case "Date":
                JdbcType="TIMESTAMP";
                break;
            case "Double":
            case "double":
                JdbcType="DOUBLE";
                break;
            case "Long":
            case "long":
                JdbcType="BIGINT";
                break;
            case "Boolean":
            case "boolean":
                JdbcType="BIT";
                break;
            case "Byte":
            case "byte":
                JdbcType="TINYINT";
                break;
            case "Short":
            case "short":
                JdbcType="SMALLINT";
                break;
        }
        return JdbcType;
    }
    public String getJdbcTypeAndNum() {
        if(jdbcTypeAndNum!=null){
            return jdbcTypeAndNum;
        }
        switch (type){
            case "Integer":
            case "int":
                jdbcTypeAndNum="INTEGER";
                break;
            case "String":
                jdbcTypeAndNum="VARCHAR("+typeNum+")";
                break;
            case "Date":
                jdbcTypeAndNum="TIMESTAMP";
                break;
            case "Double":
            case "double":
                jdbcTypeAndNum="DOUBLE";
                break;
            case "Long":
            case "long":
                jdbcTypeAndNum="BIGINT";
                break;
            case "Boolean":
            case "boolean":
                jdbcTypeAndNum="BIT";
                break;
            case "Byte":
            case "byte":
                jdbcTypeAndNum="TINYINT";
                break;
            case "Short":
            case "short":
                jdbcTypeAndNum="SMALLINT";
                break;
        }
        return jdbcTypeAndNum;
    }

    public void setJdbcType(String jdbcType) {
        JdbcType = jdbcType;
    }
}
