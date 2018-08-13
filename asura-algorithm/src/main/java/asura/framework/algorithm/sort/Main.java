/**
 * @FileName: Main.java
 * @Package asura.framework.algorithm.sort
 * 
 * @author zhangshaobin
 * @created 2015年6月25日 下午10:28:18
 * 
 * Copyright 2011-2015 asura
 */
package asura.framework.algorithm.sort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;

/**
 * <p>
 * 
 *  有N个500M以上的⼤大纯⽂文本⽂文件(N>10), 每个⽂文件中, 以换⾏行(\n)分隔, 每⾏行⼀一个数字(数字是
	2^32⼤大⼩小以内 ⽆无符号正整数 乱序排列 有重复), 请找出位于数列中间的100个数字之和(去重后的)
 *  
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
public class Main {
	
	/**
	 * 
	 * 主进程
	 * 
	 * args[0] = 文件路径 C:\\Users\\001\\Downloads\\sort
	 *
	 * @author zhangshaobin
	 * @created 2015年7月3日 下午2:06:58
	 *
	 * @param args 
	 */
	public static void main(String []args) {
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
	 * @created 2015年7月3日 下午1:55:21
	 *
	 * @param bs
	 * @param path
	 * @return
	 */
	private static BitSet genBitSet(BitSet bs, String path){
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				if (!tempString.trim().equals("")) {
					bs.set(Integer.valueOf(tempString.trim()),true);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return bs;
	}
}