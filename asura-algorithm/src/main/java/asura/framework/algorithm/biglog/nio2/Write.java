/**
 * @FileName: Write.java
 * @Package asura.framework.algorithm.biglog
 * 
 * @author zhangshaobin
 * @created 2015年6月20日 下午6:52:51
 * 
 * Copyright 2011-2015 asura
 */
package asura.framework.algorithm.biglog.nio2;

import java.io.FileWriter;
import java.io.IOException;

/**
 * <p>TODO</p>
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
	        try {
	            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
	            FileWriter writer = new FileWriter("C:\\Users\\001\\Downloads\\log\\access_log.log", true);
	            for (int i=0; i<10000000; i++) {
	                String content = "87.216.168.163 - - [14/Jun/2015:05:00:05 +0800] \u6211\u7231\u4f60\u4e2d\u56fd'GET /ops/?action=getServerSystemTraffic&create=1&server=172.16.4.197 HTTP/1.1' 200";
	                writer.write(content+"\n");
	            }
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  
	  

}
