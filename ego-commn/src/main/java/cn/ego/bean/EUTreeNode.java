package cn.ego.bean;

//tree node 用于构建树
public class EUTreeNode {

	private long id; // 节点id
	private String text;// 节点名称
	private String state;// closed：父节点，open：子节点

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
