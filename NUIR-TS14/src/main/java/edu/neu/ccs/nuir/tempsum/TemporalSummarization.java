/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;

import java.io.PrintStream;

import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.Hours;

import edu.neu.ccs.nuir.tempsum.sentencemodel.SentenceModel;
import edu.neu.ccs.nuir.tempsum.search.Search;
import edu.neu.ccs.nuir.tempsum.topicmodel.TopicModel;


/**
 * @author mattea
 *
 */
public class TemporalSummarization {
	
	Config config;
	Topic[] topics;
	String teamid;
	String runid;
	//SentenceModel model;
	//Search search;
	TemporalSummarization(Config config) {
		this.config = config;
		this.teamid = config.get("teamid");
		this.runid = config.get("runid");
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
//			runTopic(topic);
			new TopicRunner(this, topic).run();
//			(new Thread(new TopicRunner(this, topic))).start();
		}
	}
	
//	void runTopic(Topic topic) {
//		QueryModel querymodel = QueryModel.load(this.config, topic);
//		SentenceModel sentencemodel = SentenceModel.load(this.config);
//		Search search = new Search(this.config);
//		
//		int maxHour = Hours.hoursBetween(topic.start,topic.end).getHours();
//		DateTime currTime;
//		
//		for (int currHour = 0; currHour < maxHour; currHour++ ) {
//			currTime = topic.start.plusHours(currHour);
//			DocumentSet results = search.query(topic, currTime);
//			ArrayList<Sentence> sentences = sentencemodel.rankSentences(results);
//			search.updateModel(sentencemodel.topTerms());
//			querymodel.limitSentences(sentences, currTime);
//			outputSentences(sentences, currTime, System.out);
//			//Separate system runner from per topic 
//		}
//	}
//	
//	void outputSentences(ArrayList<Sentence> sentences) {
//		String hourfmt = time.toString();
//		out.println(hourfmt + "");
//	}
	
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
	
	class TopicRunner implements Runnable {
		TemporalSummarization ts;
		Topic topic;
		TopicModel topicmodel;
		SentenceModel sentencemodel;
		Search search;
		DateTime currTime;
		PrintStream out;
		
		TopicRunner(TemporalSummarization ts, Topic topic, PrintStream out) {
			this.ts = ts;
			this.topic = topic;
			this.out = out;
		}
		
		TopicRunner(TemporalSummarization ts, Topic topic) {
			this(ts, topic, System.out);
		}
		
		public void run() {
			topicmodel = TopicModel.load(ts.config, topic);
			search = new Search(ts.config);
			sentencemodel = SentenceModel.load(ts.config);
			
			int maxHour = Hours.hoursBetween(topic.start,topic.end).getHours();
			
			for (int currHour = 0; currHour < maxHour; currHour++ ) {
				currTime = topic.start.plusHours(currHour);
				DocumentSet results = search.query(topic, currTime);
				ResultSet sentences = sentencemodel.rankSentences(results);
				search.updateModel(sentencemodel.topTerms());
				topicmodel.limitSentences(sentences, currHour);
				outputSentences(sentences);
				sentences.flush();
			}
		}
		
		void outputSentences(ResultSet sentences) {
			// Convert Millis to seconds, then add an hour minus one second as reporting time
			String hourstr = String.valueOf((long)((currTime.getMillis() / 1000)  + 3599));
			String currLine;
			for (Sentence sent : sentences) {
				currLine = topic.id + "\t" + ts.teamid + "\t" + ts.runid + "\t" +
							sent.did + "\t" + sent.id + "\t" + hourstr + "\t" + sent.score;
				out.println(currLine);
			}
			out.flush();
		}
	}

}
