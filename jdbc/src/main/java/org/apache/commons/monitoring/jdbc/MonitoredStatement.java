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

package org.apache.commons.monitoring.jdbc;

import org.apache.commons.monitoring.Role;
import org.apache.commons.monitoring.monitors.Monitor;
import org.apache.commons.monitoring.repositories.Repository;
import org.apache.commons.monitoring.stopwatches.StopWatch;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author <a href="mailto:ndeloof@sourceforge.net">Nicolas De Loof</a>
 */
public class MonitoredStatement implements InvocationHandler {
    private final Statement statement;

    public MonitoredStatement(final Statement statement) {
        this.statement = statement;
    }

    protected SQLException monitor(final SQLException sqle) {
        final String name = "SQLException:" + sqle.getSQLState() + ":" + sqle.getErrorCode();
        final Monitor monitor = Repository.INSTANCE.getMonitor(name, "jdbc");
        monitor.getCounter(Role.FAILURES).add(1);
        return sqle;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final String name = method.getName();
        if (name.startsWith("execute")) {
            final StopWatch stopWatch;
            if (name.endsWith("Batch") && (args == null || args.length == 0)) {
                stopWatch = Repository.INSTANCE.start(Repository.INSTANCE.getMonitor("batch", "jdbc"));
            } else {
                stopWatch = Repository.INSTANCE.start(Repository.INSTANCE.getMonitor((String) args[0], "jdbc"));
            }

            try {
                return doInvoke(method, args);
            } catch (final InvocationTargetException e) {
                throw extractSQLException(e);
            } finally {
                stopWatch.stop();
            }
        }
        return doInvoke(method, args);
    }

    private Object doInvoke(final Method method, final Object[] args) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(statement, args);
    }

    protected Throwable extractSQLException(final InvocationTargetException e) throws Throwable {
        final Throwable th = e.getCause();
        if (SQLException.class.isInstance(th)) {
            return monitor(SQLException.class.cast(th));
        }
        return th;
    }
}