package se.commit.gen;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.lib.Repository;

import se.commit.jgit.Commit;
import se.commit.jgit.JGitWrapper;
import se.commit.nlg.CommitGenerator;

/**
 * This class contains the driver functions for running the GUI
 * @author salman
 *
 */
public class Driver
{
    public static void main(String[] args) {
        
        String repoName = "/Users/salman/eclipse-workspace/spring-framework/.git"; 
        //String repoName = "/Users/salman/eclipse-workspace/TestProject/.git";
        List<Commit> commitData = null;
        
        try(Repository repository = JGitWrapper.openGitRepository(repoName)){
            commitData = JGitWrapper.getAllCommits(repository);
            Collections.reverse(commitData);
            
            //System.out.println(commitData.size());
            
            //ChangeGui swingControlDemo = new ChangeGui(repository, commitData, 14695);  
            //swingControlDemo.displayGui();
        }
        
        CommitGenerator cg = new CommitGenerator(repoName);
        //cg.generate();
        cg.generateGUI(commitData, 8578);
        
        
    }
   

    public static String getTextFromFilePath(Path file) throws IOException {
        byte[] bytes = Files.readAllBytes(file);
        CharBuffer chars = Charset.defaultCharset().decode(ByteBuffer.wrap(bytes));
        return chars.toString();
    }
}
