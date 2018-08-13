/**
 * @FileName: T.java
 * @Package asura.framework.algorithm.biglog.nio
 * 
 * @author zhangshaobin
 * @created 2015年7月11日 下午2:15:22
 * 
 * Copyright 2011-2015 asura
 */
package asura.framework.algorithm.biglog.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class Main {
	
	public static List<String> targets = new ArrayList<String>();
	
	public static final int DEFAULT_READ_SIZE = 1024 * 1024 * 1; //10MB

    private static Map<String, String> ipMap = new HashMap<String, String>();

    private ByteWrap byteWrap;

    public Main() {

    }
    
    public static void main(String[] args) {
        try {
            long t1 = System.currentTimeMillis();
            Main main = new Main();
            main.readFromLogFile("/home/ziroom/A1/data");
//            main.readFromLogFile("C:\\Users\\001\\Downloads\\log\\access_log.log");
            System.out.println(System.currentTimeMillis() - t1);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * 从文件读取数据 以及IP数据，结果写到文件
     *
     * @param logFilePath
     */
    public void readFromLogFile(String logFilePath) throws IOException {
    	genips();
        FileChannel fileChannel = null;
        if (!isFileExist(logFilePath)) {
            throw new FileNotFoundException("File not found:" + logFilePath);
        }
        @SuppressWarnings("resource")
		RandomAccessFile fileAccess = new RandomAccessFile(logFilePath, "r");
        fileChannel = fileAccess.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(DEFAULT_READ_SIZE);
        int length = 0;
        byteBuffer.clear();
        while (length != -1) {
            length = fileChannel.read(byteBuffer);
            handlerByteBuffer(byteBuffer);
        }

    }


    /**
     * 处理ByteBuffer
     *
     * @param byteBuffer
     */
    private void handlerByteBuffer(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        byteBuffer.clear();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == '\n') {
                String str = new String(byteWrap.getBytes(), 0, byteWrap.size()).trim();
                handlerIP(str);
                byteWrap.clear();
            } else {
                if (isNull(byteWrap)) {
                    byteWrap = new ByteWrap(1024 * 1024 * 1);
                }
                byteWrap.addByte(bytes[i]);
            }
        }
    }

    /**
     * 处理IP
     *
     * @param str
     */
    private void handlerIP(String str) throws IOException {
        String[] strs = str.split(" ");
        if (ipMap.get(strs[0]) != null) {
            System.out.println(strs[0] +":"+ strs[5] + "\n");
        }
    }

   
	
	
	public static void genips() {
		ipMap.put("53.199.79.113","53.199.79.113");
		ipMap.put("136.140.92.203","136.140.92.203");
		ipMap.put("104.179.80.12","104.179.80.12");
		ipMap.put("87.216.168.163","87.216.168.163");
		ipMap.put("126.29.129.6","126.29.129.6");
		ipMap.put("15.176.102.150","15.176.102.150");
	}
	
	
	
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isBlankStr(String str) {
        if (isNull(str)) {
            return true;
        }
        if ("".equals(str)) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
	public static boolean isEmptyCollection(Collection collect) {
        if (isNull(collect)) {
            return true;
        }
        if (collect.size() == 0) {
            return true;
        }
        if (collect.contains(null)) {
            return true;
        }
        return false;
    }

    public static boolean isFileExist(String path) {
        if (isNull(path)) return false;
        File file = new File(path);
        if (!file.exists()) return false;
        file = null;
        return true;
    }

    public static boolean isEmptyArray(Object[] objs) {
        if (isNull(objs)) {
            return true;
        }
        if (objs.length == 0) {
            return true;
        }
        return false;
    }

}
