package cn.ego.pojo;

import java.util.Date;

public class TbItemParam {
    private Long id;

    private long itemCatId;

    private Date created;

    private Date updated;

    private String paramData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
	public long getItemCatId() {
		return itemCatId;
	}

	public void setItemCatId(long itemCatId) {
		this.itemCatId = itemCatId;
	}

	public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData == null ? null : paramData.trim();
    }
}