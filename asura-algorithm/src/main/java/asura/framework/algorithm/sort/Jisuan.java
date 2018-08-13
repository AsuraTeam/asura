/**
 * @FileName: Jisuan.java
 * @Package asura.framework.algorithm.sort
 * 
 * @author zhangshaobin
 * @created 2015年7月3日 下午1:52:49
 * 
 * Copyright 2011-2015 asura
 */
package asura.framework.algorithm.sort;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * <p>计算</p>
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
public class Jisuan {
	
	/**
	 * 
	 * 查找中间100个数字并计算之和
	 * 
	 * 思路：根据bitset的有效位总数量，获取中间的有效位，然后上下各取50个数字。
	 *
	 * @author zhangshaobin
	 * @created 2015年7月3日 上午9:55:41
	 *
	 * @param bs
	 */
	public static void sum(BitSet bs){
		int lines = bs.cardinality();
		int middleLine = lines/2;
		int startLine = middleLine -50;
		int endLine = middleLine + 50;
		int count = 0;
		long sum = 0;
		List<Integer> list = new ArrayList<Integer>();
		int bs_length = bs.length();
		for (int i=0; i<bs_length;i++) {
			if (bs.get(i)) {
				 count++ ;
				if (startLine<=count && count<endLine) {
					int value = bs.nextSetBit(i);
					list.add(value);
				}
			}
		}
		System.out.println("中间数字列表：" + list.size() + " ： " + list.toString());
		for (Integer item : list) {
			try {
				sum = sum + item;
			} catch (Exception e) {
				System.out.println("我日个去,应该是计算值超出范围了。" + e.getMessage());
			}
		}
		System.out.println("中间100个数字之和为：" + sum);
		
	}

}
