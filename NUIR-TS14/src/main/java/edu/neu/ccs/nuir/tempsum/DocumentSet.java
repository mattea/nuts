/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;

import java.util.HashMap;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

/**
 * @author mattea
 *
 */
public class DocumentSet {
	SearchResponse results;
	HashMap<String,SearchHit> hits;
	
	public DocumentSet(SearchResponse results) {
		this.results = results;
		hits = new HashMap<String,SearchHit>();
		for (SearchHit hit: results.getHits()) {
			hits.put((String) hit.field("stream_id").value(), hit);
		}
	}
	
	public ResultSet extractSentences() {
		ResultSet sents = new ResultSet();
		for (SearchHit hit: results.getHits()) {
			int sid = 0;
			for(Object val: hit.field("sentences").getValues()) {
				sents.add(new Sentence(sid++, hit, (String) val));
			}
		}
		return sents;
	}
}