/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;

/**
 * @author mattea
 *
 */
public class Sentence {
	public double score;
	public String text;
	public Token[] tokens;
	public Document doc;
	public int id;
	
	public Sentence(int id, Document doc, String text, Token[] tokens, double score) {
		this.id = id;
		this.doc = doc;
		this.text = text;
		this.tokens = tokens;
		this.score = score;
	}
	
	public Sentence(int id, Document doc, String text, Token[] tokens) {
		this(id, doc, text, tokens, 0);
	}
}
