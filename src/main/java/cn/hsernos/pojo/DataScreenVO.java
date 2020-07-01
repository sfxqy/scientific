package cn.hsernos.pojo;


import lombok.Data;

/**
 * @author SFX
 */
@Data
public class DataScreenVO {
    //学院
    private Integer cid;

    //学院名称
    private String college;

    //时间
    private String times;

    //成果
    private Integer turnover;

    //经济效益
    private double benefits;

}
