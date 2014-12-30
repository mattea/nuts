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
import edu.neu.ccs.nuir.tempsum.Sentence;


// Create simple TF/IDF model of all topics
// Or an LDA model of the matches in the topics

/**
 * @author mattea
 *
 */
public class SentenceModel {
	Config conf;
	Map<String,Double> keywords;
	static final Map<String, Double> basemap = ImmutableMap.<String, Double>builder()
			.put("kill",0.9)
			.put("arrest",0.9)
			.put("hurt",0.8)
			.put("injure",0.6)
			.put("damage",0.5)
			.build();
	
	SentenceModel(Config conf, Map<String,Double> keywords) {
		this.conf = conf;
		this.keywords = keywords;
	}
	
	SentenceModel(Config conf) {
		this(conf, new HashMap<String,Double>(basemap));
	}
	
	double score(Sentence sent) {
		return 0;
	}
	
	public ArrayList<Sentence> rankSentences(DocumentSet docs) {
		ArrayList<Sentence> sents = new ArrayList<Sentence>();
		
		
		return sents;
	}
	
	public void train() {
		// Eventually learn a model from the training data
	}

	public ArrayList<String> topTerms() {
		// TODO: Sort the keywords by their score and return them.
		ArrayList<String> terms = new ArrayList<String>();

		return terms;
	}
	

	public ArrayList<String> topTerms(DocumentSet docs) {
		 ArrayList<String> terms = new ArrayList<String>();
		 
		 return terms;
	}
	
	public void save(Config conf) {
		try {
			String statefile = conf.get("sentence_model");		
			FileOutputStream fos = new FileOutputStream(statefile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.keywords);
			oos.close();
		} catch (IOException e) {
			System.err.println("Can not find/open file in which to save model, not saving...");
			e.printStackTrace();
		}
	}
	
	public static SentenceModel load(Config conf) {
		try {
			String statefile = conf.get("sentence_model");
	        FileInputStream fis = new FileInputStream(statefile);
	        ObjectInputStream ois = new ObjectInputStream(fis);
			Map<String,Double> state = (Map<String,Double>) ois.readObject();
	        ois.close();
			return new SentenceModel(conf, state);
		} catch (IOException|ClassNotFoundException e) {
			return new SentenceModel(conf);
		}
	}
}
