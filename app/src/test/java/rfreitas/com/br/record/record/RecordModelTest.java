package rfreitas.com.br.record.record;


import org.junit.Assert;
import org.junit.Test;

import rfreitas.com.br.record.record.Record;

/**
 * Created by rafael-iteris on 1/6/17.
 */

public class RecordModelTest {

    @Test(expected = IllegalArgumentException.class)
    public void recordMustBeAFolderAndFileNameNotNull(){
        new Record(null, null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void recordMustBeAFolderAndFileNameNotEmpty(){
        new Record("", "", 0);
    }

    @Test
    public void recordMustBeACorrectFullPathFormat(){
        Record r1 = new Record("folder", "filename.mp3", 0);
        Record r2 = new Record("folder/", "filename.mp3", 0);
        Record r3 = new Record("folder", "/filename.mp3", 0);
        Record r4 = new Record("folder/", "/filename.mp3", 0);
        Record r5 = new Record("/folder/sub", "/filename.mp3", 0);

        Assert.assertEquals(r1.getFullPath(), "folder/filename.mp3");
        Assert.assertEquals(r2.getFullPath(), "folder/filename.mp3");
        Assert.assertEquals(r3.getFullPath(), "folder/filename.mp3");
        Assert.assertEquals(r4.getFullPath(), "folder/filename.mp3");
        Assert.assertEquals(r5.getFullPath(), "/folder/sub/filename.mp3");
    }

}
