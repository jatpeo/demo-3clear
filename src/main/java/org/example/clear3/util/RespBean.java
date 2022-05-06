package org.example.clear3.util;

/**
 * @Author Jiatp
 * @Description //TODO 实体json类
 * @Date 9:53 上午 2022/5/6
 **/
public class RespBean {

    private Integer status;
    private String msg;
    private Object obj;

    /**
     * 登陆成功情况
     *
     * @param msg
     * @return
     */
    public static RespBean ok(String msg) {
        return new RespBean(200, msg, null);
    }

    public static RespBean ok(String msg, Object obj) {
        return new RespBean(200, msg, obj);
    }

    /**
     * 登陆失败
     *
     * @param msg
     * @return
     */
    public static RespBean error(String msg) {
        return new RespBean(500, msg, null);
    }

    public static RespBean error(String msg, Object obj) {
        return new RespBean(500, msg, obj);
    }


    public RespBean(Integer status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public RespBean() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
