package com.jjsimpson.test.rob.comm.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jjsimpson.rob.comm.model.CommType;

public class CommTypeTest
{

	@Test(groups = "unit")
	public void constructorTest()
	{
		CommType cType1 = new CommType(0, 3);
		
		Assert.assertEquals(cType1.getFullType(), 3);
		Assert.assertEquals(cType1.getMainType(), 0);
		Assert.assertEquals(cType1.getSubType(), 3);
		
		CommType cType2 = new CommType(3);
		Assert.assertEquals(cType2.getFullType(), 3);
		Assert.assertEquals(cType2.getMainType(), 0);
		Assert.assertEquals(cType2.getSubType(), 3);
		
	}
}
