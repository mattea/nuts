/**
 * 
 */
package edu.neu.ccs.nuir.tempsum.sentencemodel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.neu.ccs.nuir.tempsum.Config;
import edu.neu.ccs.nuir.tempsum.DocumentSet;
import edu.neu.ccs.nuir.tempsum.ResultSet;
import edu.neu.ccs.nuir.tempsum.Sentence;
import edu.neu.ccs.nuir.tempsum.Token;


// Create simple TF/IDF model of all topics
// Or an LDA model of the matches in the topics

/**
 * @author mattea
 *
 */
abstract class SentenceModel {
	Config conf;
	
	SentenceModel(Config conf) {
		this.conf = conf;
	}
	
	abstract double score(Sentence sent);
	
	public ResultSet rankSentences(DocumentSet docs) {
		ResultSet sents = docs.extractSentences();
		calcRelevance(sents);
		return sents;
	}
	
	abstract void calcRelevance(ResultSet sents);
	
	abstract double matchModel(Sentence sent);
	
	public abstract void train();

	public abstract ArrayList<String> topTerms();
	
	public void save(Config conf) {
		try {
			String statefile = conf.get("sentence_model");
			FileOutputStream fos = new FileOutputStream(statefile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
		} catch (IOException e) {
			System.err.println("Can not find/open file in which to save model, not saving...");
			e.printStackTrace();
		}
	}
	
	public static SentenceModel load(Config conf) {
		try {
			String statefile = conf.get("sentence_model");
//			String modeltype = conf.get("sentence_model_class");
	        FileInputStream fis = new FileInputStream(statefile);
	        ObjectInputStream ois = new ObjectInputStream(fis);
			SentenceModel model = (SentenceModel) ois.readObject();
	        ois.close();
			return model;
		} catch (IOException|ClassNotFoundException e) {
			return new KeywordSentenceModel(conf);
		}
	}
}
