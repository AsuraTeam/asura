/**
 * @FileName: Main.java
 * @Package asura.framework.algorithm.biglog.nio
 * 
 * @author zhangshaobin
 * @created 2015年7月10日 下午2:03:37
 * 
 * Copyright 2011-2015 asura
 */
package asura.framework.algorithm.biglog.nio2;

/**
 * <p>启动</p>
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
	 * args[1] = nginx日志文件 C:\\Users\\001\\Downloads\\log\\access_log.log
	 * args[2] = 查找结果存放文件目录C:\\Users\\001\\Downloads\\log\\fenge
	 *
	 * @author zhangshaobin
	 * @created 2015年7月10日 下午2:04:02
	 *
	 * @param args
	 */
	public static void main(String... args){
		long s = System.currentTimeMillis();
//		FindTarget.readFileByLines(args[0]);
		FindTarget.genips();
		NIOFileReader.readByNio(args[0], args[1]);
		long e = System.currentTimeMillis();
		System.out.println("耗时：" + (e - s));
		System.exit(-1);
	}

}
