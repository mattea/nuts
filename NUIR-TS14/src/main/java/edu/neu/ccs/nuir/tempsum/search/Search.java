package edu.neu.ccs.nuir.tempsum.search;

import java.util.ArrayList;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;
import org.elasticsearch.common.joda.time.format.DateTimeFormatter;
import org.elasticsearch.node.Node;

import static org.elasticsearch.node.NodeBuilder.*;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import edu.neu.ccs.nuir.tempsum.Config;
import edu.neu.ccs.nuir.tempsum.DocumentSet;
import edu.neu.ccs.nuir.tempsum.Topic;

public class Search {
	String index;
	Node node;
	Client client;
	String docbase;
	ArrayList<String> terms;
	DocumentSet lastset;
	DateTime lasthour;
	static DateTimeFormatter datefmt = DateTimeFormat.forPattern("YYYY-MM-dd-HH");
	
	public Search(Config conf) {
		this.index = conf.get("index");
		this.docbase = conf.get("docbase");
		this.terms = new ArrayList<String>();
		node = nodeBuilder().client(true).node();
		client = node.client();
	}
	
	public DocumentSet query(Topic topic, DateTime hour) {
		String doc_type = this.docbase + datefmt.print(hour);
		QueryBuilder query = QueryBuilders.termsQuery("body", this.terms);
		query = QueryBuilders.filteredQuery(query, FilterBuilders.termFilter("source", "news"));
		SearchResponse response = client.prepareSearch(this.index)
				.setTypes(doc_type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(query)
				//.setPostFilter(FilterBuilders.rangeFilter("age").from(12).to(18))
				.setFrom(0).setSize(100).setExplain(true)
				.execute()
				.actionGet();
		// What I want here is a multi-term with diversity, maybe I need to do separate
		// searches and do a sort of federated search for diversity?
		
		DocumentSet docs = new DocumentSet(response);
		this.lastset = docs;
		return docs;
	}
	
	public void updateModel(ArrayList<String> newterms) {
		// TODO: Add documents to text model to find most important terms
		terms.addAll(newterms);
	}
	
	public void close() {
		node.close();
	}
}
