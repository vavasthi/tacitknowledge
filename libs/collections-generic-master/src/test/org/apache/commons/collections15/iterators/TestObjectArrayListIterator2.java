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

import java.util.ListIterator;

/**
 * Tests the ObjectArrayListIterator class.
 *
 * @author Matt Hall, John Watkinson, Stephen Colebourne
 * @version $Revision: 1.1 $ $Date: 2005/10/11 19:11:58 $
 */
public class TestObjectArrayListIterator2 extends AbstractTestListIterator {

    protected String[] testArray = {"One", "Two", "Three"};

    public TestObjectArrayListIterator2(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestObjectArrayListIterator2.class);
    }

    public ListIterator makeEmptyListIterator() {
        return new ObjectArrayListIterator(new Object[0]);
    }

    public ListIterator makeFullListIterator() {
        return new ObjectArrayListIterator(testArray);
    }

    public ListIterator makeArrayListIterator(Object[] array) {
        return new ObjectArrayListIterator(array);
    }

    public boolean supportsAdd() {
        return false;
    }

    public boolean supportsRemove() {
        return false;
    }

}
