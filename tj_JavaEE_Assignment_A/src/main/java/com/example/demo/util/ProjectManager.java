package com.example.demo.util;

import com.example.demo.data.AllData;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.project.Project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProjectManager {
    static private Project project;

    static public void setProject(Project project){
        ProjectManager.project=project;
    }

    static public Project getProject() {
        return ProjectManager.project;
    }

    //获取json储存目录
    static public Path getPluginPrivateDir(){
        String pluginsDirPath = PathManager.getPluginsPath();
        Path lcDirPath = Paths.get(pluginsDirPath, AllData.pluginDirName);
        if (!Files.exists(lcDirPath)) {
            try {
                Files.createDirectories(lcDirPath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to create directory", e);
            }
        }
        Path jsonDirPath=lcDirPath.resolve(AllData.jsonDirName);
        if (!Files.exists(jsonDirPath)) {
            try {
                Files.createDirectories(jsonDirPath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to create directory", e);
            }
        }
        return jsonDirPath;
    }
}
