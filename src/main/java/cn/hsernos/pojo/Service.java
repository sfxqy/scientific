package cn.hsernos.pojo;

import lombok.Data;

@Data
public class Service {
    /**
     *
     */
    private Integer sid;

    /**
     * 技术方id
     */
    private Integer uid;

    /**
     * 资源id
     */
    private Integer ids;

    /**
     * 资源类型id
     */
    private Integer rid;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }
}