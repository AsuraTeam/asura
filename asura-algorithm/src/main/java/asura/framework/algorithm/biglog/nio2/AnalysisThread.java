/**
 * @FileName: AnalysisThread.java
 * @Package asura.framework.algorithm.biglog.nio
 * 
 * @author zhangshaobin
 * @created 2015年7月10日 下午1:10:19
 * 
 * Copyright 2011-2015 asura
 */
package asura.framework.algorithm.biglog.nio2;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 		分析处理线程, 主要把每次读取的内容分析出包含的ip，并写入文件中。
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
public class AnalysisThread implements Runnable {

	/**
	 * 每次读取的数据
	 */
	private String bufferString = null;
	private List<String> ips = FindTarget.targets;
	private FileWriter writer = null;

	public AnalysisThread(String sb, FileWriter writer) {
		this.bufferString = sb;
		this.writer = writer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		String last = "";
		String[] lines = bufferString.split("\n");
		int lines_length = lines.length;
		for (String ip : ips) {
			if (bufferString.contains(ip)) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < lines_length - 1; i++) {
					String line = "";
					if (i == 0 && !"".equals(last)) {
						line = last + lines[i];
						last = "";
					} else {
						line = lines[i];
					}
					if (line.contains(ip)) {
						sb.append(line).append("\n");
					}
				}
				if (!bufferString.endsWith("\n")) {
					last = lines[lines_length - 1];
				}
				if (null != sb && sb.length() > 0) {
					try {
						String[] strs = sb.toString().split(" ");
						System.out.println(strs[0] +":"+ strs[5] + "\n");
						writer.write(sb.toString());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
