package edu.neu.ccs.nuir.tempsum;

import org.elasticsearch.search.SearchHit;

public class Document {
	public String id;
	public String clean_visible;
	public String html;
	public Sentence[] sentences;
	public SearchHit hit;
	
	public Document(String id, String clean_visible, String html, Sentence[] sentences) {
		this.id = id;
		this.clean_visible = clean_visible;
		this.html = html;
		this.sentences = sentences;
	}
	public Document(String id, String clean_visible) {
		this(id, clean_visible, "", new Sentence[0]);
	}
	
	public Document(SearchHit hit) {
		this.hit = hit;
	}
}
