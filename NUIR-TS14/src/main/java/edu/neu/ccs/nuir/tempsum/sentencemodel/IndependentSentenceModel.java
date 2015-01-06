package edu.neu.ccs.nuir.tempsum.sentencemodel;

import edu.neu.ccs.nuir.tempsum.Config;
import edu.neu.ccs.nuir.tempsum.ResultSet;
import edu.neu.ccs.nuir.tempsum.Sentence;

public abstract class IndependentSentenceModel extends SentenceModel {
	
	IndependentSentenceModel(Config conf) {
		super(conf);
	}
	
	void calcRelevance(ResultSet sents) {
		for(Sentence sent: sents) {
			sent.rel = matchModel(sent);
		}
	}

}
