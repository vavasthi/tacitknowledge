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
package org.apache.commons.collections15.iterators;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.commons.collections15.MapIterator;
import org.apache.commons.collections15.OrderedMap;
import org.apache.commons.collections15.OrderedMapIterator;
import org.apache.commons.collections15.Unmodifiable;
import org.apache.commons.collections15.map.ListOrderedMap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Tests the UnmodifiableOrderedMapIterator.
 *
 * @author Matt Hall, John Watkinson, Stephen Colebourne
 * @version $Revision: 1.1 $ $Date: 2005/10/11 19:11:58 $
 */
public class TestUnmodifiableOrderedMapIterator extends AbstractTestOrderedMapIterator {

    public static Test suite() {
        return new TestSuite(TestUnmodifiableOrderedMapIterator.class);
    }

    public TestUnmodifiableOrderedMapIterator(String testName) {
        super(testName);
    }

    public MapIterator makeEmptyMapIterator() {
        return UnmodifiableOrderedMapIterator.decorate(ListOrderedMap.decorate(new HashMap()).orderedMapIterator());
    }

    public MapIterator makeFullMapIterator() {
        return UnmodifiableOrderedMapIterator.decorate(((OrderedMap) getMap()).orderedMapIterator());
    }

    public Map getMap() {
        Map testMap = ListOrderedMap.decorate(new HashMap());
        testMap.put("A", "a");
        testMap.put("B", "b");
        testMap.put("C", "c");
        return testMap;
    }

    public Map getConfirmedMap() {
        Map testMap = new TreeMap();
        testMap.put("A", "a");
        testMap.put("B", "b");
        testMap.put("C", "c");
        return testMap;
    }

    public boolean supportsRemove() {
        return false;
    }

    public boolean supportsSetValue() {
        return false;
    }

    //-----------------------------------------------------------------------
    public void testOrderedMapIterator() {
        assertTrue(makeEmptyOrderedMapIterator() instanceof Unmodifiable);
    }

    public void testDecorateFactory() {
        OrderedMapIterator it = makeFullOrderedMapIterator();
        assertSame(it, UnmodifiableOrderedMapIterator.decorate(it));

        it = ((OrderedMap) getMap()).orderedMapIterator();
        assertTrue(it != UnmodifiableOrderedMapIterator.decorate(it));

        try {
            UnmodifiableOrderedMapIterator.decorate(null);
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

}
