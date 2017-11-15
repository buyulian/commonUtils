package mysql.model;

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
        if(type!=null){
            return type;
        }
        type=TypeMap.get(JdbcType);
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
        JdbcType= TypeMap.get(type);
        return JdbcType;
    }
    public String getJdbcTypeAndNum() {
        if(jdbcTypeAndNum!=null){
            return jdbcTypeAndNum;
        }
        jdbcTypeAndNum=TypeMap.get(type);
        if(jdbcTypeAndNum.equals("VARCHAR")){
            jdbcTypeAndNum="VARCHAR("+typeNum+")";
        }
        return jdbcTypeAndNum;
    }

    public void setJdbcType(String jdbcType) {
        JdbcType = jdbcType;
    }
}
