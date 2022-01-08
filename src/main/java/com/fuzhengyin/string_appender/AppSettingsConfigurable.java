// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.fuzhengyin.string_appender;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Provides controller functionality for application settings.
 */
public class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent mySettingsComponent;

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Extract To Special Xml";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new AppSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        AppSettingsState settings = AppSettingsState.getInstance();
        boolean modified = !mySettingsComponent.getXmlPath().equals(settings.xmlPath);
        modified |= !mySettingsComponent.getFeatureId().equals(settings.featureId);
        modified |= !mySettingsComponent.getFeatureIdScriptPath().equals(settings.featureScriptPath);
        modified |= mySettingsComponent.getFeatureIdType() != settings.featureProduceType;
        modified |= mySettingsComponent.getMaxWordType() != settings.maxWordType;
        modified |= mySettingsComponent.getMaxWord() != settings.maxWord;
        modified |= mySettingsComponent.getMaxLength() != settings.maxLength;
        modified |= !mySettingsComponent.getPythonPath().equals(settings.pythonPath);
        return modified;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.xmlPath = mySettingsComponent.getXmlPath();
        settings.featureId = mySettingsComponent.getFeatureId();
        settings.featureProduceType = mySettingsComponent.getFeatureIdType();
        settings.featureScriptPath = mySettingsComponent.getFeatureScriptPath();
        settings.maxWordType = mySettingsComponent.getMaxWordType();
        settings.maxWord = mySettingsComponent.getMaxWord();
        settings.maxLength = mySettingsComponent.getMaxLength();
        settings.pythonPath = mySettingsComponent.getPythonPath();
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        mySettingsComponent.setXmlPath(settings.xmlPath);
        mySettingsComponent.setFeatureId(settings.featureId);
        mySettingsComponent.setFeatureIdType(settings.featureProduceType);
        mySettingsComponent.setFeatureIdScriptPath(settings.featureScriptPath);
        mySettingsComponent.setMaxWord(settings.maxWord);
        mySettingsComponent.setMaxWordType(settings.maxWordType);
        mySettingsComponent.setMaxLength(settings.maxLength);
        mySettingsComponent.setPythonPath(settings.pythonPath);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}