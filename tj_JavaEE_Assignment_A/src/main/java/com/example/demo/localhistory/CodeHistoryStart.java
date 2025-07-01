package com.example.demo.localhistory;

import com.example.demo.data.AllData;
import com.example.demo.util.FileToJson;
import com.example.demo.util.FileManager;
import com.example.demo.util.ProjectManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.openapi.vfs.VirtualFile;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.example.demo.git.initialGitCommit;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CodeHistoryStart implements ProjectActivity {

    // 定时任务服务，用于定时扫描
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        System.out.println("项目已打开，初始化代码历史追踪。");

        ProjectManager.setProject(project);

        VirtualFile[] contentRoots = ProjectRootManager.getInstance(project).getContentRoots();

        // 创建 "CodeHistory" 目录
        ProjectManager.getPluginPrivateDir();

        // 遍历并处理项目中的文件
        for (VirtualFile root : contentRoots) {
            try {
                FileToJson.traverseDirectory(root, "", AllData.initializeThreshold);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // 启动定时任务，每隔 10 分钟扫描一次项目中的 Java 文件
        scheduler.scheduleAtFixedRate(this::scanAndSaveModifiedFiles, 0, 1, TimeUnit.MINUTES);

        return null;
    }


    // 扫描并保存有修改的文件
    private void scanAndSaveModifiedFiles() {
        VirtualFile[] contentRoots = ProjectRootManager.getInstance(ProjectManager.getProject()).getContentRoots();
        for (VirtualFile root : contentRoots) {
            try {
                traverseAndCheckFiles(root);
            } catch (IOException | GitAPIException e) {
                e.printStackTrace();
            }
        }
    }

    // 递归遍历目录，并检查文件修改量
    private void traverseAndCheckFiles(VirtualFile file) throws GitAPIException,IOException {
        if (file.isDirectory()) {
            for (VirtualFile child : file.getChildren()) {
                traverseAndCheckFiles(child);
            }
        } else if (file.getName().endsWith(".java")) {
            double changeAmount = FileManager.getChangeAmount(file);  // 获取文件修改百分比
            if (changeAmount > AllData.schedulerThreshold) {  // 当修改量超过阈值时才保存
                if(initialGitCommit.Running())
                {
                    initialGitCommit.Commit();
                }
                FileToJson.oneFileToJson(file, FileManager.getRelativePath(file), AllData.schedulerThreshold);
                System.out.println("文件因定时轮转任务保存: " + file.getPath() + "，修改量: " + changeAmount + "%");
            }
        }
    }

    // 停止定时任务
    public void stopAutoSaveTask() {
        scheduler.shutdown();
    }
}