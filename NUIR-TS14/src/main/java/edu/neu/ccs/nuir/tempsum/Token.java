/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;

/**
 * @author mattea
 *
 */
public class Token {
	public int id;
	public double score;
	public String text;
	public String pos;
	// TODO: Add relationships
	public Token(String text) {
		this.text = text;
	}
}
