/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;

import java.util.ArrayList;

import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.Hours;
import org.elasticsearch.indices.recovery.RecoveryState.Start;

import edu.neu.ccs.nuir.tempsum.sentencemodel.SentenceModel;
import edu.neu.ccs.nuir.tempsum.search.Search;


/**
 * @author mattea
 *
 */
public class TemporalSummarization {
	
	Config config;
	Topic[] topics;
	//SentenceModel model;
	//Search search;
	TemporalSummarization(Config config) {
		this.config = config;
	}
	
	void run() {
		try {
			this.topics = Topic.load(config);
		} catch (Exception e) {
			System.out.println("Failed to load topics");
			e.printStackTrace();
			System.exit(1);
		}
		
		
		for (Topic topic : topics) {
			runTopic(topic);
		}
	}
	
	void runTopic(Topic topic) {
		SentenceModel model = SentenceModel.load(this.config);
		Search search = new Search(this.config);
		
		int maxHour = Hours.hoursBetween(topic.start,topic.end).getHours();
		
		for (int currHour = 0; currHour < maxHour; currHour++ ) {
			DocumentSet results = search.query(topic, topic.start.plusHours(currHour));
			ArrayList<Sentence> sentences = model.rankSentences(results);
			search.updateModel(model.topTerms());
			this.outputUpdates(topic);
			//Separate system runner from per topic 
		}
	}
	
	void train() {
		
	}
	
	void doSearch() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String configFile = "config.properties";
		if (args.length > 0) {
			configFile = args[0];
		}
		try {
			TemporalSummarization ts = new TemporalSummarization(new Config(configFile));
			ts.run();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

	}

}
