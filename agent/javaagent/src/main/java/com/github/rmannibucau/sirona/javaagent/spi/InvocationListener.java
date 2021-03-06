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
package com.github.rmannibucau.sirona.javaagent.spi;

import com.github.rmannibucau.sirona.javaagent.AgentContext;

public interface InvocationListener {

    /**
     *
     * @param key has the format of fqcn.methodName
     * @param rawClassBuffer class being trasformed
     * @return to use this InvocationListener or not for this method
     */
    boolean accept(String key, byte[] rawClassBuffer);

    /**
     * Method called before the real object method called
     * @param context
     */
    void before(AgentContext context);

    /**
     *
     * @param context the {@link com.github.rmannibucau.sirona.javaagent.AgentContext used for this listener}
     * @param result  the object return by the method can be <code>null</code> if void method
     * @param error   the exception (if any) throw by the method
     */
    void after(AgentContext context, Object result, Throwable error);


}
