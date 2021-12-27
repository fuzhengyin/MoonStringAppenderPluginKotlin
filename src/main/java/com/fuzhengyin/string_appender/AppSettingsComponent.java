// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.fuzhengyin.string_appender;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

    private final JPanel myMainPanel;
    private final JBTextField xmlPathText = new JBTextField();
    private final JBTextField featureIdText = new JBTextField();

    public AppSettingsComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("xml path"), xmlPathText, 1, false)
                .addLabeledComponent(new JBLabel("featureId"),featureIdText, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return xmlPathText;
    }

    @NotNull
    public String getXmlPath() {
        return xmlPathText.getText();
    }

    public void xmlPath(@NotNull String newText) {
        xmlPathText.setText(newText);
    }

    public String getFeatureId() {
        return featureIdText.getText();
    }

    public void featureId(String newStatus) {
        featureIdText.setText(newStatus);
    }

}