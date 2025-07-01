package com.example.demo.localhistory;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.vfs.VirtualFileManager;

public class ComponentsInit implements ApplicationComponent {
    private FileEventListener fileEventListener;

    @Override
    public void initComponent() {
        // 初始化文件事件监听器
        fileEventListener = new FileEventListener();
        VirtualFileManager.getInstance().addVirtualFileListener(fileEventListener);
    }

    @Override
    public void disposeComponent() {
        // 移除文件事件监听器
        if (fileEventListener != null) {
            VirtualFileManager.getInstance().removeVirtualFileListener(fileEventListener);
        }
    }
}
