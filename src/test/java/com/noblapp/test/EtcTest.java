package com.noblapp.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.Assert;

import com.noblapp.support.AbstractTestCase;

public class EtcTest extends AbstractTestCase {

//	@Test
	public void weather(){
		Map<String, Object> cmd = new HashMap<String, Object>();
//		cmd.put("group_gsn", 1);
		Assert.isTrue(super.jsonData("/etc/weather", cmd));
	}
}
