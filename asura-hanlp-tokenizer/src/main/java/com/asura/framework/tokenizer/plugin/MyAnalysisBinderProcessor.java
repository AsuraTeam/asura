/**
 * @FileName: MyBinderProcessor.java
 * @Package: top.swimmer.elasticsearch.HanLP.plugin
 * @author youshipeng
 * @created 2017/2/6 10:50
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.tokenizer.plugin;

import org.elasticsearch.index.analysis.AnalysisModule;

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
public class MyAnalysisBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {

    @Override
    public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer("HanLP", MyAnalyzerProvider.class);
    }

    @Override
    public void processTokenizers(TokenizersBindings tokenizersBindings) {
        tokenizersBindings.processTokenizer("HanLP", MyTokenizerFactory.class);
    }
}