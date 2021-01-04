package com.atomikos.jdbc.nonxa;

import com.atomikos.datasource.pool.ConnectionPoolProperties;
import com.atomikos.datasource.pool.XPooledConnection;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
abstract class AtomikosNonXAConnectionFactory {

	private ConnectionPoolProperties props = Weaver.callOriginal();
	
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
