/**
 * 
 */
package edu.neu.ccs.nuir.tempsum.querymodel;

import java.util.ArrayList;

import org.elasticsearch.common.joda.time.DateTime;

import edu.neu.ccs.nuir.tempsum.Config;
import edu.neu.ccs.nuir.tempsum.Sentence;
import edu.neu.ccs.nuir.tempsum.Topic;

/**
 * @author mattea
 *
 */
public class QueryModel {
	Topic topic;
	
	public QueryModel(Topic topic) {
		this.topic = topic;
	}
	
	public void limitSentences(ArrayList<Sentence> sentences, DateTime hour) {
		
	}
	
	public static QueryModel load(Config conf, Topic topic) {
		QueryModel model = new QueryModel(topic);
		return model;
	}
}
