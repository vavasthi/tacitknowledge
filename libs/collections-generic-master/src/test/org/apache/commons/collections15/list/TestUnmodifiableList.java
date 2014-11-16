/*
 *  Copyright 2003-2004 The Apache Software Foundation
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
package org.apache.commons.collections15.list;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Extension of {@link AbstractTestList} for exercising the
 * {@link UnmodifiableList} implementation.
 *
 * @author Matt Hall, John Watkinson, Phil Steitz
 * @version $Revision: 1.1 $ $Date: 2005/10/11 19:11:58 $
 * @since Commons Collections 3.0
 */
public class TestUnmodifiableList extends AbstractTestList {

    public TestUnmodifiableList(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestUnmodifiableList.class);
    }

    public static void main(String args[]) {
        String[] testCaseName = {TestUnmodifiableList.class.getName()};
        junit.textui.TestRunner.main(testCaseName);
    }

    //-----------------------------------------------------------------------    
    public List makeEmptyList() {
        return UnmodifiableList.decorate(new ArrayList());
    }

    public List makeFullList() {
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(getFullElements()));
        return UnmodifiableList.decorate(list);
    }

    public boolean isSetSupported() {
        return false;
    }

    public boolean isAddSupported() {
        return false;
    }

    public boolean isRemoveSupported() {
        return false;
    }

    //-----------------------------------------------------------------------
    protected UnmodifiableList list = null;
    protected ArrayList array = null;

    protected void setupList() {
        list = (UnmodifiableList) makeFullList();
        array = new ArrayList();
        array.add(new Integer(1));
    }

    /**
     * Verify that base list and sublists are not modifiable
     */
    public void testUnmodifiable() {
        setupList();
        verifyUnmodifiable(list);
        verifyUnmodifiable(list.subList(0, 2));
    }

    protected void verifyUnmodifiable(List list) {
        try {
            list.add(0, new Integer(0));
            fail("Expecting UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        try {
            list.add(new Integer(0));
            fail("Expecting UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        try {
            list.addAll(0, array);
            fail("Expecting UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        try {
            list.addAll(array);
            fail("Expecting UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        try {
            list.clear();
            fail("Expecting UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        try {
            list.remove(0);
            fail("Expecting UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        try {
            list.remove(new Integer(0));
            fail("Expecting UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        try {
            list.removeAll(array);
            fail("Expecting UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        try {
            list.retainAll(array);
            fail("Expecting UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        try {
            list.set(0, new Integer(0));
            fail("Expecting UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    /**
     * Verify that iterator is not modifiable
     */
    public void testUnmodifiableIterator() {
        setupList();
        Iterator iterator = list.iterator();
        try {
            Object obj = iterator.next();
            iterator.remove();
            fail("Expecting UnsupportedOperationException.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    public String getCompatibilityVersion() {
        return "3.1";
    }

    //    public void testCreate() throws Exception {
    //        resetEmpty();
    //        writeExternalFormToDisk((java.io.Serializable) collection, "D:/dev/collections15/data/test/UnmodifiableList.emptyCollection.version3.1.obj");
    //        resetFull();
    //        writeExternalFormToDisk((java.io.Serializable) collection, "D:/dev/collections15/data/test/UnmodifiableList.fullCollection.version3.1.obj");
    //    }

}
