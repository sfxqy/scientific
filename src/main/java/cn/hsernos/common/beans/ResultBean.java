package cn.hsernos.common.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * controll 统一返回 ResultBean
 *
 */
@Data
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 未登录
     */
    public static final int NO_LOGIN = -1;

    /**
     * 成功
     */
    public static final int SUCCESS = 0;

    /**
     * 失败
     */
    public static final int FAIL = 1;

    /**
     * 没有权限
     */
    public static final int NO_PERMISSION = 2;

    private String msg = "success";

    private int code = SUCCESS;

    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = FAIL;
    }

    public ResultBean(String msg, int code) {
        super();
        this.msg = msg;
        this.code = code;
    }

}
