/**
 * @FileName: AbstractFrame.java
 * @Package: com.asura.framework.generator.tooltip
 * @author youshipeng
 * @created 2017/2/21 15:21
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.generator.tooltip;

import com.asura.framework.generator.panel.AbstractPanel;

import javax.swing.*;
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
public abstract class AbstractFrame extends JFrame {

    protected abstract AbstractPanel getMainPanel();

    protected abstract Rectangle getRectangle();

    protected abstract LayoutManager buildLayout();

    public void startup() {
        this.setBounds(getRectangle());
        this.setLocationRelativeTo(null);
        this.setContentPane(getMainPanel());
        this.setLayout(buildLayout());
        this.setResizable(false);
        view();
    }

    public void hidden() {
        this.setVisible(false);
    }

    public void view() {
        this.setVisible(true);
    }

}