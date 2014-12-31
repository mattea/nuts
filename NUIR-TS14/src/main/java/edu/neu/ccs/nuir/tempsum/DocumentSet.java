/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;

import java.util.HashMap;

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
			hits.put(hit.getId(), hit);
		}
	}
}