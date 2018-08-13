/**
 * @FileName: NatureAttribute.java
 * @Package: top.swimmer.elasticsearch.HanLP.core
 * @author youshipeng
 * @created 2017/2/4 16:58
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.tokenizer;

import com.hankcs.hanlp.corpus.tag.Nature;
import org.apache.lucene.util.Attribute;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author youshipeng
 * @version 1.0
 * @since 1.0
 */
public interface NatureAttribute extends Attribute {
    Nature nature();

    void setNature(Nature nature);
}