import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class Scan{
        public static void main(String[] args) throws IOException {
                Configuration conf=HBaseConfiguration.create();
                  conf.set("hbase.zookeeper.property.clientPort", "2181");
          conf.set("hbase.zookeeper.quorum", "103.233.79.159");
          conf.set("zookeeper.znode.parent", "/hbase-unsecure");

          String start=args[0];
          Long stop=Long.parseLong(start)+60;
     	 HTable table=new HTable(conf,"sampleMqtt");
          Scan scan=new Scan();
          scan.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("Data"));
          scan.setStartRow(Bytes.toBytes(start));
          scan.setStopRow(Bytes.toBytes(String.valueOf(stop)));
          ResultScanner result=table.getScanner(scan);
        String lastrow=null;
          for(Result res:result){
                 // System.out.print("for loop condition");
                 // byte[] val=res.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("Data"));
                //  System.out.println("Row-value:"+Bytes.toString(val));
                  lastrow=Bytes.toString(res.getRow());
                  System.out.println(lastrow);
          }
        //  insert_row(lastrow,"lookup");
          table.close();
        }
}
