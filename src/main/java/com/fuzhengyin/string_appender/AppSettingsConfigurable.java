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
        modified |= !mySettingsComponent.getPrefix().equals(settings.prefix);
        modified |= !mySettingsComponent.getFeatureIdScriptPath().equals(settings.fixProduceScriptPath);
        modified |= mySettingsComponent.getFixProduceType() != settings.fixProduceType;
        modified |= mySettingsComponent.getMaxWordType() != settings.maxWordType;
        modified |= mySettingsComponent.getMaxWord() != settings.maxWord;
        modified |= mySettingsComponent.getMaxLength() != settings.maxLength;
        modified |= !mySettingsComponent.getPythonPath().equals(settings.pythonPath);
        modified |= !mySettingsComponent.getSuffix().equals(settings.suffix);
        return modified;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.xmlPath = mySettingsComponent.getXmlPath();
        settings.prefix = mySettingsComponent.getPrefix();
        settings.fixProduceType = mySettingsComponent.getFixProduceType();
        settings.fixProduceScriptPath = mySettingsComponent.getFeatureScriptPath();
        settings.maxWordType = mySettingsComponent.getMaxWordType();
        settings.maxWord = mySettingsComponent.getMaxWord();
        settings.maxLength = mySettingsComponent.getMaxLength();
        settings.pythonPath = mySettingsComponent.getPythonPath();
        settings.suffix = mySettingsComponent.getSuffix();
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        mySettingsComponent.setXmlPath(settings.xmlPath);
        mySettingsComponent.setPrefix(settings.prefix);
        mySettingsComponent.setFixProduceType(settings.fixProduceType);
        mySettingsComponent.setFixProduceScriptPath(settings.fixProduceScriptPath);
        mySettingsComponent.setPythonPath(settings.pythonPath);
        mySettingsComponent.setKeyRestrictType(settings.maxWordType);
        mySettingsComponent.setMaxWord(settings.maxWord);
        mySettingsComponent.setMaxLength(settings.maxLength);
        mySettingsComponent.setSuffix(settings.suffix);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}