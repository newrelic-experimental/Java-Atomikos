package com.atomikos.jms;

import javax.jms.Connection;

import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.atomikos.jms.DataSourceSampler;

@Weave
public abstract class AtomikosConnectionFactoryBean {

	public abstract String getUniqueResourceName();

	public abstract int poolAvailableSize();

	public abstract int poolTotalSize();
	
	@NewField
	private boolean initialized = false;
	
	
	public Connection createConnection() {
		if(!initialized) {
			if(!DataSourceSampler.getInstance().contains(this)) {
				DataSourceSampler.getInstance().addPool(this);
			}
			initialized = true;
		}
		return Weaver.callOriginal();
	}
	
	public AtomikosConnectionFactoryBean() {
		DataSourceSampler.getInstance().addPool(this);
		initialized = true;
	}
	
	
	public synchronized void close() {
		DataSourceSampler.getInstance().removePool(this);
		Weaver.callOriginal();
	}
}
