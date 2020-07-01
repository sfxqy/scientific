package cn.hsernos.pojo;

import lombok.Data;

@Data
public class Achievement {
    /**
     *
     */
    private Integer aid;

    /**
     * 成果类别
     */
    private String achievement;

    /**
     * 详细
     */
    private String detail;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement == null ? null : achievement.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }
}