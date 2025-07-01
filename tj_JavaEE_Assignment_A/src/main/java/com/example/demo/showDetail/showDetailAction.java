package com.example.demo.showDetail;

import com.example.demo.util.FileManager;
import com.example.demo.util.JsonManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.ui.Messages; // 导入 Messages 类
import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class showDetailAction extends AnAction {

    private VirtualFile file;

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            Messages.showMessageDialog("No project is open.", "Error", Messages.getErrorIcon());
            return;
        }

        // 获取当前打开的文件
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        VirtualFile[] files = fileEditorManager.getSelectedFiles();
        if (files.length == 0) {
            Messages.showMessageDialog("No file is open.", "Error", Messages.getErrorIcon());
            return;
        }

        VirtualFile selectedFile = files[0];
        file = selectedFile;
        Document document = FileDocumentManager.getInstance().getDocument(selectedFile);
        if (document == null) {
            Messages.showMessageDialog("Failed to get the document for the selected file.", "Error", Messages.getErrorIcon());
            return;
        }

        // 获取当前文件的内容
        String currentVersionCode = document.getText();

        // 创建并显示主窗口
        SwingUtilities.invokeLater(() -> {
            try {
                new VersionComparisonUI(currentVersionCode).setVisible(true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private class VersionComparisonUI extends JFrame {

        private JList<String> versionList;
        private DefaultListModel<String> versionListModel;
        private JTextPane codeArea;
        private JTextPane currentVersionArea;
        private Color textColor; // 声明 textColor 成员变量

        public VersionComparisonUI(String currentVersionCode) throws IOException {
            // 初始化模型
            versionListModel = new DefaultListModel<>();
            ArrayList<String> directoryList = JsonManager.getVersionList(FileManager.getJsonFilePath(file));
            for (String oneDirectory : directoryList) {
                versionListModel.addElement(oneDirectory);
            }

            // 设置全局字体和颜色
            Font globalFont = new Font("Microsoft YaHei", Font.PLAIN, 14);
            Font labelFont = new Font("Microsoft YaHei", Font.BOLD, 18);
            Color backgroundColor = JBColor.background(); // 直接使用 Color 类型
            this.textColor = JBColor.foreground(); // 初始化 textColor 成员变量
            JBColor highlightColor = new JBColor(Gray._60, Gray._120);
            JBColor labelBackgroundColor = new JBColor(Gray._50, Gray._100);

            // 创建导航栏
            versionList = new JList<>(versionListModel);
            versionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            versionList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = versionList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        String selectedVersionCode = null;
                        try {
                            selectedVersionCode = JsonManager.getVersionContent(selectedIndex, FileManager.getJsonFilePath(file));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        displayDifferences(currentVersionCode, selectedVersionCode);
                    }
                }
            });

            // 创建代码详情区
            codeArea = new JTextPane();
            codeArea.setEditable(false);
            JScrollPane codeScrollPane = new JScrollPane(codeArea);

            // 创建当前版本区
            currentVersionArea = new JTextPane();
            currentVersionArea.setEditable(false);
            JScrollPane currentScrollPane = new JScrollPane(currentVersionArea);

            // 创建主面板
            JPanel mainPanel = new JPanel(new BorderLayout());

            // 历史版本区域
            JPanel historyPanel = new JPanel(new BorderLayout());
            historyPanel.setBackground(backgroundColor);
            JLabel historyLabel = new JLabel("历史版本");
            historyLabel.setFont(labelFont);
            historyLabel.setForeground(textColor);
            historyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            historyLabel.setOpaque(true);
            historyLabel.setBackground(labelBackgroundColor); // 直接使用 JBColor
            historyPanel.add(historyLabel, BorderLayout.NORTH);
            historyPanel.add(new JScrollPane(versionList), BorderLayout.CENTER);

            // 选中版本区域
            JPanel selectedPanel = new JPanel(new BorderLayout());
            selectedPanel.setBackground(backgroundColor);
            JLabel selectedLabel = new JLabel("选中版本");
            selectedLabel.setFont(labelFont);
            selectedLabel.setForeground(textColor);
            selectedLabel.setHorizontalAlignment(SwingConstants.CENTER);
            selectedLabel.setOpaque(true);
            selectedLabel.setBackground(labelBackgroundColor); // 直接使用 JBColor
            selectedPanel.add(selectedLabel, BorderLayout.NORTH);
            selectedPanel.add(codeScrollPane, BorderLayout.CENTER);

            // 当前版本区域
            JPanel currentPanel = new JPanel(new BorderLayout());
            currentPanel.setBackground(backgroundColor);
            JLabel currentLabel = new JLabel("当前版本");
            currentLabel.setFont(labelFont);
            currentLabel.setForeground(textColor);
            currentLabel.setHorizontalAlignment(SwingConstants.CENTER);
            currentLabel.setOpaque(true);
            currentLabel.setBackground(labelBackgroundColor); // 直接使用 JBColor
            currentPanel.add(currentLabel, BorderLayout.NORTH);
            currentPanel.add(currentScrollPane, BorderLayout.CENTER);

            // 使用 JSplitPane 分割导航栏和代码详情区
            JSplitPane leftRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, historyPanel, selectedPanel);
            leftRightSplitPane.setOneTouchExpandable(true);
            leftRightSplitPane.setDividerLocation(200); // 设置初始分割位置

            // 使用 JSplitPane 分割代码详情区和当前版本区
            JSplitPane centerRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftRightSplitPane, currentPanel);
            centerRightSplitPane.setOneTouchExpandable(true);
            centerRightSplitPane.setDividerLocation(700); // 设置初始分割位置

            mainPanel.add(centerRightSplitPane, BorderLayout.CENTER);

            // 设置窗口属性
            setTitle("Version Comparison");
            setSize(1200, 900);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            add(mainPanel);
            setLocationRelativeTo(null); // 居中显示

            // 初始显示当前版本
            displayDifferences(currentVersionCode, "");
        }

        private void displayDifferences(String currentVersionCode, String selectedVersionCode) {
            StyledDocument currentDoc = currentVersionArea.getStyledDocument();
            StyledDocument selectedDoc = codeArea.getStyledDocument();

            // 清空文档
            try {
                currentDoc.remove(0, currentDoc.getLength());
                selectedDoc.remove(0, selectedDoc.getLength());
            } catch (BadLocationException e) {
                e.printStackTrace(); // 处理异常
            }

            // 设置默认样式
            SimpleAttributeSet defaultAttr = new SimpleAttributeSet();
            StyleConstants.setFontFamily(defaultAttr, "Microsoft YaHei");
            StyleConstants.setFontSize(defaultAttr, 14);
            StyleConstants.setForeground(defaultAttr, textColor); // 使用当前主题的颜色值

            // 设置差异样式
            SimpleAttributeSet greenAttr = new SimpleAttributeSet();
            StyleConstants.setBackground(greenAttr, new Color(0, 255, 0)); // 绿色背景
            StyleConstants.setFontFamily(greenAttr, "Microsoft YaHei");
            StyleConstants.setFontSize(greenAttr, 14);
            StyleConstants.setForeground(greenAttr, textColor); // 使用当前主题的颜色值

            SimpleAttributeSet redAttr = new SimpleAttributeSet();
            StyleConstants.setBackground(redAttr, new Color(51, 102, 153));
            StyleConstants.setFontFamily(redAttr, "Microsoft YaHei");
            StyleConstants.setFontSize(redAttr, 14);
            StyleConstants.setForeground(redAttr, textColor); // 使用当前主题的颜色值

            // 比较两个版本
            String[] currentLines = currentVersionCode.split("\n");
            String[] selectedLines = selectedVersionCode.split("\n");

            int maxLength = Math.max(currentLines.length, selectedLines.length);
            for (int i = 0; i < maxLength; i++) {
                String currentLine = i < currentLines.length ? currentLines[i] : "";
                String selectedLine = i < selectedLines.length ? selectedLines[i] : "";

                if (currentLine.equals(selectedLine)) {
                    try {
                        currentDoc.insertString(currentDoc.getLength(), currentLine + "\n", defaultAttr);
                        selectedDoc.insertString(selectedDoc.getLength(), selectedLine + "\n", defaultAttr);
                    } catch (BadLocationException e) {
                        e.printStackTrace(); // 处理异常
                    }
                } else {
                    try {
                        currentDoc.insertString(currentDoc.getLength(), currentLine + "\n", redAttr);
                        selectedDoc.insertString(selectedDoc.getLength(), selectedLine + "\n", redAttr);
                    } catch (BadLocationException e) {
                        e.printStackTrace(); // 处理异常
                    }
                }
            }
        }
    }
}