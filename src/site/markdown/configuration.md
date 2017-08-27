<!---
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->
## Configuration features
### Location

Configuration relies on `sirona.properties` file which should be found in the sirona-core classloader.

Note: you can change the file name using `com.github.rmannibucau.sirona.configuration` system property.

### Advanced configuration

Configuration relies in fact on a SPI: `com.github.rmannibucau.sirona.configuration.ConfigurationProvider`.

This interface providers two method:

* `ordinal`: use to sort providers. The lowest is the first.
* `configuration`: returns the properties to add in the global configuration

Note: by default a provider for `sirona.properties` (ordinal = 50) and one for system properties (ordinal = 100) are added.

### Utilities

`com.github.rmannibucau.sirona.configuration.Configuration` has several utility methods to get
int, boolean... from the properties file. You can reuse it in your sirona extensions if you want.

It will be commonly used with `com.github.rmannibucau.sirona.configuration.ioc.IoCs` class.
This last allows a basic lifecycle handling of your objects.
Using `com.github.rmannibucau.sirona.configuration.ioc.IoCs.newInstance` method you can
decorate your class methods with `com.github.rmannibucau.sirona.configuration.Configuration.Created`
and `com.github.rmannibucau.sirona.configuration.ioc.Destroying` to get lifecycle hooks.

`Destroying` is called when the monitoring is stopped. Generally since sirona-core is deployed in the
container or JVM classloader it is with the JVM but sometimes you can deploy it in your application. In this case
you'll need to either configure the `javax.servlet.ServletContextListener`
`com.github.rmannibucau.sirona.web.lifecycle.SironaLifecycle` from reporting module
or to call manually `Configuration.shutdown()` method.

You can also use `com.github.rmannibucau.sirona.configuration.ioc.AutoSet` to init field of an instance using sirona configuration.

### Main configuration keys (by module)

#### Core

* com.github.rmannibucau.sirona.configuration: the configuration file path if not using the default
* com.github.rmannibucau.sirona.shutdown.hook: boolean, true by default. Should be set to false when deploying sirona-core in an application (see Utilities part).
* com.github.rmannibucau.sirona.gauge.max-size: int, 100 by default. Number of gauge measures to keep in memory when not persistent.
* com.github.rmannibucau.sirona.gauge.memory.period: int, 4000 (ms) by default. Period for memory gauge.
* com.github.rmannibucau.sirona.gauge.cpu.period: int, 4000 (ms) by default. Period for CPU gauge.
* com.github.rmannibucau.sirona.store.DataStoreFactory: qualified class name, default `com.github.rmannibucau.sirona.store.DefaultDataStoreFactory`. DataStoreFactory to use.
* com.github.rmannibucau.sirona.repositories.Repository: qualified class name, default `com.github.rmannibucau.sirona.repositories.DefaultRepository`. Repository to use.
* com.github.rmannibucau.sirona.core.gauge.activated: a boolean to deactivate cpu/memory gauges
* com.github.rmannibucau.sirona.\<name>.period: the period to use to flush counters for a batch data store (like graphite one)
* com.github.rmannibucau.sirona.periodic.status.period: the period to use for status reporting. Note: when using another reporter (cube typically) you'll need to replace `periodic` by the specific name of the reporter (`cube`). Note too that `period` is optional to allow to share the same period between all stores.
* com.github.rmannibucau.sirona.\<name>.gauge.period: the default period defining when gauge snapshots are taken (default name = `inmemory`)
* com.github.rmannibucau.sirona.\<name>.aggregated.gauge.period: the default period defining when gauge snapshots are persisted/pushed (ex: graphite)
* com.github.rmannibucau.sirona.counter.with-gauge: a boolean to ask to register for each counter a gauge for the max/sum/hists metrics of the counter. Only works with in memory counter stores (agent ones in general).
* com.github.rmannibucau.sirona.counter.with-jmx: a boolean to ask to register a MBean for each counter. Only works with in memory counter stores.
* com.github.rmannibucau.sirona.\<name>.counter.clearOnCollect: boolean to clear counter after each push/pull


#### Reporting

* org.apache.commons.proxy.ProxyFactory: qualified class name. ProxyFactory to use for client aop.
* [plugin name].activated: boolean, true by default. Should the plugin referenced by [plugin.name] be used.
* com.github.rmannibucau.sirona.jmx.method.allowed: boolean, true by default. Are JMX method invocation allowed.
* com.github.rmannibucau.sirona.gauge.csv.separator: char, ';' by default. CSV separator for CSV report.
* com.github.rmannibucau.sirona.gauge.jta.period: jta gauge period
* com.github.rmannibucau.sirona.gauge.memory.period: memory gauge period
* com.github.rmannibucau.sirona.gauge.cpu.period: cpu gauge period
* com.github.rmannibucau.sirona.reporting.activated: if auto deployment of reporting module is activated
* com.github.rmannibucau.sirona.reporting.mapping: the mapping of monitoring GUI

#### Web

* com.github.rmannibucau.sirona.web.activated: if auto deployment of web module is activated
* com.github.rmannibucau.sirona.web.monitored-urls: the mapping of monitored urls
* com.github.rmannibucau.sirona.web.gauge.sessions.period: the gauge period for sessions number monitoring
* com.github.rmannibucau.sirona.web.gauge.status.period: when status monitoring is activated the period for status gauges
* com.github.rmannibucau.sirona.web.monitored-statuses: the comma separated list of monitored statuses (if not a default list is used)

#### CDI

* com.github.rmannibucau.sirona.cdi.enabled: a boolean to activate/deactivate CDI interceptors config
* com.github.rmannibucau.sirona.cdi.performance: list of intercepted beans for performances (prefix:org.superbiz, regex:.*Service...)
* com.github.rmannibucau.sirona.cdi.jta: list of intercepted beans for JTA

# TomEE

* com.github.rmannibucau.sirona.tomee.gauges.activated: a boolean to deactivate tomee guages (stateless pool stat)
* com.github.rmannibucau.sirona.tomee.validations.activated: a boolean to deactivate tomee validations (datasource validation by validation query)

# Pull

* com.github.rmannibucau.sirona.agent.pull.mapping: the servlet mapping, default to `/sirona/pull`

# Collector

* `com.github.rmannibucau.sirona.collector.collection.period`: the timer period (in ms) when used in pull mode
* `com.github.rmannibucau.sirona.collector.collection.agent-urls`: comma separated value of agent urls when auto registering is not used by agents
* `com.github.rmannibucau.sirona.collector.server.api.SecurityProvider`: the basic information provider requests on agents
* `com.github.rmannibucau.sirona.collector.server.api.SSLSocketFactoryProvider`: the ssl socket factory provider when contacting agents over ssl

# cassandra

* `com.github.rmannibucau.sirona.cassandra.CassandraBuilder.hosts`: comma separated list of hosts (IP:port)
* `com.github.rmannibucau.sirona.cassandra.CassandraBuilder.cluster`: sirona cluster name
* `com.github.rmannibucau.sirona.cassandra.CassandraBuilder.keyspace`: sirona keyspace name
* `com.github.rmannibucau.sirona.cassandra.CassandraBuilder.counterColumnFamily`: sirona counter column family name
* `com.github.rmannibucau.sirona.cassandra.CassandraBuilder.gaugeValuesColumnFamily`: sirona gauge column family name to store data
* `com.github.rmannibucau.sirona.cassandra.CassandraBuilder.statusColumnFamily`: sirona statuses column family name
* `com.github.rmannibucau.sirona.cassandra.CassandraBuilder.markerGaugesColumFamily`: sirona gauge by marker (= instance) column family name
* `com.github.rmannibucau.sirona.cassandra.CassandraBuilder.replicationFactor`: replication factory for cassandra client instance
