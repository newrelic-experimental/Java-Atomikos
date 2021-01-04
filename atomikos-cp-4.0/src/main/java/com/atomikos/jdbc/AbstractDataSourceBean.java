package com.atomikos.jdbc;

import java.sql.Connection;

import com.atomikos.datasource.pool.ConnectionFactory;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.atomikos.jdbc.DataSourceSampler;

@Weave(type=MatchType.BaseClass)
public abstract class AbstractDataSourceBean {
	
	@NewField
	protected boolean initialized = false;
	
	public abstract String getUniqueResourceName();
	
	public abstract int poolAvailableSize();

	public abstract int poolTotalSize();
	
	protected ConnectionFactory doInit() {
		if(!DataSourceSampler.getInstance().contains(this)) {
			DataSourceSampler.getInstance().addPool(this);
		}
		return Weaver.callOriginal();
	}

	protected void doClose() {
		if(DataSourceSampler.getInstance().contains(this)) {
			DataSourceSampler.getInstance().removePool(this);
		}

		Weaver.callOriginal();
	}

	public Connection getConnection() {
		if(!initialized) {
			if(!DataSourceSampler.getInstance().contains(this)) {
				DataSourceSampler.getInstance().addPool(this);
			}
			initialized = true;
		}
		return Weaver.callOriginal();
	}
}
