package com.ruoyi.common.utils;
 
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** 
* AddressUtils Tester. 
* 
* @author Leonhardt
* @since 07/22/2018
* @version 1.0 
*/ 

public class AddressUtilsTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getRealAddressByIP(String ip) 
* 
* $method.annotation
*/ 
@Test
public void testGetRealAddressByIP() throws Exception { 
    //TODO: Test goes here...
    String ipAddress = AddressUtils.getRealAddressByIP("121.8.250.154");
    System.out.println(ipAddress);
} 


} 
