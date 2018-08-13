/**
 * @FileName: ISmsReceiver.java
 * @Package: com.asrua.framework.sms.send
 * @author sence
 * @created 12/3/2015 10:45 AM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.asrua.framework.sms.send;

import com.asura.framework.base.entity.DataTransferObject;

import java.io.IOException;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @since 1.0
 * @version 1.0
 */
public interface ISmsReceiver {

    DataTransferObject receive() throws IOException;

}
