package com.example.demo.git;


import com.example.demo.util.CommitHistoryToolWindowFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.dircache.DirCacheCheckout;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.jetbrains.annotations.NotNull;

public class gitAction {

    public static void createBranch(Git git) throws GitAPIException , IOException {
        System.out.println("进入createBranch函数");
        if(git.getRepository().findRef("fineGrained")==null) {
            git.branchCreate().setName("fineGrained").call();
        }
        System.out.println("Branch created");
        git.checkout().setName("fineGrained").call();
        System.out.println("Checkout created");
    }

    public static void commit(Git git, Integer version, Project project) throws GitAPIException {
        git.add().setUpdate(true).addFilepattern(".").call();

        RevCommit commit =git.commit().setMessage("V."+version.toString()).call();
        System.out.println("table enter");
        String commitMessage = commit.getShortMessage();
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Commit History");

        // 更新表格内容
        CommitHistoryToolWindowFactory.addCommitInfo(commitMessage);
        System.out.println("table done");

    }





    private static String formatCommitInfo(RevCommit commit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = LocalDateTime.now().format(formatter);
        return time + " - " + commit.getShortMessage();
    }


    public static void applyCommitFromFineGrainedBranch(Git git, String targetBranch, String fineGrainedBranch, Project project) throws GitAPIException, IOException {
        Repository repository = git.getRepository();

        // 切换到目标分支
        git.checkout().setName(targetBranch).call();

        // 获取 fineGrained 分支的最后一次 commit
        Ref fineGrainedRef = repository.findRef(fineGrainedBranch);
        if (fineGrainedRef == null) {
            throw new IllegalArgumentException("Branch " + fineGrainedBranch + " does not exist.");
        }

        RevWalk revWalk = new RevWalk(repository);
        RevCommit fineGrainedLastCommit = revWalk.parseCommit(fineGrainedRef.getObjectId());

        // 获取目标分支的最后一次 commit
        Ref targetRef = repository.findRef(targetBranch);
        RevCommit targetLastCommit = revWalk.parseCommit(targetRef.getObjectId());

        // 生成 diff 信息
        String diffMessage = generateDiffBetweenCommits(repository, fineGrainedLastCommit, targetLastCommit);
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Commit History");

        // 更新表格内容
        CommitHistoryToolWindowFactory.addCommitInfo("已结束自动commit");

        File lockFile = new File(git.getRepository().getDirectory(), "index.lock");
        if (lockFile.exists()) {
            if (!lockFile.delete()) {
                throw new IOException("Failed to delete lock file: " + lockFile.getAbsolutePath());
            }
        }


        // 应用 fineGrained 分支的最后一次 commit 到目标分支
        DirCacheCheckout checkout = new DirCacheCheckout(repository, repository.readDirCache(), fineGrainedLastCommit.getTree());
        checkout.checkout();

        // 提交新的 commit，带上 diff 信息
        git.commit()
                .setMessage("Merged changes from " + fineGrainedBranch + " to " + targetBranch + "\n\nDiff:\n" + diffMessage)
                .call();


        // 删除 fineGrained 分支
        git.branchDelete().setBranchNames(fineGrainedBranch).setForce(true).call();

        revWalk.close();
    }

    // 生成两个 commit 之间的差异（diff）
    private static String generateDiffBetweenCommits(Repository repository, RevCommit newCommit, RevCommit oldCommit) throws IOException {
        // 输出流，用于存储 diff 信息
        ByteArrayOutputStream diffOutput = new ByteArrayOutputStream();
        DiffFormatter diffFormatter = new DiffFormatter(diffOutput);
        diffFormatter.setRepository(repository);

        // 获取新旧 commit 的树（Tree）
        ObjectReader reader = repository.newObjectReader();
        CanonicalTreeParser newTreeParser = new CanonicalTreeParser();
        newTreeParser.reset(reader, newCommit.getTree());
        CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
        oldTreeParser.reset(reader, oldCommit.getTree());

        // 生成 diff
        List<DiffEntry> diffs = diffFormatter.scan(oldTreeParser, newTreeParser);
        diffFormatter.format(diffs);

        diffFormatter.close();

        // 返回 diff 信息
        return diffOutput.toString();
    }


}
