package cn.hsernos.pojo;

import lombok.Data;

@Data
public class Demand {
    /**
     *
     */
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

    /**
     * 联系人姓名
     */
    private String username;

    /**
     * 联系人方式
     */
    private String phone;

    /**
     * 审核
     */
    private Byte audit;

    /**
     * 需求具体信息
     */
    private String detail;

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Byte getAudit() {
        return audit;
    }

    public void setAudit(Byte audit) {
        this.audit = audit;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }
}