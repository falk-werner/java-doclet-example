package org.example.doclet;

import org.junit.Test;
import static org.junit.Assert.*;

public class SampleTest {

    /**
     * A Dummy Testcase.
     */
    @Test public void someTest() {
        assertTrue("someLibraryMethod should return 'true'", true);
    }

    /**
     * Another Testcase.
     */
    @Test public void anotherTest() {
        assertTrue("someLibraryMethod should return 'true'", true);
    }

    @Test public void undocumentedTest() {
        assertTrue("someLibraryMethod should return 'true'", true);
    }

    /**
     * This is not a Test.
     */
    public void notATest() {

    }
}
