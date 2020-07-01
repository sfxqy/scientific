package cn.hsernos.pojo;

import lombok.Data;

@Data
public class Software {
    /**
     *
     */
    private Integer sid;

    /**
     * 软著名称
     */
    private String name;

    /**
     * 持有人姓名
     */
    private String username;

    /**
     * 软著证书照片
     */
    private String img;

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

    public Byte getAudit() {
        return audit;
    }

    public void setAudit(Byte audit) {
        this.audit = audit;
    }
}