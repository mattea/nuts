package edu.neu.ccs.nuir.tempsum.sentencemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.neu.ccs.nuir.tempsum.Config;
import edu.neu.ccs.nuir.tempsum.Sentence;
import edu.neu.ccs.nuir.tempsum.Token;

public final class KeywordSentenceModel extends IndependentSentenceModel {
	Map<String,Double> keywords;
	
	static final Map<String, Double> basemap = ImmutableMap.<String, Double>builder()
			.put("kill",0.9)
			.put("arrest",0.9)
			.put("hurt",0.8)
			.put("injure",0.6)
			.put("damage",0.5)
			.build();
	

	KeywordSentenceModel(Config conf, Map<String,Double> keywords) {
		super(conf);
		this.keywords = keywords;
	}
	
	KeywordSentenceModel(Config conf) {
		this(conf, new HashMap<String,Double>(basemap));
	}
	
	double matchModel(Sentence sent) {
		double score = 0;
		for(Token tok: sent.tokens()) {
			if (keywords.containsKey(tok.text)) {
				score = Math.max(score, keywords.get(tok.text));
			}
		}
		return score;
	}
	
	public void train() {
		// Eventually learn a model from the training data
	}
	
	public ArrayList<String> topTerms() {
		// TODO: Sort the keywords by their score and return them.
		ArrayList<String> terms = new ArrayList<String>();

		return terms;
	}

}
