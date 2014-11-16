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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Tests the ObjectArrayIterator.
 *
 * @author James Strachan
 * @author Mauricio S. Moura
 * @author Morgan Delagrange
 * @author Matt Hall, John Watkinson, Stephen Colebourne
 * @version $Revision: 1.1 $ $Date: 2005/10/11 19:11:58 $
 */
public class TestObjectArrayIterator extends AbstractTestIterator {

    protected String[] testArray = {"One", "Two", "Three"};

    public static Test suite() {
        return new TestSuite(TestObjectArrayIterator.class);
    }

    public TestObjectArrayIterator(String testName) {
        super(testName);
    }

    public Iterator makeEmptyIterator() {
        return new ObjectArrayIterator(new Object[0]);
    }

    public Iterator makeFullIterator() {
        return new ObjectArrayIterator(testArray);
    }

    public ObjectArrayIterator makeArrayIterator() {
        return new ObjectArrayIterator();
    }

    public ObjectArrayIterator makeArrayIterator(Object[] array) {
        return new ObjectArrayIterator(array);
    }

    public ObjectArrayIterator makeArrayIterator(Object[] array, int index) {
        return new ObjectArrayIterator(array, index);
    }

    public ObjectArrayIterator makeArrayIterator(Object[] array, int start, int end) {
        return new ObjectArrayIterator(array, start, end);
    }

    public boolean supportsRemove() {
        return false;
    }

    public void testIterator() {
        Iterator iter = (Iterator) makeFullIterator();
        for (int i = 0; i < testArray.length; i++) {
            Object testValue = testArray[i];
            Object iterValue = iter.next();

            assertEquals("Iteration value is correct", testValue, iterValue);
        }

        assertTrue("Iterator should now be empty", !iter.hasNext());

        try {
            Object testValue = iter.next();
        } catch (Exception e) {
            assertTrue("NoSuchElementException must be thrown", e.getClass().equals((new NoSuchElementException()).getClass()));
        }
    }

    public void testNullArray() {
        try {
            Iterator iter = makeArrayIterator(null);

            fail("Constructor should throw a NullPointerException when constructed with a null array");
        } catch (NullPointerException e) {
            // expected
        }

        ObjectArrayIterator iter = makeArrayIterator();
        try {
            iter.setArray(null);

            fail("setArray(null) should throw a NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    public void testDoubleSet() {
        ObjectArrayIterator it = makeArrayIterator();
        it.setArray(new String[0]);
        try {
            it.setArray(new String[0]);
            fail();
        } catch (IllegalStateException ex) {
        }
    }

    public void testReset() {
        ObjectArrayIterator it = makeArrayIterator(testArray);
        it.next();
        it.reset();
        assertEquals("One", it.next());
    }

}
