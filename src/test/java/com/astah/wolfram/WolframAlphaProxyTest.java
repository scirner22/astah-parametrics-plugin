package com.astah.wolfram;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class WolframAlphaProxyTest {
	private WolframAlphaProxy waProxy;
	
	@Before
	public void setUp() {
		waProxy = new WolframAlphaProxy();
	}
	
	@Test
	public void testBasicQuery() {
		String result = waProxy.query("1+1");
		
		assertEquals("2", result);
	}
	
	@Test
	public void testSummationQuery() {
		String result = waProxy.query("sum(1,5,10)");
		
		assertEquals("16", result);
	}
	
	@Test
	public void testMinimumQuery() {
		String result = waProxy.query("min(1, 2, 3)");
		
		assertEquals("1", result);
	}
}
