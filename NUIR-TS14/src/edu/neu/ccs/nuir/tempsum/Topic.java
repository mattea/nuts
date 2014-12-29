/**
 * 
 */
package edu.neu.ccs.nuir.tempsum;

import java.io.File;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.DateTimeZone;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author mattea
 *
 */
public class Topic {
	String id;
	String title;
	DateTime start;
	DateTime end;
	String query;
	String type;
	Topic(Element xml) {
		id = ((Node) xml.getElementsByTagName("id").item(0)).getNodeValue();
		title = ((Node) xml.getElementsByTagName("title").item(0)).getNodeValue();
		
		String startstr = ((Node) xml.getElementsByTagName("start").item(0)).getNodeValue();
		start = new DateTime ( Long.parseLong(startstr) * 1000L, DateTimeZone.forID( "UTC" ) );
		
		String endstr = ((Node) xml.getElementsByTagName("end").item(0)).getNodeValue();
		end = new DateTime ( Long.parseLong(endstr) * 1000L, DateTimeZone.forID( "UTC" ) );
		
		query = ((Node) xml.getElementsByTagName("query").item(0)).getNodeValue();
		type = ((Node) xml.getElementsByTagName("type").item(0)).getNodeValue();
	}
	static Topic[] load(Config conf) throws Exception {
		Topic[] topics;

		File file = new File(conf.get("topicsfile"));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();
		NodeList nodeLst = doc.getElementsByTagName("event");
		int ntopics = nodeLst.getLength();

		topics = new Topic[ntopics];

		for (int s = 0; s < ntopics; s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

				topics[s] = new Topic((Element) fstNode);
				
			}

		}
		return topics;
	}
	
}
