package com.example.demo.util;

import com.example.demo.data.AllData;
import com.example.demo.data.AllData;
import com.example.demo.git.initialGitCommit;
import com.example.demo.structure.OneVersionInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.refactoring.rename.RenameProcessor;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileToJson {
    //用于解析json数据
    private static final ObjectMapper objectMapper = new ObjectMapper();

    //负责将一个文件转化为json数据
    static public void oneFileToJson(VirtualFile file, String directoryPath, int modifyThreshold) throws IOException {
        Path jsonFilePath = FileManager.getJsonFilePath(file);

        if (FileManager.isJavaFile(file)) {
            try {
                double changeAmount = FileManager.getChangeAmount(file);
                if (changeAmount < modifyThreshold) {
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("文件因变化量任务保存: ");
        System.out.println("文件因变化量任务保存: ");
        try {

            if (initialGitCommit.Running()) {
                initialGitCommit.Commit();
            }
        }
        catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
        if(Files.exists(jsonFilePath)){
            addVersionJson(jsonFilePath, file);
        }else{
            createNewJson(jsonFilePath, file, directoryPath);
        }
    }

    //创建一个新的json文件
    static public void createNewJson(Path jsonFilePath, VirtualFile file, String directoryPath){
        Map<String, Object> fileInfo = new HashMap<>();
        fileInfo.put("version", 0);
        fileInfo.put("fileName", file.getName());
        fileInfo.put("filePath", directoryPath);
        try {
            ArrayList<OneVersionInfo> information = new ArrayList<>();
            OneVersionInfo oneversioninfo = new OneVersionInfo(LocalDateTime.now().toString(),readFileContent(file));
            information.add(oneversioninfo);
            fileInfo.put("information", information);
        } catch (IOException e) {
            e.printStackTrace();
            fileInfo.put("information", "Error reading file content");
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(fileInfo);

        try {
            Files.write(jsonFilePath, jsonString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to write JSON file", e);
        }
    }

    //在对应json文件后添加新的版本
    static public void addVersionJson(Path jsonFilePath, VirtualFile file) throws IOException {
        String jsonContent = new String(Files.readAllBytes(jsonFilePath));
        JsonNode rootNode = objectMapper.readTree(jsonContent);

        ObjectNode rootObject = (ObjectNode) rootNode;
        JsonNode informationNode = rootObject.get("information");
        ArrayNode informationArray = (ArrayNode) informationNode;
        String content = informationArray.get(informationArray.size() - 1).get("content").asText();

        String fileContent = readFileContent(file);
        if (!fileContent.equals(content)) {
            OneVersionInfo oneversioninfo = new OneVersionInfo(LocalDateTime.now().toString(),fileContent);
            ObjectNode objectNode = objectMapper.valueToTree(oneversioninfo);
            informationArray.add(objectNode);

            JsonNode versionNode = rootObject.get("version");
            int version = versionNode.asInt();
            rootObject.put("version", version + 1);
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            String updatedJsonContent = writer.writeValueAsString(rootObject);
            Files.write(jsonFilePath, updatedJsonContent.getBytes());
        }
    }

    //修改文件路径时对json的修改
    static public void reviseFilePath(Path oldfilePath,Path newfilePath,String fileName) throws IOException {
        String oldRelativePath=FileManager.getRelativePath(oldfilePath);
        String newRelativePath=FileManager.getRelativePath(newfilePath);
        String oldJsonName=fileName+'_'+oldRelativePath.replace("\\", "")+".json";
        String newJsonName=fileName+'_'+newRelativePath.replace("\\", "")+".json";

        //获取json路径
        Path codeHistoryDir = ProjectManager.getPluginPrivateDir();
        Path jsonFilePath = codeHistoryDir.resolve(oldJsonName);

        //获取与写回数据
        String jsonContent = new String(Files.readAllBytes(jsonFilePath));
        JsonNode rootNode = objectMapper.readTree(jsonContent);
        ObjectNode rootObject = (ObjectNode) rootNode;
        rootObject.put("filePath", newRelativePath);
        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
        String updatedJsonContent = writer.writeValueAsString(rootObject);
        Files.write(jsonFilePath, updatedJsonContent.getBytes());

        //修改json文件名
        VirtualFile jsonFile=LocalFileSystem.getInstance().findFileByPath(jsonFilePath.toString().replace("\\", "/"));
        Project project=ProjectManager.getProject();
        PsiManager psiManager = PsiManager.getInstance(project);
        PsiFile psiFile = psiManager.findFile(jsonFile);
        WriteCommandAction.runWriteCommandAction(project, () -> {
            RenameProcessor renameProcessor = new RenameProcessor(project, psiFile, newJsonName, false, false);
            renameProcessor.run();
        });
    }

    //用于读取文件内容
    static public String readFileContent(VirtualFile file) throws IOException {
        byte[] contentBytes = file.contentsToByteArray();
        return new String(contentBytes);
    }

    // 递归遍历整个文件，在打开项目时使用
    static public void traverseDirectory(VirtualFile dir, String currentPath, int modifyThreshold) throws IOException {
        for (VirtualFile child : dir.getChildren()) {
            String childPath = currentPath + "\\" + child.getName();
            if (child.isDirectory()) {
                traverseDirectory(child, childPath, modifyThreshold);
            } else if(child.getName().endsWith(".java")){
                oneFileToJson(child, currentPath, modifyThreshold);
            }
        }
    }
}
