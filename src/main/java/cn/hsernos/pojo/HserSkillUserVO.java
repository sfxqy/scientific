package cn.hsernos.pojo;

public class HserSkillUserVO {

    /**
     * 工号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 部门id
     */
    private Integer cid;

    /**
     * 部门类别
     */
    private String college;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 图片
     */
    private String img;

    /**
     * 职称
     */
    private String title;

    /**
     * 学位
     */
    private String degree;

    /**
     * 二级专业领域类别id
     */
    private Integer fid;

    /**
     * 二级专业领域类别
     */
    private String fields;

    /**
     * 一级专业领域类别id
     */
    private Integer id;

    /**
     * 一级专业领域类别
     */
    private String field;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 基本信息简介
     */
    private String info;

    /**
     * 审核
     */
    private Byte audit;


    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields == null ? null : fields.trim();
    }


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field == null ? null : field.trim();
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public Byte getAudit() {
        return audit;
    }

    public void setAudit(Byte audit) {
        this.audit = audit;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }


    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "HserSkillUserVO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", cid=" + cid +
                ", college='" + college + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", degree='" + degree + '\'' +
                ", fid=" + fid +
                ", fields='" + fields + '\'' +
                ", id=" + id +
                ", field='" + field + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", info='" + info + '\'' +
                ", audit=" + audit +
                '}';
    }
}
