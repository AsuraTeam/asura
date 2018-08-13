/**
 * @FileName: AnalysisPlugin.java
 * @Package: top.swimmer.elasticsearch.HanLP.plugin
 * @author youshipeng
 * @created 2017/2/4 18:03
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.tokenizer.plugin;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.Plugin;

import java.util.Collection;
import java.util.Collections;

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
public class MyAnalysisPlugin extends Plugin {

    public static final String PLUGIN_NAME = "HanLP";

    @Override
    public String name() {
        return PLUGIN_NAME;
    }

    @Override
    public String description() {
        return "基于HanLP自然语言处理包实现的elasticsearch分词器。";
    }

    @Override
    public Collection<Module> nodeModules() {
        return Collections.<Module>singletonList(new MyIndicesAnalysisModule());
    }

    public void onModule(AnalysisModule module) {
        module.addProcessor(new MyAnalysisBinderProcessor());
    }
}