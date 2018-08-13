/**
 * @FileName: FindTarget.java
 * @Package asura.framework.algorithm.biglog.nio
 * 
 * @author zhangshaobin
 * @created 2015年7月10日 下午1:24:20
 * 
 * Copyright 2011-2015 asura
 */
package asura.framework.algorithm.biglog.nio2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 		查找目标
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
public class FindTarget {

	/**
	 * 被查找的内容
	 */
	public static List<String> targets = new ArrayList<String>();

	/**
	 * 
	 * 读取查找目标，并放入list中
	 *
	 * @author zhangshaobin
	 * @created 2015年6月20日 下午8:26:10
	 *
	 * @return
	 */
	public static void readFileByLines(String targetFilePath) {
		File file = new File(targetFilePath);
		if (file.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					targets.add(tempString.trim());
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
				if (targets.isEmpty()) {
					System.out.println("你妹的,到底查询什么???");
					System.exit(-1);
				}
			}
		} else {
			System.out.println("你妹的,读取查找目标文件不存在!!!");
			System.exit(-1);
		}
	}
	
	public static void genips() {
		targets.add("53.199.79.113");
		targets.add("136.140.92.203");
		targets.add("87.216.168.163");
		targets.add("104.179.80.12");
		targets.add("126.29.129.6");
		targets.add("15.176.102.150");
	}

}
