import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;


public class BulkLoad {
//
	public static class Map extends Mapper<LongWritable, Text, ImmutableBytesWritable,Put>{
	public final	byte[]family=Bytes.toBytes("User-details");
	public final	byte[]NameCol=Bytes.toBytes("name");
	public final	byte[]CityCol=Bytes.toBytes("city");
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line=value.toString();
			String [] parts=line.split(",");
			byte[] rowkey=Bytes.toBytes(parts[0]);
			byte[]name=Bytes.toBytes(parts[1]);
			byte[]city=Bytes.toBytes(parts[2]);
			Put put=new Put(rowkey);
			put.add(family, NameCol, name);
			put.add(family, CityCol, city);
			context.write(new ImmutableBytesWritable(rowkey), put);
			
		}
	}
		public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable>{
			@Override
			protected void reduce(Text key, Iterable<IntWritable> values,
					Context context)throws IOException, InterruptedException {
				
				int sum=0;
				for(IntWritable x:values){
					sum+=x.get();
				}
				context.write(key, new IntWritable(sum));
			}
		}
		
		public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
			Configuration conf=HBaseConfiguration.create();
			String tablename=args[1];
			conf.set("hbase.mapred.outputtable", tablename);
			Job job=new Job(conf);
			job.setJarByClass(BulkLoad.class);
			job.setMapperClass(Map.class);
			job.setReducerClass(Reduce.class);
			job.setInputFormatClass(TextInputFormat.class);
			job.setOutputKeyClass(ImmutableBytesWritable.class);
			job.setOutputValueClass(Writable.class);
			//job.setOutputFormatClass(TableOutputFormat.class);
		//	TableMapReduceUtil.initTableMapperJob(scans, mapper, outputKeyClass, outputValueClass, job);
			job.setNumReduceTasks(0);
			TextInputFormat.addInputPath(job, new Path(args[0]));
			TableMapReduceUtil.initTableReducerJob(tablename, null, job);
			System.exit(job.waitForCompletion(true)?1:0);
			
		}
	

}
