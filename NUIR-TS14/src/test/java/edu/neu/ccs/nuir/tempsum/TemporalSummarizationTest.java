package edu.neu.ccs.nuir.tempsum;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import static org.junit.Assert.*;

import java.util.HashMap;

import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.junit.Test;

public class TemporalSummarizationTest {

	@Test
	public void testTemporalSummarization() {
		fail("Not yet implemented");
	}

	@Test
	public void testRun() {
		fail("Not yet implemented");
	}

	public class Cluster implements Runnable {
		Config conf;
		Cluster(Config conf) {
			this.conf = conf;
		}
		public void run() {
			Node node = nodeBuilder().node();
			Client client = node.client();
			AdminClient admin = client.admin();
			HashMap<String,Object> reposettings = new HashMap<String,Object>();
			reposettings.put("location", conf.get("repo_path"));
			reposettings.put("compress", true);
			
			try {
				admin.cluster().preparePutRepository("test_repo").setType("fs").setSettings(reposettings).execute().get();
				admin.cluster().prepareGetSnapshots("test_repo").execute().get();
				admin.cluster().prepareGetSnapshots("test_repo").execute().get();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}
}