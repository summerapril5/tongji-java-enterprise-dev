package com.example.demo.util;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CommitHistoryToolWindowFactory implements ToolWindowFactory, DumbAware {

    private static DefaultTableModel tableModel;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 创建表格模型
        tableModel = new DefaultTableModel(new Object[]{"Time", "Commit Message"}, 0);

        // 创建表格
        JBTable commitTable = new JBTable(tableModel);
        commitTable.setPreferredScrollableViewportSize(new Dimension(500, 200));

        // 创建面板并将表格放入其中
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JBScrollPane(commitTable), BorderLayout.CENTER);

        // 将面板嵌入到 Tool Window
        toolWindow.getComponent().add(panel);
    }

    // 提供一个静态方法用于在执行commit时实时更新表格
    public static void addCommitInfo(String commitMessage) {
        if (tableModel != null) {
            String time = java.time.LocalDateTime.now().toString();
            tableModel.addRow(new Object[]{time, commitMessage});
            System.out.println("table added");
        }
    }
}
