package sattools.findbugs;

import edu.lu.uni.serval.alarm.iter.task.FindBugsAlarmCollectTask;
import edu.lu.uni.serval.git.exception.GitRepositoryNotFoundException;
import edu.lu.uni.serval.git.exception.NotValidGitRepositoryException;
import edu.lu.uni.serval.git.traverse.GitRepository;
import edu.lu.uni.serval.utils.FileHelper;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FindBugsRunner {

    void runFBforSingleRepoandRevision(String repoDir, String tarComStr, String revisedFilesPath, String previousFilesPath, String tmpDir, String reportDir, String logDir) {
        FileHelper.createDirectory(revisedFilesPath);
        FileHelper.createDirectory(previousFilesPath);

        GitRepository gitRepo = new GitRepository(repoDir + "/.git", revisedFilesPath, previousFilesPath);
        try {
            gitRepo.open();
            List<RevCommit> commits = gitRepo.getAllCommits(false);
            RevCommit tarCom = null;
            for (RevCommit com : commits) {
                if (com.getName().equals(tarComStr)) {
                    tarCom = com;
                    break;
                }
            }
            if (tarCom == null) {
                System.exit(1);
            }
            FindBugsAlarmCollectTask tk = new FindBugsAlarmCollectTask();
            tk.setRepoDir(repoDir);
            tk.setProjectName(getRepositoryName(repoDir));
            tk.setReportsRoot(reportDir);
            tk.setMavenTempRepoDir(tmpDir);
            tk.setLogDir(new File(logDir));

            tk.doTask(tarCom);
        } catch (GitRepositoryNotFoundException e) {
            e.printStackTrace();
        } catch (NotValidGitRepositoryException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoHeadException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    private static String getRepositoryName(String gitRepositoryPath) {
        // ../../git/commons-math/.git
        String gitRepositoryName = FileHelper.getFileName(FileHelper.getFileParentPath(gitRepositoryPath));

        return gitRepositoryName;
    }

    public void main(String[] args) {
        String repoDir = "/data/bowen/data/Transformation4J/test/stripe#stripe-java";
        String tarComStr = "674dbc9e6c4a344c6015b84dfa3175ca4525af7a";
        String revisedFilesPath = "";
        String previousFilesPath = "";
        String tmpDir = "";
        String reportDir = "";
        String logDir = "";
        runFBforSingleRepoandRevision(repoDir, tarComStr, revisedFilesPath, previousFilesPath, tmpDir, reportDir, logDir);
    }


}
