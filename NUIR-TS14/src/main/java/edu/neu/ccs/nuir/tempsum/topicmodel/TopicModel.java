/**
 * 
 */
package edu.neu.ccs.nuir.tempsum.topicmodel;

import java.util.ArrayList;

import org.elasticsearch.common.joda.time.DateTime;

import edu.neu.ccs.nuir.tempsum.Config;
import edu.neu.ccs.nuir.tempsum.ResultSet;
import edu.neu.ccs.nuir.tempsum.Sentence;
import edu.neu.ccs.nuir.tempsum.Topic;

/**
 * @author mattea
 *
 */
public class TopicModel {
	Topic topic;
	TopicClass topicclass;
	double hourweight;
	int sentcount;
	
	public TopicModel(Topic topic, double hourweight) {
		this.topic = topic;
		this.hourweight = hourweight;
		topicclass = classify(topic);
		sentcount = 0;
	}
	
	public TopicModel(Topic topic) {
		this(topic, 1);
	}
	
	public void limitSentences(ResultSet sentences, DateTime hour) {
		//TODO take into account how many sentences have been output, and some potential global number of updates desired
	}
	
	public static TopicModel load(Config conf, Topic topic) {
		TopicModel model = new TopicModel(topic);
		return model;
	}
	
	public TopicClass classify(Topic topic) {
		TopicClass tclass;
		switch (topic.ttype) {
		case "accident":
			tclass = new ShortTopic(topic);
			break;
		case "storm":
			tclass = new LongTopic(topic);
			break;
		case "bombing":
			tclass = new LongTopic(topic);
			break;
		case "riot":
			tclass = new ShortTopic(topic);
			break;
		case "protest":
			tclass = new LongTopic(topic);
			break;
		case "hostage":
			tclass = new LongTopic(topic);
			break;
		case "impact event":
			tclass = new ShortTopic(topic);
			break;
		case "shooting":
			tclass = new BurstyTopic(topic);
			break;
		default:
			tclass = new LongTopic(topic);
			break;
		}
		return tclass;
	}
	
	abstract class TopicClass {
		Topic topic;
		public TopicClass(Topic topic) {
			this.topic = topic;
		}
		public abstract double offsetScore(long hour);
		public double timeOfDayWeight(long hour) {
			double score = hour % 24;
			if (score < 4) {
				score = (score + 1) / 10;
			} else if (score > 22) {
				score = (25 - score) / 10;
			} else {
				score = 1;
			}
			return score;
		}
		public double scoreTime(long hour) {
			return offsetScore(hour) * timeOfDayWeight(hour);
		}
	}
	class ShortTopic extends TopicClass {
		public ShortTopic(Topic topic) {
			super(topic);
		}
		public double offsetScore(long hour) {
			return 1/(Math.log(hour)+1);
		}
	}
	class BurstyTopic extends TopicClass {
		public BurstyTopic(Topic topic) {
			super(topic);
		}
		public double offsetScore(long hour) {
			return 1;
		}
	}
	class LongTopic extends TopicClass {
		public LongTopic(Topic topic) {
			super(topic);
		}
		static final long tendaysinhours = 240;
		public double offsetScore(long hour) {
			return 0.5 + 0.5 * (1- hour/tendaysinhours);
		}
	}
}
