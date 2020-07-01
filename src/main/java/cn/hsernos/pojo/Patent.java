package cn.hsernos.pojo;

import lombok.Data;

@Data
public class Patent {
    /**
     *
     */
    private Integer pid;

    /**
     * 专利名称
     */
    private String name;

    /**
     * 专利照片
     */
    private String img;

    /**
     * 持有人姓名
     */
    private String username;

    /**
     * 专利类型
     */
    private String clazz;

    /**
     * 专利授权号
     */
    private String authorization;

    /**
     * 审核
     */
    private Byte audit;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization == null ? null : authorization.trim();
    }

    public Byte getAudit() {
        return audit;
    }

    public void setAudit(Byte audit) {
        this.audit = audit;
    }
}