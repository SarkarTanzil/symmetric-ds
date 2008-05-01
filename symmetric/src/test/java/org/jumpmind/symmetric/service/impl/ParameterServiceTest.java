package org.jumpmind.symmetric.service.impl;

import org.jumpmind.symmetric.AbstractDatabaseTest;
import org.jumpmind.symmetric.common.ParameterConstants;
import org.jumpmind.symmetric.common.TestConstants;
import org.jumpmind.symmetric.service.IParameterService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParameterServiceTest extends AbstractDatabaseTest {

    @Test(groups="continuous")
    public void testParameterGetFromDefaults() {
       Assert.assertEquals(getParameterService().getString(ParameterConstants.RUNTIME_CONFIG_TABLE_PREFIX), "sym", "Unexpected default table prefix found.");
    }
    
    
    @Test(groups="continuous")
    public void testParameterGetFromDatabase() {
       Assert.assertEquals(getParameterService().getInt(ParameterConstants.CONCURRENT_WORKERS), 2);       
       getParameterService().saveParameter(TestConstants.TEST_CLIENT_EXTERNAL_ID, TestConstants.TEST_CLIENT_NODE_GROUP, ParameterConstants.CONCURRENT_WORKERS, 10);
       
       // make sure we are not picking up someone else's parameter
       Assert.assertEquals(getParameterService().getInt(ParameterConstants.CONCURRENT_WORKERS), 2);
       
       getParameterService().saveParameter(IParameterService.ALL, TestConstants.TEST_ROOT_NODE_GROUP, ParameterConstants.CONCURRENT_WORKERS, 5);
       
       Assert.assertEquals(getParameterService().getInt(ParameterConstants.CONCURRENT_WORKERS), 5);
       
       getParameterService().saveParameter(ParameterConstants.CONCURRENT_WORKERS, 10);
       
       Assert.assertEquals(getParameterService().getInt(ParameterConstants.CONCURRENT_WORKERS), 10);
    }
    
    @Test(groups="continuous")
    public void testBooleanParameter() {
        Assert.assertEquals(getParameterService().is("boolean.test"), false);
        getParameterService().saveParameter("boolean.test", true);
        Assert.assertEquals(getParameterService().is("boolean.test"), true);
    }

    
}
