/**
 * @FileName: EmojiUtil.java
 * @Package: com.asura.framework.commons.util
 * @author liusq23
 * @created 2017/7/6 下午10:51
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.framework.commons.util;

/**
 * <p>emoji 表情处理</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @version 1.0
 * @since 1.0
 */
public class EmojiUtil {

    private EmojiUtil() {
    }

    /**
     * 把emoji直接过滤掉
     *
     * @param input
     *
     * @return
     */
    public static String eraseEmojis(String input) {
        if (Check.isNullOrEmpty(input)) {
            return input;
        }
        return input.replaceAll("[^\\u0000-\\uFFFF]", "");
    }
}
