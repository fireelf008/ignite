package com.test.ignite;

import com.test.ignite.config.IgniteProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.*;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class IgniteTest {

	private final static String CACHE_NAME = "MY_CACHE";

	@Resource
	private IgniteProperties igniteProperties;

	@Before
	public void active(){
		Ignite ignite = Ignition.ignite(igniteProperties.getInstanceName());
		ignite.getOrCreateCache(getCacheConfiguration());
		ignite.cluster().active(true);
		log.info("is wal enabled == {}", ignite.cluster().isWalEnabled(CACHE_NAME));
	}

	@Test
	public void testSeq(){
		Ignite ignite = Ignition.ignite(igniteProperties.getInstanceName());
		final IgniteAtomicSequence seq = ignite.atomicSequence("seqName", 0, true);
		for (int i = 0; i < 20; i++) {
			long currentValue = seq.get();
			long newValue = seq.incrementAndGet();
			System.out.println(currentValue);
			System.out.println(newValue);
		}
	}

	@Test
	public void testCache(){
		Ignite ignite = Ignition.ignite(igniteProperties.getInstanceName());
		IgniteCache<String, String> cache = ignite.getOrCreateCache(getCacheConfiguration());
		ignite.cluster().enableWal(CACHE_NAME);
		IgniteTransactions transactions = ignite.transactions();
		Transaction tx = transactions.txStart();
		try {
			for (int i= 0; i<20000; i++){
				cache.put("KEY"+i, "VALUE"+i);
			}
			tx.commit();
			log.info("===================success");
		} catch (Exception e){
			tx.rollback();
			log.info("===================rollback");
		} finally {
			tx.close();
			log.info("===================close");
		}
	}

	@Test
	public void testGetCache(){
		long start = System.currentTimeMillis();
		Ignite ignite = Ignition.ignite(igniteProperties.getInstanceName());
		IgniteCache<String, String> cache = ignite.getOrCreateCache(getCacheConfiguration());
		ignite.cluster().enableWal(CACHE_NAME);
		for (int i= 0; i<20000; i++){
			//System.out.println("============" + cache.get("KEY"+i));
			cache.get("KEY"+i);
		}

		long end = System.currentTimeMillis();

		log.info("cost time millis {}", end - start);
	}

	private CacheConfiguration<String, String> getCacheConfiguration(){
		CacheConfiguration<String, String> cacheConfiguration = new CacheConfiguration<>();
		cacheConfiguration.setName(CACHE_NAME);
		cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
		cacheConfiguration.setBackups(2);
		cacheConfiguration.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
		return cacheConfiguration;
	}
}
