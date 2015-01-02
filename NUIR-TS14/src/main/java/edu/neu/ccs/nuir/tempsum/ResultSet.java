/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author mattea
 *
 */
public class ResultSet implements Iterable<Sentence> {
	public double threshold;
	ArrayList<Sentence> p;
	
	public ResultSet(ArrayList<Sentence> sentences, double threshold) {
		p = sentences;
		this.threshold = threshold;
	}
	
	public ResultSet(ArrayList<Sentence> sentences) {
		this(sentences, 0);
	}
	
	public ResultSet() {
		p = new ArrayList<Sentence>();
		threshold = 0;
	}
	
	public void add(Sentence s) {
		p.add(s);
	}
	
	public void subList(int from, int to) {
		p = (ArrayList<Sentence>) p.subList(from,  to);
	}
	
	public int size() {
		return p.size();
	}
	
	public void flush() {
		for (Sentence s: p) {
			s.flush();
		}
	}

	@Override
	public Iterator<Sentence> iterator() {
		return p.iterator();
	}
}