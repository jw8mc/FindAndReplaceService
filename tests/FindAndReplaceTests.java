import Week1Java.FindAndReplace;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;


/**
 * Created by student on 9/22/2015.
 */


public class FindAndReplaceTests {

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        temporaryFolder.create();
    }

    @Test
    public void testInstantiateFindAndReplace() {
    //    FindAndReplace findAndReplace = new FindAndReplace(null, null, null);
    //    assertNotNull(findAndReplace);
    }

    @Test
    public void testCreatePrintWriter() throws IOException {
        File output = temporaryFolder.newFile("testOutput.txt");

    }

    @Test
    public void testCreatePrintWriterWithoutPermissions() throws IOException {
        File temp = temporaryFolder.newFolder("tempNoPermissions");
        File output = new File(temp, "testOutput.txt");
        temp.setReadOnly();
     //   FindAndReplace findAndReplace = new FindAndReplace(null, output.getAbsolutePath(), null);
       // PrintWriter printWriter = findAndReplace.createOutputFile();
    //    assertNull(printWriter);
    }
}
