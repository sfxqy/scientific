package cn.hsernos.pojo;

public class HserSkillListVO {
    private Integer uid;
    private String name;
    private String img;
    private String info;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "HserSkillListVO{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
