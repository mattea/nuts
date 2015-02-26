package edu.neu.ccs.nuir.tempsum.sentencemodel;

import java.util.HashSet;

import edu.neu.ccs.nuir.tempsum.Sentence;
import edu.neu.ccs.nuir.tempsum.Token;
import gov.sandia.cognition.math.Semimetric;
import gov.sandia.cognition.util.AbstractCloneableSerializable;

public class SentenceDivergenceMetric extends AbstractCloneableSerializable implements
		Semimetric<Sentence> {

	private static final long serialVersionUID = 1L;
	
	public static final SentenceDivergenceMetric INSTANCE =
			new SentenceDivergenceMetric();
	
	public SentenceDivergenceMetric() {
		super();
	}

	@Override
	public double evaluate(Sentence first, Sentence second) {
		Token[] toks = first.tokens();
		HashSet<String> map = second.bagofwords();
		int cnt = 0;
		for (Token tok : toks) {
			if (map.contains(tok.text))
				cnt++;
		}
		return 2 * cnt / (toks.length + map.size());
	}
	
}