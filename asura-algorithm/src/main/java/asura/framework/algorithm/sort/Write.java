/**
 * @FileName: Write.java
 * @Package asura.framework.algorithm.biglog
 * 
 * @author zhangshaobin
 * @created 2015年6月20日 下午6:52:51
 * 
 * Copyright 2011-2015 asura
 */
package asura.framework.algorithm.sort;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * <p>造测试数据</p>
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
public class Write {
	
	  public static void main(String[] args) {
		  for (int j=0; j<20; j++) {
     		 // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
	            try {
					FileWriter writer = new FileWriter("C:\\Users\\001\\Downloads\\sort\\sort"+j+".log", true);
					for (int i=0; i<100000000; i++) {
					    Random r = new Random();
					    int i1 = r.nextInt(Integer.MAX_VALUE);
					    String content = i1+"\n";
					    writer.write(content);
					}
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
     	}
	    }
	  
	  

}
