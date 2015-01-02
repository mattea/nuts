/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;

import org.elasticsearch.search.SearchHit;

/**
 * @author mattea
 *
 */
public class Sentence {
	public double score;
	public double rel;
	public String text;
	Token[] tokens;
	public SearchHit doc;
	public int id;
	public String did;
	
	public Sentence(int id, SearchHit doc, String text, Token[] tokens, double score) {
		this.id = id;
		this.doc = doc;
		this.text = text;
		this.tokens = tokens;
		this.score = score;
		did = (String) doc.field("stream_id").value();
	}
	
	public Sentence(int id, SearchHit doc, String text, Token[] tokens) {
		this(id, doc, text, tokens, 0);
	}
	
	public Sentence(int id, SearchHit doc, String text) {
		this(id, doc, text, null, 0);
	}
	
	public void flush() {
		doc = null;
	}
	
	public Token[] tokens() {
		if (tokens != null)
			return tokens;
		
		String[] words = text.split("\\s");
		tokens = new Token[words.length];
		for(int i = 0; i < words.length; i++) tokens[i] = new Token(words[i]);
		
		return tokens;
	}
}
