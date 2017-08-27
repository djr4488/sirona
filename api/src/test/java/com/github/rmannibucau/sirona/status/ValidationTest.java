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
package com.github.rmannibucau.sirona.status;

import com.github.rmannibucau.sirona.configuration.ioc.IoCs;
import com.github.rmannibucau.sirona.spi.SpiTestImpl;
import com.github.rmannibucau.sirona.store.status.PeriodicNodeStatusDataStore;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;


public class ValidationTest {
    @Test
    public void globalStatus() {
        final Date date = new Date();
        Assert.assertEquals( Status.OK,
                             new NodeStatus( new ValidationResult[]{ new ValidationResult( null, Status.OK, null ) },
                                             date ).getStatus() );
        Assert.assertEquals( Status.KO,
                             new NodeStatus( new ValidationResult[]{ new ValidationResult( null, Status.KO, null ) },
                                             date ).getStatus() );
        Assert.assertEquals( Status.DEGRADED, new NodeStatus(
            new ValidationResult[]{ new ValidationResult( null, Status.DEGRADED, null ) }, date ).getStatus() );
        Assert.assertEquals( Status.KO, new NodeStatus(
            new ValidationResult[]{ new ValidationResult( null, Status.DEGRADED, null ),
                new ValidationResult( null, Status.KO, null ) }, date ).getStatus() );
        Assert.assertEquals( Status.DEGRADED, new NodeStatus(
            new ValidationResult[]{ new ValidationResult( null, Status.DEGRADED, null ),
                new ValidationResult( null, Status.OK, null ) }, date ).getStatus() );
    }

    @Test
    public void periodicNodeReporter() throws InterruptedException {
        final PeriodicNodeStatusDataStore store = IoCs.processInstance(new PeriodicNodeStatusDataStore() {
            protected int getPeriod(final String name) {
                return 100;
            }
        });
        Thread.sleep(200);
        Assert.assertEquals( Status.OK, store.statuses().values().iterator().next().getStatus() );

        SpiTestImpl.status = new ValidationResult("", Status.KO, "");
        Thread.sleep(200);
        Assert.assertEquals( Status.KO, store.statuses().values().iterator().next().getStatus() );
        store.shutdown();
    }
}
