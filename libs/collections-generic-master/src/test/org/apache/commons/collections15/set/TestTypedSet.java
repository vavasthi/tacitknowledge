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
package org.apache.commons.collections15.set;

import junit.framework.Test;
import org.apache.commons.collections15.BulkTest;

import java.util.HashSet;
import java.util.Set;

/**
 * Extension of {@link AbstractTestSet} for exercising the
 * {@link TypedSet} implementation.
 *
 * @author Matt Hall, John Watkinson, Phil Steitz
 * @version $Revision: 1.1 $ $Date: 2005/10/11 19:11:58 $
 * @since Commons Collections 3.1
 */
public class TestTypedSet extends AbstractTestSet {

    public TestTypedSet(String testName) {
        super(testName);
    }

    public static Test suite() {
        return BulkTest.makeSuite(TestTypedSet.class);
    }

    public static void main(String args[]) {
        String[] testCaseName = {TestTypedSet.class.getName()};
        junit.textui.TestRunner.main(testCaseName);
    }

    //-------------------------------------------------------------------
    public Set makeEmptySet() {
        return TypedSet.decorate(new HashSet(), Object.class);
    }

    public boolean isNullSupported() {
        return false;
    }

    public boolean skipSerializedCanonicalTests() {
        return true;  // Typed and Predicated get confused
    }

}
