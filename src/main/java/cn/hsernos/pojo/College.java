package cn.hsernos.pojo;

import lombok.Data;

@Data
public class College {
    /**
     *
     */
    private Integer cid;

    /**
     * 学院类别
     */
    private String college;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }
}