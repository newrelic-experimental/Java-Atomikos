package com.atomikos.jdbc;

import com.atomikos.datasource.pool.ConnectionPoolProperties;
import com.atomikos.datasource.pool.XPooledConnection;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;


@Weave
public abstract class AtomikosNonXADataSourceBean {

	@Weave
	private static class AtomikosNonXAConnectionFactory {
		private final ConnectionPoolProperties props = Weaver.callOriginal();

		@SuppressWarnings({ "unused", "rawtypes" })
		public XPooledConnection createPooledConnection() {
			String poolName = props.getUniqueResourceName();
			String metricName = null;
			if(poolName != null && !poolName.isEmpty()) {
				metricName = "Custom/Atomikos Connection Pools/JDBC/XA/" + poolName + "/createPooledConnection";
			} else {
				metricName = "Custom/Atomikos Connection Pools/JDBC/XA/UnknownPool/createPooledConnection";
			}
			NewRelic.recordMetric(metricName, 1);
			return Weaver.callOriginal();
		}
	}
}
