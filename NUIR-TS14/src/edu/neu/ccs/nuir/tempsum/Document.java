package edu.neu.ccs.nuir.tempsum;

public class Document {
	public String id;
	public String clean_visible;
	public String html;
	public Document(String id, String clean_visible, String html) {
		this.id = id;
		this.clean_visible = clean_visible;
		this.html = html;
	}
	public Document(String id, String clean_visible) {
		this(id, clean_visible, "");
	}
}
