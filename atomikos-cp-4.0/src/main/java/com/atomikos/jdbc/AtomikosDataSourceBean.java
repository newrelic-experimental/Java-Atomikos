package com.atomikos.jdbc;

import javax.sql.XADataSource;

import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.atomikos.jdbc.DataSourceSampler;

@Weave
public abstract class AtomikosDataSourceBean extends AbstractDataSourceBean {

	public XADataSource getXaDataSource() {
		if(!initialized && !DataSourceSampler.getInstance().contains(this)) {
			DataSourceSampler.getInstance().addPool(this);
		}
		return Weaver.callOriginal();
	}
}
