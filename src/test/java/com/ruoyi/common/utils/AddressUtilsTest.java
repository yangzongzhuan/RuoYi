package com.ruoyi.common.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * AddressUtils Tester.
 *
 * @author Leonhardt
 * @version 1.0
 * @since 07/22/2018
 */

public class AddressUtilsTest
{

    @Before
    public void before() throws Exception
    {
    }

    @After
    public void after() throws Exception
    {
    }

    /**
     * Method: getRealAddressByIP(String ip)
     * <p>
     */
    @Test
    public void testGetRealAddressByIP() throws Exception
    {
        // TODO: Test goes here...
        String ipAddress = AddressUtils.getRealAddressByIP("121.8.250.1");
        System.out.println(ipAddress);
    }

}
