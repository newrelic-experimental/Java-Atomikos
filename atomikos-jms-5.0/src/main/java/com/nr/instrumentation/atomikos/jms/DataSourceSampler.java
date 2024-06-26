package com.nr.instrumentation.atomikos.jms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.atomikos.jms.AtomikosConnectionFactoryBean;
import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weaver;

public class DataSourceSampler implements Runnable {
	
	private static List<AtomikosConnectionFactoryBean> pools;
	
	private static String METRIC_PREFIX = "Custom/Atomikos Connection Pools/JMS/";
	
	private static DataSourceSampler instance = null;
	
	public static DataSourceSampler getInstance() {
		if(instance == null) {
			instance = new DataSourceSampler();
		}
		return instance;
	}
	
	public boolean contains(AtomikosConnectionFactoryBean b) {
		return pools.contains(b);
	}
	
	public void addPool(AtomikosConnectionFactoryBean b) {
		if(!pools.contains(b)) {
			pools.add(b);
		}
	}
	
	public boolean removePool(AtomikosConnectionFactoryBean b) {
		return pools.remove(b);
	}
	
	private DataSourceSampler() {
		pools = new ArrayList<AtomikosConnectionFactoryBean>();
		AgentBridge.instrumentation.registerCloseable(Weaver.getImplementationTitle(),AgentBridge.privateApi.addSampler(this, 15, TimeUnit.SECONDS));
	}

	@Override
	public void run() {
		NewRelic.recordMetric(METRIC_PREFIX+"Pools Monitored", pools.size());
		for(AtomikosConnectionFactoryBean pool : pools) {
			String poolName = pool.getUniqueResourceName();
			int available = pool.poolAvailableSize();
			int total = pool.poolTotalSize();
			String metricName = METRIC_PREFIX + poolName + "/Available";
			NewRelic.recordMetric(metricName, available);
			metricName = METRIC_PREFIX + poolName + "/Total";
			NewRelic.recordMetric(metricName, total);
			
		}
	}

}
