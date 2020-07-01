package cn.hsernos.pojo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class HserResultVO {
    private String title;
    private String content;
    private BigDecimal benefit;
    private String img;
    private Date begin;
    private Date end;
    private String achievement;
    private String name;
    private String college;
    private String needname;
    private String unit;
    private Byte audit;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBenefit(BigDecimal benefit) {
        this.benefit = benefit;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setNeedname(String needname) {
        this.needname = needname;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setAudit(Byte audit) {
        this.audit = audit;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public BigDecimal getBenefit() {
        return benefit;
    }

    public String getImg() {
        return img;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    public String getAchievement() {
        return achievement;
    }

    public String getName() {
        return name;
    }

    public String getCollege() {
        return college;
    }

    public String getNeedname() {
        return needname;
    }

    public String getUnit() {
        return unit;
    }

    public Byte getAudit() {
        return audit;
    }
}
