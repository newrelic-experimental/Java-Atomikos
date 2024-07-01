package com.nr.instrumentation.atomikos.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.atomikos.jdbc.internal.AbstractDataSourceBean;
import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weaver;

public class DataSourceSampler implements Runnable {
	
	private static List<AbstractDataSourceBean> pools;
	
	private static String METRIC_PREFIX = "Custom/Atomikos Connection Pools/JDBC/";
	
	private static DataSourceSampler instance = null;
	
	public static DataSourceSampler getInstance() {
		if(instance == null) {
			instance = new DataSourceSampler();
		}
		return instance;
	}
	
	public void addPool(AbstractDataSourceBean b) {
		if(!pools.contains(b)) {
			pools.add(b);
		}
	}
	
	public boolean contains(AbstractDataSourceBean b) {
		return pools.contains(b);
	}
	
	public boolean removePool(AbstractDataSourceBean b) {
		return pools.remove(b);
	}
	
	private DataSourceSampler() {
		pools = new ArrayList<AbstractDataSourceBean>();
		AgentBridge.instrumentation.registerCloseable(Weaver.getImplementationTitle(),AgentBridge.privateApi.addSampler(this, 15, TimeUnit.SECONDS));
	}

	@Override
	public void run() {
		for(AbstractDataSourceBean pool : pools) {
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
