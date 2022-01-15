package com.fuzhengyin.string_appender;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.impl.PsiElementFactoryImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtVisitorVoid;

import java.io.*;

public class ExtractToSpecialDest extends AbstractKotlinInspection {
    MainViewModel mainViewModel = new MainViewModel();

    ExtractToSpecialDest() {

    }

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new KtVisitorVoid() {

            @Override
            public void visitLiteralStringTemplateEntry(@NotNull KtLiteralStringTemplateEntry entry) {
                super.visitLiteralStringTemplateEntry(entry);
                var temp = entry.getParent();
                while (true) {
                    if (temp != null) {
                        if (temp instanceof KtAnnotationEntry) {
                            return;
                        } else if (temp instanceof KtCallExpression) {
                            break;
                        }
                    } else {
                        break;
                    }
                    temp = temp.getParent();
                }
                holder.registerProblem(entry, "Please don't hardcode string!", new LocalQuickFix() {
                    @Override
                    public @IntentionFamilyName @NotNull String getFamilyName() {
                        return "Extract to special xml";
                    }

                    @Override
                    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
                        String text = entry.getText();
                        AppSettingsState instance = AppSettingsState.getInstance();
                        String xmlPath = instance.xmlPath;
                        if (instance.fixProduceType == AppSettingsState.feature_id_type_script_produce && (instance.fixProduceScriptPath == null || instance.fixProduceScriptPath.trim().length() == 0)) {
                            Messages.showMessageDialog("Can't process", "Python Script not Defined", Messages.getErrorIcon());
                            return;
                        }
                        if (xmlPath == null || !new File(xmlPath).exists()) {
                            Messages.showMessageDialog("Can't process", "xml " + xmlPath + " not exists", Messages.getErrorIcon());
                            return;
                        }
                        extractFeatureId(project, instance, mainViewModel);

                        String prefix = mainViewModel.prefix.value;
                        if (prefix == null || prefix.trim().isEmpty()) {
                            Messages.showMessageDialog("Can't process", "Prefix not Defined", Messages.getErrorIcon());
                            return;
                        }

                        mainViewModel.moonDest.setValue(xmlPath);
                        String trans = mainViewModel.trans(text);
                        if (instance.maxWordType == AppSettingsState.max_word_type_word) {
                            if (instance.maxWord != 0) {
                                String[] s = trans.split("_");
                                if (instance.maxWord < s.length) {
                                    diy(project, text, entry);
                                    return;
                                }
                            }

                        } else {
                            if (instance.maxLength != 0) {
                                if (instance.maxLength < trans.length()) {
                                    diy(project, text, entry);
                                    return;
                                }
                            }
                        }
                        work(project, text, entry);
                    }
                });
            }
        };
    }

    private void extractFeatureId(@NotNull Project project, AppSettingsState instance, MainViewModel mainViewModel) {
        if (instance.fixProduceType == AppSettingsState.feature_id_type_fixed_string) {
            mainViewModel.prefix.setValue(instance.prefix);
            mainViewModel.suffix.setValue(instance.suffix);
            return;
        }
        Process process = null;
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            String basePath = project.getBasePath();
            String pythonPath;
            if (instance.pythonPath != null && instance.pythonPath.length() > 0) {
                pythonPath = instance.pythonPath;
            } else pythonPath = "python";
            process = Runtime.getRuntime().exec(new String[]{pythonPath, instance.fixProduceScriptPath, basePath});
            int i = process.waitFor();
            if (i == 0) {
                inputStream = process.getInputStream();
                bufferedInputStream = new BufferedInputStream(inputStream);
                byte[] bytes = bufferedInputStream.readAllBytes();
                String[] split = new String(bytes).trim().split("/");
                if (split.length == 1) {
                    mainViewModel.prefix.setValue(split[0]);
                    mainViewModel.suffix.setValue("");
                } else {
                    mainViewModel.prefix.setValue(split[0]);
                    mainViewModel.suffix.setValue(split[1]);
                }
                return;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        mainViewModel.prefix.setValue(instance.prefix);
        mainViewModel.suffix.setValue(instance.suffix);
    }

    private void diy(@NotNull Project project, String text, @NotNull KtLiteralStringTemplateEntry entry) {
        String key = Messages.showInputDialog(project, "String$length() is to long.(not include feature id)", "Alert", Messages.getQuestionIcon());
        if (key != null) {
            work1(project, text, key, entry);
        }
    }

    private void work(@NotNull Project project, String text, @NotNull KtLiteralStringTemplateEntry entry) {
        mainViewModel.key.setValue(text);
        String key = mainViewModel.start();
        workDetail(project, entry, key);
    }

    private void work1(@NotNull Project project, String text, String k, @NotNull KtLiteralStringTemplateEntry entry) {
        mainViewModel.key.setValue(text);
        String key = mainViewModel.start(k);
        workDetail(project, entry, key);
    }

    private void workDetail(@NotNull Project project, @NotNull KtLiteralStringTemplateEntry entry, String key) {
        if (key.trim().length() == 0) return;
        String stringResource = "getString(" + key + ")";
        PsiStatement statementFromText = new PsiElementFactoryImpl(project).createStatementFromText(stringResource, entry);
        entry.getParent().replace(statementFromText);
    }
}
