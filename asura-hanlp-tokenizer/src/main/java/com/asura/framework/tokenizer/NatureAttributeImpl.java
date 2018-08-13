/**
 * @FileName: NatureAttributeImpl.java
 * @Package: top.swimmer.elasticsearch.HanLP.core
 * @author youshipeng
 * @created 2017/2/4 17:01
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.tokenizer;

import com.hankcs.hanlp.corpus.tag.Nature;
import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.AttributeReflector;

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
public class NatureAttributeImpl extends AttributeImpl implements NatureAttribute, Cloneable {

    private Nature nature = null;

    @Override
    public Nature nature() {
        return nature;
    }

    @Override
    public void setNature(Nature nature) {
        this.nature = nature;
    }

    @Override
    public void clear() {
        this.nature = null;
    }

    @Override
    public void copyTo(AttributeImpl target) {
        NatureAttribute t = (NatureAttribute) target;
        t.setNature(this.nature);
    }

    public void reflectWith(AttributeReflector reflector) {
//        super.reflectWith(reflector);
        reflector.reflect(NatureAttribute.class, "nature", this.nature.name());
    }
}