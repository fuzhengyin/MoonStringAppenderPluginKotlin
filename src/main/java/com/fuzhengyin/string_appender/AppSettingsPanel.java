package com.fuzhengyin.string_appender;

import com.intellij.ui.JBIntSpinner;
import com.intellij.ui.components.JBOptionButton;
import com.intellij.ui.components.JBOptionButtonKt;
import com.intellij.ui.components.JBRadioButton;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ItemEvent;
import java.io.File;

public class AppSettingsPanel {
    JBTextField xmlPath;
    JBTextField prefixTextField;
    JPanel contentPanel;
    JBTextField featureScriptPath;
    JBRadioButton scriptRadioButton;
    JButton selectXmlPath;
    JButton selectScriptPath;
    JBRadioButton maxWordCountRadioButton;
    JBIntSpinner spinnerMaxLength;
    JBIntSpinner spinnerMaxWord;
    JBRadioButton maxLengthRadioButton;
    JBRadioButton fixedString;
    JBTextField pythonPath;
    JBTextField suffixTextField;

    public AppSettingsPanel() {

        maxLengthRadioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                spinnerMaxLength.setEnabled(true);
                spinnerMaxWord.setEnabled(false);
            }
        });
        maxWordCountRadioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                spinnerMaxWord.setEnabled(true);
                spinnerMaxLength.setEnabled(false);
            }
        });
        scriptRadioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                featureScriptPath.setEnabled(true);
                prefixTextField.setEnabled(false);
                suffixTextField.setEnabled(false);
            }
        });
        fixedString.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                featureScriptPath.setEnabled(false);
                prefixTextField.setEnabled(true);
                suffixTextField.setEnabled(true);
            }
        });
        selectXmlPath.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getAbsolutePath().endsWith(".xml");
                }

                @Override
                public String getDescription() {
                    return null;
                }
            });
            int i = jFileChooser.showOpenDialog(contentPanel);
            if (i == JFileChooser.APPROVE_OPTION) {
                xmlPath.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        selectScriptPath.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getAbsolutePath().endsWith(".py");
                }

                @Override
                public String getDescription() {
                    return null;
                }
            });
            int i = jFileChooser.showOpenDialog(contentPanel);
            if (i == JFileChooser.APPROVE_OPTION) {
                featureScriptPath.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }

    private void createUIComponents() {
        spinnerMaxLength = new JBIntSpinner(0, 0, 100);
        spinnerMaxWord = new JBIntSpinner(0, 0, 100);
    }

    int keyRestrictType() {
        if (maxWordCountRadioButton.isSelected()) return AppSettingsState.max_word_type_word;
        else return AppSettingsState.max_word_type_length;
    }

    public void setKeyRestrictType(int type) {
        if (type == AppSettingsState.max_word_type_word) {
            maxWordCountRadioButton.setSelected(true);
        } else {
            maxLengthRadioButton.setSelected(true);
        }
    }

    int fixProduceType() {
        if (fixedString.isSelected()) return AppSettingsState.feature_id_type_fixed_string;
        else return AppSettingsState.feature_id_type_script_produce;
    }

    public void setFixProduceType(int type) {
        if (type == AppSettingsState.feature_id_type_fixed_string) {
            fixedString.setSelected(true);
        } else {
            scriptRadioButton.setSelected(true);
        }
    }
}
