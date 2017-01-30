import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;


public class HbasePut {

        @SuppressWarnings("deprecation")
		public static void main(String[] args) throws IOException
        {

          String dd=null;
          byte[] COL_FAMILY=Bytes.toBytes("cf1");
          byte[] DATA_COL=Bytes.toBytes("Data");
       // String timestamp=obj.get("timestamp").toString();
       // long curr = System.currentTimeMillis();
          Configuration conf=HBaseConfiguration.create();
          conf.set("hbase.zookeeper.property.clientPort", "2181");
          conf.set("hbase.zookeeper.quorum", "103.233.79.152");
          conf.set("zookeeper.znode.parent", "/hbase-unsecure");
          HTable table=new HTable(conf, "sampleMqtt");
          for(int i=0;i<20;i++){
                //  long curr = System.currentTimeMillis();
                  long curr = System.currentTimeMillis();
                  String cur = String.valueOf(curr);

          if(cur!=null){
          Put put=new  Put(Bytes.toBytes(cur));
          put.add(COL_FAMILY, DATA_COL, Bytes.toBytes("abc"));

        // System.out.println(table);
        // System.out.println(put);
          table.put(put);
          dd=cur;
          }

          System.out.println("record inserted");
          }
          System.out.println(dd);
          table.close();


   }

}
