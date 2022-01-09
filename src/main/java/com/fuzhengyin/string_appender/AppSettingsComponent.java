// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.fuzhengyin.string_appender;

import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

    private final JPanel myMainPanel;
    private final JBTextField xmlPathText;
    private final JBTextField featureIdText;
    private final AppSettingsPanel appSettingsPanel;

    public AppSettingsComponent() {
        appSettingsPanel = new AppSettingsPanel();
        myMainPanel = appSettingsPanel.contentPanel;
        xmlPathText = appSettingsPanel.xmlPath;
        featureIdText = appSettingsPanel.prefixTextField;
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

    public void setXmlPath(@NotNull String newText) {
        xmlPathText.setText(newText);
    }

    public String getPrefix() {
        return featureIdText.getText();
    }

    public void setPrefix(String newStatus) {
        featureIdText.setText(newStatus);
    }

    public int getMaxWordType() {
        return appSettingsPanel.keyRestrictType();
    }

    public String getFeatureScriptPath() {
        return appSettingsPanel.featureScriptPath.getText();
    }

    public int getMaxWord() {
        return (int) appSettingsPanel.spinnerMaxWord.getValue();
    }

    public void setMaxWord(int word) {
        appSettingsPanel.spinnerMaxWord.setValue(word);
    }

    public void setKeyRestrictType(int type) {
        appSettingsPanel.setKeyRestrictType(type);
    }

    public int getMaxLength() {
        return (int) appSettingsPanel.spinnerMaxLength.getValue();
    }

    public void setMaxLength(int length) {
        appSettingsPanel.spinnerMaxLength.setValue(length);
    }

    public int getFixProduceType() {
        return appSettingsPanel.fixProduceType();
    }

    public void setFixProduceType(int type) {
        appSettingsPanel.setFixProduceType(type);
    }

    public String getFeatureIdScriptPath() {
        return appSettingsPanel.featureScriptPath.getText();
    }

    public void setFixProduceScriptPath(String path) {
        appSettingsPanel.featureScriptPath.setText(path);
    }

    public String getPythonPath() {
        return appSettingsPanel.pythonPath.getText();
    }

    public void setPythonPath(String pythonPath) {
        appSettingsPanel.pythonPath.setText(pythonPath);
    }

    public String getSuffix() {
        return appSettingsPanel.suffixTextField.getText();
    }

    public void setSuffix(String suffix) {
        appSettingsPanel.suffixTextField.setText(suffix);
    }
}