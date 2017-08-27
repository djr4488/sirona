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
# Graphite

Graphite module allows to push counters and gauges to a graphite instance.

## Configuration

* `com.github.rmannibucau.sirona.graphite.GraphiteBuilder.address`: the graphite instance host/IP
* `com.github.rmannibucau.sirona.graphite.GraphiteBuilder.port`: the graphite instance port
* `com.github.rmannibucau.sirona.graphite.GraphiteBuilder.charset`: the charset to use with this Graphite instance

For instance your `sirona.properties` can look like:

<pre class="prettyprint linenums"><![CDATA[
com.github.rmannibucau.sirona.graphite.GraphiteBuilder.address = localhost
com.github.rmannibucau.sirona.graphite.GraphiteBuilder.port = 1234
]]></pre>

## DataStore

To push metrics (Gauges + Counters) to Graphite you can use the dedicated `DataStore`: `com.github.rmannibucau.sirona.graphite.GraphiteDataStore`.

Simply add to `sirona.properties` the line:

<pre class="prettyprint linenums"><![CDATA[
com.github.rmannibucau.sirona.store.DataStore = com.github.rmannibucau.sirona.graphite.GraphiteDataStore
]]></pre>

### Counters

You can also configure the period used to flush counters values:

* `com.github.rmannibucau.sirona.graphite.period`: which period to use to push counters data to Graphite (default to 1mn).

## Limitations

When using GraphiteDataStore you cannot retrieve locally gauges values (you are expected to use Graphite for it).
