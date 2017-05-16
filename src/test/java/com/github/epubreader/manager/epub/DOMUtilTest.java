package com.github.epubreader.manager.epub;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(Enclosed.class)
public class DOMUtilTest {

	public static class GetAttribute {
		
		@Test
		public void test_simple_foo() {
			// given
			String input = "<?xml version=\"1.0\"?><doc xmlns:a=\"foo\" xmlns:b=\"bar\" a:myattr=\"red\" b:myattr=\"green\"/>";

			try {
				Document document = EpubProcessorSupport.createDocumentBuilder().parse(new InputSource(new StringReader(input)));
				
				// when
				String actualResult = DOMUtil.getAttribute(document.getDocumentElement(), "foo", "myattr");
				
				// then
				assertEquals("red", actualResult);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
		
		@Test
		public void test_simple_bar() {
			// given
			String input = "<?xml version=\"1.0\"?><doc xmlns:a=\"foo\" xmlns:b=\"bar\" a:myattr=\"red\" b:myattr=\"green\"/>";

			try {
				Document document = EpubProcessorSupport.createDocumentBuilder().parse(new InputSource(new StringReader(input)));
				
				// when
				String actualResult = DOMUtil.getAttribute(document.getDocumentElement(), "bar", "myattr");
				
				// then
				assertEquals("green", actualResult);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
}
