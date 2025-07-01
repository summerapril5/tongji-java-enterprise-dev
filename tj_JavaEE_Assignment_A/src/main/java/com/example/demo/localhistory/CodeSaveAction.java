package com.example.demo.localhistory;

import com.example.demo.data.AllData;
import com.example.demo.util.FileToJson;
import com.example.demo.util.ProjectManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

public class CodeSaveAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = ProjectManager.getProject();
        VirtualFile[] contentRoots = ProjectRootManager.getInstance(project).getContentRoots();
        for (VirtualFile root : contentRoots) {
            try {
                FileToJson.traverseDirectory(root, "", 1);
            } catch (IOException a) {
                throw new RuntimeException(a);
            }
        }
        Messages.showMessageDialog(e.getProject(), "代码版本保存成功！", "保存", Messages.getInformationIcon());
    }
}
