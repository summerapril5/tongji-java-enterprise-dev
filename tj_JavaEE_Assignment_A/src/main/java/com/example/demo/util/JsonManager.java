package com.example.demo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class JsonManager {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    //获取对应路径Json最新版本的content内容
    static public String getLatestContent(Path jsonFilePath) throws IOException {
        if(!Files.exists(jsonFilePath)){
            return "";
        }

        String jsonContent = new String(Files.readAllBytes(jsonFilePath));
        JsonNode rootNode = objectMapper.readTree(jsonContent);
        ObjectNode rootObject = (ObjectNode) rootNode;
        JsonNode informationNode = rootObject.get("information");
        ArrayNode informationArray = (ArrayNode) informationNode;
        return informationArray.get(informationArray.size() - 1).get("content").asText();
    }

    //获取对应版本的content内容
    static public String getVersionContent(int index, Path jsonFilePath) throws IOException{
        if(!Files.exists(jsonFilePath)){
            return "";
        }

        String jsonContent = new String(Files.readAllBytes(jsonFilePath));
        JsonNode rootNode = objectMapper.readTree(jsonContent);
        ObjectNode rootObject = (ObjectNode) rootNode;
        JsonNode informationNode = rootObject.get("information");
        ArrayNode informationArray = (ArrayNode) informationNode;
        return informationArray.get(index).get("content").asText();
    }

    //获取所有版本的目录内容
    static public ArrayList<String> getVersionList(Path jsonFilePath) throws IOException{
        ArrayList<String> versionList = new ArrayList<>();

        if(!Files.exists(jsonFilePath)){
            return versionList;
        }

        String jsonContent = new String(Files.readAllBytes(jsonFilePath));
        JsonNode rootNode = objectMapper.readTree(jsonContent);
        ObjectNode rootObject = (ObjectNode) rootNode;
        JsonNode informationNode = rootObject.get("information");
        ArrayNode informationArray = (ArrayNode) informationNode;
        for(int i=0;i<informationArray.size();i++){
            versionList.add("Version "+i+" - "+informationArray.get(i).get("time").asText());
        }
        return versionList;
    }
}
