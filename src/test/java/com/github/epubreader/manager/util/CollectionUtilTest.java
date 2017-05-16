package com.github.epubreader.manager.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectionUtilTest {

	@Test
	public void testIsEmpty_null() {
		Assert.assertTrue(CollectionUtil.isEmpty(null));
	}

	@Test
	public void testIsEmpty_empty() {
		Assert.assertTrue(CollectionUtil.isEmpty(new ArrayList<Object>()));
	}

	@Test
	public void testIsEmpty_elements() {
		Assert.assertFalse(CollectionUtil.isEmpty(Arrays.asList("foo")));
	}
}
