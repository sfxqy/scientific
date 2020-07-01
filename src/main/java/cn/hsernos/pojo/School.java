package cn.hsernos.pojo;

import lombok.Data;

@Data
public class School {
    /**
     *
     */
    private Integer sid;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 照片
     */
    private String img;

    /**
     * 项目类别
     */
    private String clazz;

    /**
     * 审核
     */
    private Byte audit;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }

    public Byte getAudit() {
        return audit;
    }

    public void setAudit(Byte audit) {
        this.audit = audit;
    }
}