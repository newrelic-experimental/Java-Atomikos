package com.atomikos.jdbc;

import javax.sql.XADataSource;

import com.atomikos.datasource.pool.XPooledConnection;
import com.atomikos.jdbc.internal.AbstractDataSourceBean;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.atomikos.jdbc.DataSourceSampler;
import com.atomikos.datasource.pool.ConnectionPoolProperties;

@Weave
public abstract class AtomikosDataSourceBean extends AbstractDataSourceBean {

	public XADataSource getXaDataSource() {
		if(!initialized && !DataSourceSampler.getInstance().contains(this)) {
			DataSourceSampler.getInstance().addPool(this);
		}
		return Weaver.callOriginal();
	}
	
	
	@Weave
	private static class  AtomikosXAConnectionFactory {

		private final ConnectionPoolProperties props = Weaver.callOriginal();
		
		@SuppressWarnings({ "rawtypes", "unused" })
		public XPooledConnection createPooledConnection() {
			String poolName = props.getUniqueResourceName();
			String metricName = null;
			if(poolName != null && !poolName.isEmpty()) {
				metricName = "Custom/Atomikos Connection Pools/JDBC/NonXA/" + poolName + "/createPooledConnection";
			} else {
				metricName = "Custom/Atomikos Connection Pools/JDBC/NonXA/UnknownPool/createPooledConnection";
			}
			NewRelic.recordMetric(metricName, 1);
			return Weaver.callOriginal();
		}

	}

}
