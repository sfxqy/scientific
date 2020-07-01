package cn.hsernos.pojo;

import lombok.Data;

@Data
public class Terrace {
    /**
     *
     */
    private Integer tid;

    /**
     * 平台名称
     */
    private String name;

    /**
     * 负责人
     */
    private String username;

    /**
     * 照片
     */
    private String img;

    /**
     * 平台介绍
     */
    private String introduce;

    /**
     * 审核
     */
    private Byte audit;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public Byte getAudit() {
        return audit;
    }

    public void setAudit(Byte audit) {
        this.audit = audit;
    }
}