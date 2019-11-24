package sattools.findbugs;

public class FingBugsRunnerTest {

    public void runFBforSingleRepoandRevision() {
        String repoDir = "/data/bowen/data/Transformation4J/test/stripe#stripe-java";
        String tarComStr = "674dbc9e6c4a344c6015b84dfa3175ca4525af7a";
        String revisedFilesPath = "";
        String previousFilesPath = "";
        String tmpDir = "";
        String reportDir = "";
        String logDir = "";
        new FindBugsRunner().runFBforSingleRepoandRevision(repoDir, tarComStr, revisedFilesPath, previousFilesPath, tmpDir, reportDir, logDir);
    }
}
