package edu.neu.ccs.nuir.tempsum.sentencemodel;

import java.util.ArrayList;

import edu.neu.ccs.nuir.tempsum.Config;
import edu.neu.ccs.nuir.tempsum.DocumentSet;
import edu.neu.ccs.nuir.tempsum.ResultSet;
import edu.neu.ccs.nuir.tempsum.Sentence;
import gov.sandia.cognition.learning.algorithm.clustering.AffinityPropagation;
import gov.sandia.cognition.learning.algorithm.clustering.cluster.CentroidCluster;

public class AffinityPropagationModel extends SentenceModel {
	AffinityPropagation<Sentence> model;

	public AffinityPropagationModel(Config conf) {
		super(conf);
		this.model = new AffinityPropagation<Sentence>(new SentenceDivergenceMetric(), 0.0, 0.5, 100);
	}
	
	@Override
	void calcRelevance(ResultSet sents) {

		model.learn(sents);
		try {
			model.wait();
		} catch (InterruptedException e) {
			// TODO: Log early stopping...
		}
		ArrayList<CentroidCluster<Sentence>> res = model.getResult();
		for (CentroidCluster<Sentence> cluster : res) {
			Sentence cent = cluster.getCentroid();
			cent.novelty = 1.0;
			for (Sentence noncent : cluster.getMembers()) {
				if (noncent != cent)
					noncent.novelty = 0.0;
			}
		}
	}

	@Override
	public void train() {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<String> topTerms() {
		// TODO Auto-generated method stub
		return null;
	}

}
