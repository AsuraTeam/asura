/**
 * @FileName: MainFrame.java
 * @Package: com.asura.framework.generator.tooltip
 * @author youshipeng
 * @created 2017/2/21 15:23
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.generator.tooltip;

import com.asura.framework.generator.panel.AbstractPanel;
import com.asura.framework.generator.panel.CodeCreatePanel;

import java.awt.*;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author youshipeng
 * @since 1.0
 * @version 1.0
 */
public class CodeFrame extends AbstractFrame {

    private static final int X = 0;
    private static final int Y = 0;
    private static final int HEIGHT = 800;
    private static final int WIDTH = 660;

    @Override
    protected AbstractPanel getMainPanel() {
        return new CodeCreatePanel(WIDTH, HEIGHT);
    }

    @Override
    protected Rectangle getRectangle() {
        return new Rectangle(X, Y, WIDTH, HEIGHT);
    }

    @Override
    protected LayoutManager buildLayout() {
        return new BorderLayout();
    }
}