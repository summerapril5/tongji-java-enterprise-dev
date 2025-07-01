package com.example.demo.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class FileManager {

    // 获取本项目中定义的相对路径 (根据文件对象)
    static public String getRelativePath(VirtualFile file) {
        Project project = ProjectManager.getProject();
        VirtualFile[] contentRoots = ProjectRootManager.getInstance(project).getContentRoots();

        VirtualFile baseDir = contentRoots[0];
        Path basePath = Paths.get(baseDir.getPath());
        Path filePath = Paths.get(file.getPath()).getParent();
        Path relativePath = basePath.relativize(filePath);

        return relativePath.toString();
    }

    // 获取本项目中定义的相对路径 (根据路径对象)
    static public String getRelativePath(Path parentPath) {
        Project project = ProjectManager.getProject();
        VirtualFile[] contentRoots = ProjectRootManager.getInstance(project).getContentRoots();

        VirtualFile baseDir = contentRoots[0];
        Path basePath = Paths.get(baseDir.getPath());
        Path relativePath = basePath.relativize(parentPath);

        return relativePath.toString();
    }

    // 判断是否为Java文件
    static public boolean isJavaFile(VirtualFile file) {
        if (file == null || file.isDirectory()) {
            return false;
        }
        String fileName = file.getName();
        return fileName.endsWith(".java");
    }

    // 获取文件对应的json路径
    static public Path getJsonFilePath(VirtualFile file){
        Path codeHistoryDir = ProjectManager.getPluginPrivateDir();

        String fileNameWithoutExtension = file.getNameWithoutExtension();
        String jsonFileName = fileNameWithoutExtension + '_'+ FileManager.getRelativePath(file).replace("\\", "") + ".json";
        return codeHistoryDir.resolve(jsonFileName);
    }

    // 修改量计算，返回文件的修改百分比
    static public double getChangeAmount(VirtualFile file) throws IOException {
        // 获取文件的当前内容，并按行分割
        String newContent = new String(file.contentsToByteArray(), StandardCharsets.UTF_8);
        List<String> newContentLines = new ArrayList<>(List.of(newContent.split("\n")));

        // 获取文件的旧内容，并按行分割
        String oldContent = JsonManager.getLatestContent(getJsonFilePath(file));  // 确保此方法返回文件的旧内容
        List<String> oldContentLines = new ArrayList<>(List.of(oldContent.split("\n")));

        // 计算差异
        int totalLines = oldContentLines.size();
        int changes = calculateChanges(oldContentLines, newContentLines);

        // 如果旧文件为空，则防止除零错误
        if (totalLines == 0) {
            return 100.0;  // 当旧文件没有内容时，认为是100%变化
        }

        // 计算变更百分比
        return (double) changes / totalLines * 100.0;
    }

    // 辅助方法计算两个文本行列表的差异
    static int calculateChanges(List<String> oldLines, List<String> newLines) {
        int changes = 0;
        int maxIndex = Math.max(oldLines.size(), newLines.size());

        for (int i = 0; i < maxIndex; i++) {
            String oldLine = i < oldLines.size() ? oldLines.get(i) : "";
            String newLine = i < newLines.size() ? newLines.get(i) : "";

            if (!oldLine.equals(newLine)) {
                changes++;
            }
        }

        return changes;
    }
}
