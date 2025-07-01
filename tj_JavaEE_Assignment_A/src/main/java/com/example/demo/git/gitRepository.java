package com.example.demo.git;

import com.intellij.openapi.project.Project;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import java.io.IOException;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class gitRepository {


    private static Git git;

    private gitRepository(){};

    private static File projectPath;



/**
 * 单例获取实例方法
 * */
    public static Git getInstance() throws GitAPIException, IOException {
        if(git == null){

            if(!isPathExist(projectPath)){
                createRepository(projectPath);
            }//当前项目内不存在仓库
            else{
                FileRepositoryBuilder builder = new FileRepositoryBuilder();
                try(Repository repo = builder.setGitDir(new File(projectPath,".git"))
                        .readEnvironment().findGitDir().build())
                {
                    git = new Git(repo);
                }
            }//当前项目存在仓库
        }
        return git;
    }



    /**
     * 判断该路径下是否存在本地仓库
     * @param projectPath 文件获取绝对路径路径
     * @return 布尔值
     * */
    public static boolean isPathExist(File projectPath)
    {
        File gitDir = new File(projectPath, ".git");
        //.git子文件夹
        return gitDir.exists()&&gitDir.isDirectory();
    }

    /**
     * 创建本地仓库
     */
    public static void createRepository(File gitDir) throws GitAPIException , IOException{
      try{

          git = Git.init().setDirectory(gitDir).call();
      }
      catch(GitAPIException e)
      {
          e.printStackTrace();
      }
    }

    public static void setGitDir(String gitDir)
    {
        projectPath = new File(gitDir);
    }












}
