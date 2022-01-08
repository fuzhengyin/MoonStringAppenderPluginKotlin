package com.fuzhengyin.string_appender;

import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ItemEvent;
import java.io.File;

public class AppSettingsPanel {
    JBTextField xmlPath;
    JBTextField featuredId;
    JPanel contentPanel;
    JTextField featureScriptPath;
    JRadioButton scriptRadioButton;
    JButton selectXmlPath;
    JButton selectScriptPath;
    JRadioButton maxWordCountRadioButton;
    JSpinner spinnerMaxLength;
    JSpinner spinnerMaxWord;
    JRadioButton maxLengthRadioButton;
    JRadioButton fixedString;
    JTextField pythonPath;

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
                featuredId.setEnabled(false);
            }
        });
        fixedString.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                featureScriptPath.setEnabled(false);
                featuredId.setEnabled(true);
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

    }

    int maxWordType() {
        if (maxWordCountRadioButton.isSelected()) return AppSettingsState.max_word_type_word;
        else return AppSettingsState.max_word_type_length;
    }

    public void setMaxWordType(int type) {
        if (type == AppSettingsState.max_word_type_word) {
            maxWordCountRadioButton.setSelected(true);
        } else {
            maxLengthRadioButton.setSelected(true);
        }
    }

    int featureId() {
        if (fixedString.isSelected()) return AppSettingsState.feature_id_type_fixed_string;
        else return AppSettingsState.feature_id_type_script_produce;
    }

    public void setFeatureIdType(int type) {
        if (type == AppSettingsState.feature_id_type_fixed_string) {
            fixedString.setSelected(true);
        } else {
            scriptRadioButton.setSelected(true);
        }
    }
}
