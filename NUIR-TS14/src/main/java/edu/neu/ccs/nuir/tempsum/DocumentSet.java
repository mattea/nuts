/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;

import org.elasticsearch.action.search.SearchResponse;

/**
 * @author mattea
 *
 */
public class DocumentSet {
	SearchResponse results;
	public DocumentSet(SearchResponse results) {
		this.results = results;
	}

}
