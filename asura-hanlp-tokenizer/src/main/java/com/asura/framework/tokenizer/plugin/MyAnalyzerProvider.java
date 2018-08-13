/**
 * @FileName: MyAnalyzerProvider.java
 * @Package: top.swimmer.elasticsearch.HanLP.plugin
 * @author youshipeng
 * @created 2017/2/6 10:48
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.tokenizer.plugin;

import com.asura.framework.tokenizer.core.MyAnalyzer;
import com.asura.framework.tokenizer.core.SegmentationType;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;

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
public class MyAnalyzerProvider extends AbstractIndexAnalyzerProvider<MyAnalyzer> {

    @Inject
    public MyAnalyzerProvider(Index index, Settings indexSettings, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
    }

    @Override
    public MyAnalyzer get() {
        return new MyAnalyzer(SegmentationType.search);
    }
}