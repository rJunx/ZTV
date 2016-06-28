package test;

import static org.junit.Assert.*;

import org.junit.Test;

import ztv.cli.HttpClientFetcher;
import ztv.core.IFetcher;
import ztv.core.TestConfig;

public class TestFetcher {

	@Test
	public void test() {
		IFetcher fetcher = new HttpClientFetcher();
		
		try {
			fetcher.start(TestConfig.url, TestConfig.user, TestConfig.pwd);
			assertEquals(200, fetcher.getStatusCode());
			System.out.println(fetcher.getText());
		} catch (Exception e) {
			fail(e.getMessage());
		}		
	}

}
