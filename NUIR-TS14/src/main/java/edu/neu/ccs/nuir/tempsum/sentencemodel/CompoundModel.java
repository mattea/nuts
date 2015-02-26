package edu.neu.ccs.nuir.tempsum.sentencemodel;

import java.util.ArrayList;

import edu.neu.ccs.nuir.tempsum.Config;
import edu.neu.ccs.nuir.tempsum.ResultSet;
import edu.neu.ccs.nuir.tempsum.Sentence;

public class CompoundModel extends SentenceModel {
	ArrayList<SentenceModel> models;

	public CompoundModel(Config conf, ArrayList<SentenceModel> models) {
		super(conf);
		this.models = models;
	}

	@Override
	void calcRelevance(ResultSet sents) {
		for (SentenceModel model: models)
			model.calcRelevance(sents);
	}
	
	@Override
	public void train() {
		for (SentenceModel model: models)
			model.train();
	}

	@Override
	public ArrayList<String> topTerms() {
		ArrayList<String> terms = new ArrayList<String>();
		for (SentenceModel model: models)
			terms.addAll(model.topTerms());
		return terms;
	}

}
