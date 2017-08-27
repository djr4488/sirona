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
package com.github.rmannibucau.sirona.store.status;

import com.github.rmannibucau.sirona.alert.AlertListener;
import com.github.rmannibucau.sirona.status.NodeStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// this is empty but updatable
public class EmptyStatuses implements NodeStatusDataStore {
    private Map<String, NodeStatus> statuses = new ConcurrentHashMap<String, NodeStatus>();

    @Override
    public Map<String, NodeStatus> statuses() {
        return statuses;
    }

    @Override
    public void reset() {
        // no-op
    }

    public void addAlerter(AlertListener listener) {
        noAlert();
    }

    public void removeAlerter(final AlertListener listener) {
        noAlert();
    }

    private void noAlert() {
        throw new UnsupportedOperationException(getClass().getSimpleName() + " doesn't support alerts");
    }
}
