package cn.hsernos.pojo;

import lombok.Data;

@Data
public class Resource {
    /**
     *
     */
    private Integer rid;

    /**
     * 资源类别
     */
    private String resource;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource == null ? null : resource.trim();
    }
}