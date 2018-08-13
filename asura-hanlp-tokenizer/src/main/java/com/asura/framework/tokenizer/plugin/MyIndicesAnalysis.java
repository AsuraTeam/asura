/**
 * @FileName: MyIndicesAnalysis.java
 * @Package: top.swimmer.elasticsearch.HanLP.plugin
 * @author youshipeng
 * @created 2017/2/6 10:55
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.tokenizer.plugin;

import com.asura.framework.tokenizer.core.MyAnalyzer;
import com.asura.framework.tokenizer.core.MyTokenizer;
import com.asura.framework.tokenizer.core.SegmentationType;
import com.asura.framework.tokenizer.update.HotUpdate;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.analysis.AnalyzerScope;
import org.elasticsearch.index.analysis.PreBuiltAnalyzerProviderFactory;
import org.elasticsearch.index.analysis.PreBuiltTokenizerFactoryFactory;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;

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
public class MyIndicesAnalysis extends AbstractComponent {

    @Inject
    public MyIndicesAnalysis(Settings settings, IndicesAnalysisService indicesAnalysisService, Environment env) {
        super(settings);
        HotUpdate.begin(env);

        // 注册分析器
        indicesAnalysisService.analyzerProviderFactories()
                .put("han_index", new PreBuiltAnalyzerProviderFactory("han_index", AnalyzerScope.GLOBAL, new MyAnalyzer(SegmentationType.index)));

        indicesAnalysisService.analyzerProviderFactories()
                .put("han_search", new PreBuiltAnalyzerProviderFactory("han_search", AnalyzerScope.GLOBAL, new MyAnalyzer(SegmentationType.search)));

        indicesAnalysisService.analyzerProviderFactories()
                .put("han_synonym", new PreBuiltAnalyzerProviderFactory("han_synonym", AnalyzerScope.GLOBAL, new MyAnalyzer(SegmentationType.synonym)));

        // 注册分词器
        indicesAnalysisService.tokenizerFactories().put("han_index", new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
            @Override
            public String name() {
                return "han_index";
            }

            @Override
            public Tokenizer create() {
                return new MyTokenizer(SegmentationType.index);
            }
        }));

        indicesAnalysisService.tokenizerFactories().put("han_search", new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
            @Override
            public String name() {
                return "han_search";
            }

            @Override
            public Tokenizer create() {
                return new MyTokenizer(SegmentationType.search);
            }
        }));

        indicesAnalysisService.tokenizerFactories().put("han_synonym", new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
            @Override
            public String name() {
                return "han_synonym";
            }

            @Override
            public Tokenizer create() {
                return new MyTokenizer(SegmentationType.synonym);
            }
        }));
    }
}