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
package org.apache.commons.collections15.collection;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Extension of {@link AbstractTestCollection} for exercising the
 * {@link UnmodifiableCollection} implementation.
 *
 * @author Phil Steitz
 * @author Matt Hall, John Watkinson, Stephen Colebourne
 * @version $Revision: 1.1 $ $Date: 2005/10/11 19:11:58 $
 * @since Commons Collections 3.0
 */
public class TestUnmodifiableCollection extends AbstractTestCollection {

    public TestUnmodifiableCollection(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestUnmodifiableCollection.class);
    }

    public static void main(String args[]) {
        String[] testCaseName = {TestUnmodifiableCollection.class.getName()};
        junit.textui.TestRunner.main(testCaseName);
    }

    //-----------------------------------------------------------------------    
    public Collection makeCollection() {
        return UnmodifiableCollection.decorate(new ArrayList());
    }

    public Collection makeFullCollection() {
        List list = new ArrayList();
        list.addAll(Arrays.asList(getFullElements()));
        return UnmodifiableCollection.decorate(list);
    }

    public Collection makeConfirmedCollection() {
        ArrayList list = new ArrayList();
        return list;
    }

    public Collection makeConfirmedFullCollection() {
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(getFullElements()));
        return list;
    }

    public boolean isAddSupported() {
        return false;
    }

    public boolean isRemoveSupported() {
        return false;
    }

    public String getCompatibilityVersion() {
        return "3.1";
    }

    //    public void testCreate() throws Exception {
    //        resetEmpty();
    //        writeExternalFormToDisk((java.io.Serializable) collection, "D:/dev/collections15/data/test/UnmodifiableCollection.emptyCollection.version3.1.obj");
    //        resetFull();
    //        writeExternalFormToDisk((java.io.Serializable) collection, "D:/dev/collections15/data/test/UnmodifiableCollection.fullCollection.version3.1.obj");
    //    }

}
