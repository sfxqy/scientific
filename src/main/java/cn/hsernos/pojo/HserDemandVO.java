package cn.hsernos.pojo;

import lombok.Data;

@Data
public class HserDemandVO {

    private Integer nid;

    private Integer did;

    /**
     * 需求名
     */
    private String name;

    /**
     * 需求描述
     */
    private String describe;

    /**
     * 照片
     */
    private String img;
}
