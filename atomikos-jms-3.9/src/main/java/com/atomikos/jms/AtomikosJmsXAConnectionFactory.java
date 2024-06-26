package com.atomikos.jms;

import com.atomikos.datasource.pool.XPooledConnection;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
abstract class AtomikosJmsXAConnectionFactory {

	private AtomikosConnectionFactoryBean atomikosConnectionFactory = Weaver.callOriginal();
	
	public XPooledConnection createPooledConnection() {
		String poolName = atomikosConnectionFactory != null ? atomikosConnectionFactory.getUniqueResourceName() : null;
		String metricName = null;
		if(poolName != null && !poolName.isEmpty()) {
			metricName = "Custom/Atomikos Connection Pools/JMS/" + poolName + "/createPooledConnection";
		} else {
			metricName = "Custom/Atomikos Connection Pools/JMS/UnknownPool/createPooledConnection";
		}
		NewRelic.recordMetric(metricName, 1);
		return Weaver.callOriginal();
	}
}
