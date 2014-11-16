/*
 *  Copyright 2001-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.commons.collections15.keyvalue;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.HashMap;
import java.util.Map;

/**
 * Test the TiedMapEntry class.
 *
 * @author Matt Hall, John Watkinson, Stephen Colebourne
 * @version $Revision: 1.1 $ $Date: 2005/10/11 19:11:58 $
 * @since Commons Collections 3.0
 */
public class TestTiedMapEntry extends AbstractTestMapEntry {

    public TestTiedMapEntry(String testName) {
        super(testName);

    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestTiedMapEntry.class);
    }

    public static Test suite() {
        return new TestSuite(TestTiedMapEntry.class);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the instance to test
     */
    public Map.Entry makeMapEntry(Object key, Object value) {
        Map map = new HashMap();
        map.put(key, value);
        return new TiedMapEntry(map, key);
    }

    //-----------------------------------------------------------------------
    /**
     * Tests the constructors.
     */
    public void testConstructors() {
        // ignore
    }

    /**
     * Tests the constructors.
     */
    public void testSetValue() {
        Map map = new HashMap();
        map.put("A", "a");
        map.put("B", "b");
        map.put("C", "c");
        Map.Entry entry = new TiedMapEntry(map, "A");
        assertSame("A", entry.getKey());
        assertSame("a", entry.getValue());
        assertSame("a", entry.setValue("x"));
        assertSame("A", entry.getKey());
        assertSame("x", entry.getValue());

        entry = new TiedMapEntry(map, "B");
        assertSame("B", entry.getKey());
        assertSame("b", entry.getValue());
        assertSame("b", entry.setValue("y"));
        assertSame("B", entry.getKey());
        assertSame("y", entry.getValue());

        entry = new TiedMapEntry(map, "C");
        assertSame("C", entry.getKey());
        assertSame("c", entry.getValue());
        assertSame("c", entry.setValue("z"));
        assertSame("C", entry.getKey());
        assertSame("z", entry.getValue());
    }

}
