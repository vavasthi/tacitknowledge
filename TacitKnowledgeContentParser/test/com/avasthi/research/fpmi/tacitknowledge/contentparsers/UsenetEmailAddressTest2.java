/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import java.util.StringTokenizer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author vavasthi
 */
public class UsenetEmailAddressTest2 {
    
    public UsenetEmailAddressTest2() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class UsenetEmailAddress.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        UsenetEmailAddress instance = new UsenetEmailAddress("gazelle@shell.xmission.com (Kenny McCormack)");
        String expResult = "Kenny McCormack";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAddress method, of class UsenetEmailAddress.
     */
    @Test
    public void testGetAddress() {
        System.out.println("getAddress");
        UsenetEmailAddress instance = new UsenetEmailAddress("gazelle@shell.xmission.com (Kenny McCormack)");
        String expResult = "gazelle@shell.xmission.com";
        String result = instance.getAddress();
        assertEquals(expResult, result);
    }
    
}
