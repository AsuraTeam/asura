/**
 * @FileName: CodeCreatePanel.java
 * @Package: com.asura.framework.generator.panel
 * @author youshipeng
 * @created 2017/2/22 10:05
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.generator.panel;

import com.asura.framework.generator.replace.Replacer;
import com.asura.framework.generator.utils.PropertiesUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static com.asura.framework.generator.utils.IOUtils.*;

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
public class CodeCreatePanel extends AbstractPanel {

    private int width;
    private int height;

    private String replaceUrl = getResource(PropertiesUtils.get("replace.url", "replace.txt"));

    public CodeCreatePanel(int width, int height) {
        this.width = width;
        this.height = height;
        initDetails();
    }

    private void initDetails() {
        this.setSize(width, height);
        this.setBackground(Color.gray);
        this.setLayout(null);
        build();
    }

    private void build() {
        final TextArea propertiesArea = new TextArea();
        propertiesArea.setBounds(20, 20, 620, 660);
        propertiesArea.setText(read(replaceUrl));

        JButton buildBtn = new JButton("生成");
        buildBtn.setBounds(380, 700, 100, 60);
        buildBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                write(replaceUrl, propertiesArea.getText());
                Replacer.go();
            }
        });

        JButton saveBtn = new JButton("保存");
        saveBtn.setBounds(500, 700, 100, 60);
        saveBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                write(replaceUrl, propertiesArea.getText());
            }
        });

        this.add(propertiesArea);
        this.add(buildBtn);
        this.add(saveBtn);
    }
}