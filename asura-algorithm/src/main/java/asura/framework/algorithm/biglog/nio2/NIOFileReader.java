/**
 * @FileName: NIOFileReader.java
 * @Package asura.framework.algorithm.biglog.nio
 * 
 * @author zhangshaobin
 * @created 2015年7月10日 下午1:34:13
 * 
 * Copyright 2011-2015 asura
 */
package asura.framework.algorithm.biglog.nio2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 		nio方式读取文件
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
public class NIOFileReader {
	
	/**
	 * 线程池
	 */
	private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	
	
	/**
	 * 
	 * nio方式读取数据
	 *
	 * @author zhangshaobin
	 * @created 2015年7月10日 下午2:01:49
	 *
	 * @param readFilePath
	 * @param writerFilePath
	 */
	public static void readByNio(String readFilePath, String writerFilePath){
		File writerFile = new File(writerFilePath);
		FileWriter writer = null;
		if(writerFile.exists()) {
			try {
				writer = new FileWriter(writerFilePath+"/zhangshaobin"  + ".log", true);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("获取filewriter异常!!");
				System.exit(-1);
			}
		} else {
			System.out.println("你妹的,找出来的信息没地方存放!!!");
			System.exit(-1);
		}
		
		
		File file = new File(readFilePath);
		if (file.exists()) {
			FileInputStream fin = null;
			FileChannel fcin = null;
	        try {
				fin = new FileInputStream(file);  
				fcin = fin.getChannel();
				ByteBuffer buffer = ByteBuffer.allocateDirect(1024*1024*1); //10M  
				while (true) {  
				    int flag = fcin.read(buffer);  
				    if (flag == -1) { 
				        break;  
				    }  
				    buffer.flip();  
				    String bufferString = byteBufferToString(buffer);
				    if (null != bufferString && bufferString.length() > 0) {
				    	threadPoolExecutor.execute(new AnalysisThread(bufferString, writer));
				    }
				    buffer.clear();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if (null != fcin) {
					try {
						fcin.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (null != fin) {
					try {
						fin.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		} else {
			System.out.println("你妹的,nginx日志文件不存在!!!");
			System.exit(-1);
		}
        
        while(true) {
//        	System.out.println(threadPoolExecutor.getActiveCount());
        	if(threadPoolExecutor.getActiveCount() == 0){
        		break;  
        	}
        }
	}
	
	/**
	  * 
	  * bytebuffer转换成字符串
	  *
	  * @author zhangshaobin
	  * @created 2015年6月20日 下午8:07:23
	  *
	  * @param bb
	  * @return
	  */
	public static String byteBufferToString(ByteBuffer bb) {
	     final byte[] bytes = new byte[bb.remaining()];
	     bb.duplicate().get(bytes);
	     return new String(bytes);
	}

}
