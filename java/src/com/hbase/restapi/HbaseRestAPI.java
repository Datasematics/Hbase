import java.io.IOException;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.rest.client.Client;
import org.apache.hadoop.hbase.rest.client.Cluster;
import org.apache.hadoop.hbase.rest.client.RemoteHTable;
import org.apache.hadoop.hbase.util.Bytes;



public class HbaseRestAPI {
        public static void main(String[] args) throws IOException {
//Set up a cluster list adding all known REST server hosts
                Cluster cluster = new Cluster();
                cluster.add("localhost", 8080);
//Create the client handling the HTTP communication.
                Client client = new Client(cluster);
//Create a remote table instance, wrapping the REST access into a normal client.
                RemoteHTable table = new RemoteHTable(client, "rest_table");
//After getting the connection Perform a get() operation as if it were a direct HBase connection.
                Get get = new Get(Bytes.toBytes("row-1"));
                get.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("testcol1"));
                Result result1 = table.get(get);
                System.out.println("Get result1: " + result1);
//Scan the table; again, this is the same approach as if using the native Java API.
                Scan scan = new Scan();
                scan.setStartRow(Bytes.toBytes("row-1"));
                scan.setStopRow(Bytes.toBytes("row-3"));
                scan.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("testcol1"));
                ResultScanner scanner = table.getScanner(scan);
                for (Result result2 : scanner) {
                System.out.println("Scan row[" + Bytes.toString(result2.getRow()) +
                "]: " + result2);
                }
        }

}
