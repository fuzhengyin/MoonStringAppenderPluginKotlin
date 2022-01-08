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
        featureIdText = appSettingsPanel.featuredId;
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

    public String getFeatureId() {
        return featureIdText.getText();
    }

    public void setFeatureId(String newStatus) {
        featureIdText.setText(newStatus);
    }

    public int getMaxWordType() {
        return appSettingsPanel.maxWordType();
    }

    public String getFeatureScriptPath() {
        return appSettingsPanel.featureScriptPath.getText();
    }

    public int getMaxWord() {
        return (int) appSettingsPanel.spinnerMaxWord.getValue();
    }

    public void setMaxWord(int word) {
        appSettingsPanel.spinnerMaxWord.setValue(word);
        appSettingsPanel.spinnerMaxLength.setValue(word);
    }

    public void setMaxWordType(int type) {
        appSettingsPanel.setMaxWordType(type);
    }

    public int getMaxLength() {
        return (int) appSettingsPanel.spinnerMaxLength.getValue();
    }

    public void setMaxLength(int length) {
        appSettingsPanel.spinnerMaxLength.setValue(length);
    }

    public int getFeatureIdType() {
        return appSettingsPanel.featureId();
    }

    public void setFeatureIdType(int type) {
        appSettingsPanel.setFeatureIdType(type);
    }

    public String getFeatureIdScriptPath() {
        return appSettingsPanel.featureScriptPath.getText();
    }

    public void setFeatureIdScriptPath(String path) {
        appSettingsPanel.featureScriptPath.setText(path);
    }

    public String getPythonPath() {
        return appSettingsPanel.pythonPath.getText();
    }

    public void setPythonPath(String pythonPath) {
        appSettingsPanel.pythonPath.setText(pythonPath);
    }
}