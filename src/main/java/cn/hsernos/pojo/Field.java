package cn.hsernos.pojo;

import lombok.Data;

@Data
public class Field {
    /**
     *
     */
    private Integer id;

    /**
     * 专业领域一级类别
     */
    private String field;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field == null ? null : field.trim();
    }
}