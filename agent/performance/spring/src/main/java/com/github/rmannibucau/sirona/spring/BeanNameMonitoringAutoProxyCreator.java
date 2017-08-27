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

package com.github.rmannibucau.sirona.spring;

import com.github.rmannibucau.sirona.aop.DefaultMonitorNameExtractor;
import com.github.rmannibucau.sirona.aop.MonitorNameExtractor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;

public class BeanNameMonitoringAutoProxyCreator extends BeanNameAutoProxyCreator {
    private MonitorNameExtractor counterNameExtractor = DefaultMonitorNameExtractor.INSTANCE;

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(final Class beanClass, final String beanName, final TargetSource targetSource) {
        if (super.getAdvicesAndAdvisorsForBean(beanClass, beanName, targetSource) != DO_NOT_PROXY) {
            final AopaliancePerformanceInterceptor interceptor = new AopaliancePerformanceInterceptor();
            interceptor.setMonitorNameExtractor(counterNameExtractor);
            return new Object[] { interceptor };
        }
        return DO_NOT_PROXY;
    }

    public void setCounterNameExtractor(final MonitorNameExtractor counterNameExtractor) {
        this.counterNameExtractor = counterNameExtractor;
    }

    public MonitorNameExtractor getCounterNameExtractor() {
        return counterNameExtractor;
    }
}
