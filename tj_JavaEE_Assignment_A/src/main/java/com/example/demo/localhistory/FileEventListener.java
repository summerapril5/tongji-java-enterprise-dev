package com.example.demo.localhistory;

import com.example.demo.data.AllData;
import com.example.demo.util.FileManager;
import com.example.demo.util.FileToJson;
import com.intellij.openapi.vfs.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileEventListener implements VirtualFileListener {

    @Override
    public void contentsChanged(@NotNull VirtualFileEvent event) {
        // 文件内容发生变化时触发
        VirtualFile file = event.getFile();

        if(FileManager.isJavaFile(file)){
            try {
                FileToJson.oneFileToJson(file, FileManager.getRelativePath(file), AllData.onceModifyThreshold);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void fileCreated(@NotNull VirtualFileEvent event) {
        // 文件被创建时触发
        VirtualFile file = event.getFile();

        // 判断是否为 Java 文件
        if (FileManager.isJavaFile(file)) {
            try {
                // 保存创建时的文件版本
                FileToJson.oneFileToJson(file, FileManager.getRelativePath(file), AllData.createThreshold);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void fileMoved(@NotNull VirtualFileMoveEvent event) {
        // 文件被移动时触发
        VirtualFile oldParent = event.getOldParent();
        VirtualFile newParent = event.getNewParent();
        Path oldfilePath = Paths.get(oldParent.getPath());
        Path newfilePath = Paths.get(newParent.getPath());

        VirtualFile file = event.getFile();
        if (FileManager.isJavaFile(file)){
            try {
                // 更新文件的路径，并保存
                FileToJson.reviseFilePath(oldfilePath, newfilePath, file.getNameWithoutExtension());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
