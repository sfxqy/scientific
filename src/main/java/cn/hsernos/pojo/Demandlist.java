package cn.hsernos.pojo;

import lombok.Data;

@Data
public class Demandlist {
    /**
     *
     */
    private Integer id;

    /**
     * 需求方id
     */
    private Integer nid;

    /**
     * 需求id
     */
    private Integer did;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }
}