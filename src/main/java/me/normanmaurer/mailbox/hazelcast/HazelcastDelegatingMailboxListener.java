/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package me.normanmaurer.mailbox.hazelcast;

import java.util.List;
import java.util.Map;

import org.apache.james.mailbox.MailboxListener;
import org.apache.james.mailbox.MailboxPath;
import org.apache.james.mailbox.store.AbstractDelegatingMailboxListener;

import com.hazelcast.core.Hazelcast;

/**
 * Use Hazelcast for distributed storage of {@link MailboxListener}
 * 
 * @author Norman Maurer
 *
 */
public class HazelcastDelegatingMailboxListener extends AbstractDelegatingMailboxListener {

    private final static String MAILBOX_LISTENERS = "mailboxListeners";
    private final static String GLOBAL_MAILBOX_LISTENERS = "globalMailboxListeners";

    @Override
    protected Map<MailboxPath, List<MailboxListener>> getListeners() {
        return Hazelcast.getMap(MAILBOX_LISTENERS);
    }

    @Override
    protected List<MailboxListener> getGlobalListeners() {
        return Hazelcast.getList(GLOBAL_MAILBOX_LISTENERS);
    }

}
