package cn.hsernos.pojo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class Result {
    /**
     *
     */
    private Integer rid;

    /**
     * 服务id
     */
    private Integer sid;

    /**
     * 需求id
     */
    private Integer did;

    /**
     * 技术方id
     */
    private Integer uid;

    /**
     * 成果分类
     */
    private Integer aid;

    /**
     * 标题
     */
    private String title;

    /**
     * 照片
     */
    private String img;

    /**
     * 经济效益
     */
    private BigDecimal benefit;

    /**
     * 开始时间
     */
    private Date begin;

    /**
     * 结束时间
     */
    private Date end;

    /**
     * 审核
     */
    private Byte audit;

    /**
     * 服务内容
     */
    private String content;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public BigDecimal getBenefit() {
        return benefit;
    }

    public void setBenefit(BigDecimal benefit) {
        this.benefit = benefit;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Byte getAudit() {
        return audit;
    }

    public void setAudit(Byte audit) {
        this.audit = audit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}