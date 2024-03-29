/**
 * Mule CloudHub Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.cloudhub.automation.testcases;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import org.junit.*;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.transformer.TransformerException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.mule.api.MuleEvent;
import org.mule.tck.junit4.FunctionalTestCase;


import com.mulesoft.cloudhub.client.Application;

public class GetApplication extends FunctionalTestCase {
	
	private ApplicationContext data_objects;
	private Map<String,Object> operation_sandbox;
	
	@Override
	protected String getConfigResources() {
		return "automation/automation-test-flows.xml";
	}
	
    private MessageProcessor lookupFlowConstruct(String name) {
        return (MessageProcessor) muleContext.getRegistry().lookupFlowConstruct(name);
    }
	
    @Before
    public void setUp() {
    	data_objects = new ClassPathXmlApplicationContext("automation/Applications.xml");
    	operation_sandbox = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
    	
    }
    
	@Test
	public void getApp() {
		
		try {
			Application sandbox_application = (Application) data_objects.getBean("applicationA");
			
			MessageProcessor sandboxFlow = lookupFlowConstruct("get-application");
			MuleEvent response = sandboxFlow.process(getTestEvent(sandbox_application.getDomain()));
			//
			
			Application flow_response = (Application) response.getMessage().getPayload();
			
			assertTrue("Objects should be an Application", flow_response instanceof Application);
			assertEquals("Domains dont match",flow_response.getDomain(),sandbox_application.getDomain());
			assertEquals("mule version dont match",flow_response.getMuleVersion(),sandbox_application.getMuleVersion());
		} catch (MuleException e) {
			e.printStackTrace();
			fail();
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	        	
	}
	
}
