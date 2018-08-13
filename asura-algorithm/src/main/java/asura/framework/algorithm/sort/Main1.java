/**
 * @FileName: Main1.java
 * @Package asura.framework.algorithm.sort
 * 
 * @author zhangshaobin
 * @created 2015年7月3日 上午9:54:18
 * 
 * Copyright 2011-2015 asura
 */
package asura.framework.algorithm.sort;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.BitSet;

/**
 * <p>
 * 			NIO方式读取数据， 可以处理半行拼接
 * 
 * 
 *  有N个500M以上的⼤大纯⽂文本⽂文件(N>10), 每个⽂文件中, 以换⾏行(\n)分隔, 每⾏行⼀一个数字(数字是
	2^32⼤大⼩小以内 ⽆无符号正整数 乱序排列 有重复), 请找出位于数列中间的100个数字之和(去重后的)
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class Main1 {
	
	/**
	 * 
	 * 主程序
	 * 
	 * args[0] = 文件夹路径 C:\\Users\\001\\Downloads\\sort
	 *
	 * @author zhangshaobin
	 * @created 2015年7月3日 上午10:28:57
	 *
	 * @param args 
	 * @throws Exception
	 */
	public static void main(String []args) throws Exception {
		long s = System.currentTimeMillis();
		BitSet bs = new BitSet(Integer.MAX_VALUE);
		String pathFile = "C:\\Users\\001\\Downloads\\sort"; // 文件路径
		File dir = new File(pathFile);
		if (dir.exists()) {
			if (dir.isDirectory()) {
				File [] files = dir.listFiles();
				for (File f : files) {
					System.out.println("---"+f.getAbsolutePath());
					genBitSet(bs, f.getAbsolutePath());
				}
			} else {
				genBitSet(bs, pathFile);
			}
			Jisuan.sum(bs);
		} else {
			System.out.println("老兄,文件夹路径错误!!!");
		}
		
        long e = System.currentTimeMillis();
        System.out.println("耗时："+(e-s));
	}
	
	/**
	 * 
	 * 生成bitset对象
	 *
	 * @author zhangshaobin
	 * @created 2015年7月3日 上午10:01:44
	 *
	 * @return
	 */
	@SuppressWarnings("resource")
	private static BitSet genBitSet(BitSet bs, String path){
		try {
			FileInputStream fin = new FileInputStream(path);  
			FileChannel fcin = fin.getChannel();
			ByteBuffer buffer = ByteBuffer.allocateDirect(1024*1024*1); //10M  可以作为参数传过去 
			String last = "";
			while (true) {  
			    int flag = fcin.read(buffer);  
			    if (flag == -1) { 
			        break;  
			    }  
			   
			    buffer.flip();  
			    String bufferString = toString(buffer);
			    if (null != bufferString && bufferString.length() > 0) {
			    	String [] lines = bufferString.split("\n");
			    	int lines_length = lines.length;
			    	if (null != lines && lines_length > 0) {
			    		for (int i=0; i<lines_length-1; i++) {
			    			if (i==0 && !"".equals(last)) {
			    				bs.set(Integer.valueOf(last+lines[i]),true);
			    				last = "";
			    			} else {
			    				bs.set(Integer.valueOf(lines[i]),true);
			    			}
			    			
			    		}
			    		 if (!bufferString.endsWith("\n")) {
					        	last = lines[lines_length-1];
					      }
			    		
			    	}
			    }
			    buffer.clear();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        return bs;
	}
	
	/**
	 * 
	 * 字节准换成字符
	 *
	 * @author zhangshaobin
	 * @created 2015年7月3日 上午9:59:41
	 *
	 * @param bb
	 * @return
	 */
	private static String toString(ByteBuffer bb) {
	     final byte[] bytes = new byte[bb.remaining()];
	     bb.duplicate().get(bytes);
	     return new String(bytes);
	}
	
}
