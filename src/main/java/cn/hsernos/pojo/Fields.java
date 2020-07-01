package cn.hsernos.pojo;

import lombok.Data;

@Data
public class Fields {
    /**
     *
     */
    private Integer fid;

    /**
     * 专业领域一级类别
     */
    private Integer id;

    /**
     * 专业领域二级类别
     */
    private String fields;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields == null ? null : fields.trim();
    }
}