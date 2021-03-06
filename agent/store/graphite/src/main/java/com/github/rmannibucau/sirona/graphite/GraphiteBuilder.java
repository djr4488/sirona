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
package com.github.rmannibucau.sirona.graphite;

import com.github.rmannibucau.sirona.SironaException;
import com.github.rmannibucau.sirona.configuration.ioc.AutoSet;

import javax.net.SocketFactory;
import java.io.IOException;
import java.nio.charset.Charset;

@AutoSet
public class GraphiteBuilder {
    private String address;
    private int port;
    private String charset;

    public synchronized Graphite build() {
        if (charset == null) {
            charset = "UTF-8";
        }
        try {
            return new Graphite(SocketFactory.getDefault(), address, port, Charset.forName(charset));
        } catch (final IOException e) {
            throw new SironaException(e);
        }
    }
}
