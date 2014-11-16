/*
 *  Copyright 2004 The Apache Software Foundation
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
package org.apache.commons.collections15.map;

import junit.framework.Test;
import junit.textui.TestRunner;
import org.apache.commons.collections15.BulkTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Tests for the {@link CaseInsensitiveMap} implementation.
 *
 * @author Matt Hall, John Watkinson, Commons-Collections team
 * @version $Revision: 1.1 $ $Date: 2005/10/11 19:11:58 $
 */
public class TestCaseInsensitiveMap extends AbstractTestIterableMap {

    public TestCaseInsensitiveMap(String testName) {
        super(testName);
    }

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public static Test suite() {
        return BulkTest.makeSuite(TestCaseInsensitiveMap.class);
    }

    public Map makeEmptyMap() {
        return new CaseInsensitiveMap();
    }

    public String getCompatibilityVersion() {
        return "3";
    }
   
    //-------------------------------------------------------------------------
    
    public void testCaseInsensitive() {
        Map map = new CaseInsensitiveMap();
        map.put("One", "One");
        map.put("Two", "Two");
        assertEquals("One", (String) map.get("one"));
        assertEquals("One", (String) map.get("oNe"));
        map.put("two", "Three");
        assertEquals("Three", (String) map.get("Two"));
    }

    public void testNullHandling() {
        Map map = new CaseInsensitiveMap();
        map.put("One", "One");
        map.put("Two", "Two");
        map.put(null, "Three");
        assertEquals("Three", (String) map.get(null));
        map.put(null, "Four");
        assertEquals("Four", (String) map.get(null));
        Set keys = map.keySet();
        assertTrue(keys.contains("one"));
        assertTrue(keys.contains("two"));
        assertTrue(keys.contains(null));
        assertTrue(keys.size() == 3);
    }

    public void testPutAll() {
        // GenericsNote: Updating this testcase to better reflect the type safety of the new CaseInsensitiveMap
        Map<String, String> map = new HashMap<String, String>();
        map.put("One", "One");
        map.put("Two", "Two");
        map.put("one", "Three");
        map.put(null, "Four");
        //map.put(new Integer(20), "Five");
        Map<String, String> caseInsensitiveMap = new CaseInsensitiveMap<String>(map);
        assertTrue(caseInsensitiveMap.size() == 3); // ones collapsed
        Set keys = caseInsensitiveMap.keySet();
        assertTrue(keys.contains("one"));
        assertTrue(keys.contains("two"));
        assertTrue(keys.contains(null));
        //assertTrue(keys.contains(Integer.toString(20)));
        assertTrue(keys.size() == 3);
        assertTrue(!caseInsensitiveMap.containsValue("One") || !caseInsensitiveMap.containsValue("Three")); // ones collaped
        assertEquals(caseInsensitiveMap.get(null), "Four");
    }

    public void testClone() {
        CaseInsensitiveMap map = new CaseInsensitiveMap(10);
        map.put("1", "1");
        Map cloned = (Map) map.clone();
        assertEquals(map.size(), cloned.size());
        assertSame(map.get("1"), cloned.get("1"));
    }
    
    /*
    public void testCreate() throws Exception {
        resetEmpty();
        writeExternalFormToDisk((java.io.Serializable) map, "/home/phil/jakarta-commons/collections15/data/test/CaseInsensitiveMap.emptyCollection.version3.obj");
        resetFull();
        writeExternalFormToDisk((java.io.Serializable) map, "/home/phil/jakarta-commons/collections15/data/test/CaseInsensitiveMap.fullCollection.version3.obj");
    }
     */
}
