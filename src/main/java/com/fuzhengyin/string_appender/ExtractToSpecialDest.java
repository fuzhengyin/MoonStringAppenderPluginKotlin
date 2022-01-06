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

import java.io.File;

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
                        String xmlPath = AppSettingsState.getInstance().xmlPath;
                        String featureId = AppSettingsState.getInstance().featureId;
                        if (xmlPath == null || !new File(xmlPath).exists()) {
                            Messages.showMessageDialog("Can't process","xml "+ xmlPath +" not exists",Messages.getErrorIcon());
                            return;
                        }
                        if (featureId == null || featureId.trim().isEmpty()) {
                            Messages.showMessageDialog("Can't process", "FeatureId not Exists",Messages.getErrorIcon());
                            return;
                        }
                        mainViewModel.moonDest.setData(xmlPath);
                        mainViewModel.featureId.setData(featureId);

                        mainViewModel.key.setData(text);
                        String key = mainViewModel.start();
                        String text1 = "getString(" + key + ")";
                        PsiStatement statementFromText = new PsiElementFactoryImpl(project).createStatementFromText(text1, entry);
                        entry.getParent().replace(statementFromText);
                    }
                });
            }
        };
    }
}
