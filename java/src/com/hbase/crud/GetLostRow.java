import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class LastRow {
	public static void main(String[] args) throws IOException {
		HTable table=connect_table("sampleMqtt");
    	Scan scan = new Scan();
    	scan.setReversed(true);
    	scan.setMaxResultSize(1);
    	ResultScanner scanner = table.getScanner(scan);
    	Result lastrow = scanner.next();
    	System.out.println(Bytes.toString(lastrow.getRow()));
    	scanner.close();
	}
	@SuppressWarnings("deprecation")
	public static HTable connect_table(String tablename) throws IOException{
        Configuration conf=HBaseConfiguration.create();
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "103.233.79.152");
        conf.set("zookeeper.znode.parent", "/hbase-unsecure");

         return new HTable(conf, tablename);
    	
    } 

}

