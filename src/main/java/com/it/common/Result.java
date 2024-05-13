package com.it.common;

/**
 * @author 
 * @date 2024/4/5 23:36
 */
public class Result<T>{

    private T data;

    private Long total = 0L;

    private int code;

    private String msg;

    public Result(T data, int code, String msg){
        this.data =  data;
        this.code = code;
        this.msg = msg;
    }

    public Result(T data, int code, String msg, Long total){
        this.data =  data;
        this.code = code;
        this.msg = msg;
        this.total = total;
    }

    public static <T> Result ok(T data, int code, String msg){
        return new Result(data, code, msg);
    }
    public static <T> Result ok(T data, int code, String msg, Long total){
        return new Result(data, code, msg, total);
    }

    public static <T> Result ok(T data){
        return new Result(data, 200,  "sucess");
    }

    public static Result defaultOk(){
        return new Result(null, 200, "success");
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
