/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vavasthi
 */
public class UsenetEmailAddressTest {
    
    public UsenetEmailAddressTest() {
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
        UsenetEmailAddress instance = new UsenetEmailAddress("James Kuyper <jameskuyper@verizon.net>");
        String expResult = "James Kuyper";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAddress method, of class UsenetEmailAddress.
     */
    @Test
    public void testGetAddress() {
        System.out.println("getAddress");
        UsenetEmailAddress instance = new UsenetEmailAddress("James Kuyper <jameskuyper@verizon.net>");
        String expResult = "jameskuyper@verizon.net";
        String result = instance.getAddress();
        assertEquals(expResult, result);
    }
    
}
