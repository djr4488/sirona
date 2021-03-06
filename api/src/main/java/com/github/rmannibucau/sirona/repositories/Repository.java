/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rmannibucau.sirona.repositories;

import com.github.rmannibucau.sirona.Role;
import com.github.rmannibucau.sirona.configuration.ioc.IoCs;
import com.github.rmannibucau.sirona.counters.Counter;
import com.github.rmannibucau.sirona.gauges.Gauge;
import com.github.rmannibucau.sirona.status.NodeStatus;
import com.github.rmannibucau.sirona.stopwatches.StopWatch;

import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;

public interface Repository {
    Repository INSTANCE = IoCs.findOrCreateInstance(Repository.class);

    Counter getCounter(Counter.Key key);

    Collection<Counter> counters();

    void clearCounters();

    void reset();

    StopWatch start(Counter counter);

    void addGauge(final Gauge gauge);

    void stopGauge(Gauge role);

    SortedMap<Long, Double> getGaugeValues(long start, long end, Role role);

    Collection<Role> gauges();

    Role findGaugeRole(String name);

    Map<String, NodeStatus> statuses();

}
