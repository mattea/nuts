/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author mattea
 *
 */
public class ResultSet implements Iterable<Sentence>, Collection<Sentence> {
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
	
	public boolean add(Sentence s) {
		return p.add(s);
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

	@Override
	public boolean isEmpty() {
		return p.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		if (o.getClass() != Sentence.class)
			return false;
		return p.contains((Sentence) o);
	}

	@Override
	public Object[] toArray() {
		return p.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return p.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		return p.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return p.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Sentence> c) {
		return p.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return p.removeAll(c);
	}

	@Override
	public boolean removeIf(Predicate<? super Sentence> filter) {
		return p.removeIf(filter);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return p.retainAll(c);
	}

	@Override
	public void clear() {
		p.clear();
	}

	@Override
	public Stream<Sentence> stream() {
		return p.stream();
	}

	@Override
	public Stream<Sentence> parallelStream() {
		return p.parallelStream();
	}

	@Override
	public void forEach(Consumer<? super Sentence> action) {
		p.forEach(action);
	}

	@Override
	public Spliterator<Sentence> spliterator() {
		return p.spliterator();
	}
}