/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;

import java.util.ArrayList;

/**
 * @author mattea
 *
 */
public class ResultSet {
	public ArrayList<Sentence> sentences;
	public double threshold;
	
	public ResultSet(ArrayList<Sentence> sentences, double threshold) {
		this.sentences = sentences;
		this.threshold = threshold;
	}
	
	public ResultSet(ArrayList<Sentence> sentences) {
		this(sentences, 0);
	}
}