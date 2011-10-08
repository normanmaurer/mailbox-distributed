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

import org.apache.james.mailbox.MailboxListener;
import org.apache.james.mailbox.store.HashMapDelegatingMailboxListener;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.MessageListener;

/**
 * Use Hazelcast to dispatch {@link Event}'s to an {@link ITopic} and listen for events and notify the registered {@link MailboxListener} instances
 * 
 * @author Norman Maurer
 *
 */
public class HazelcastDelegatingMailboxListener extends HashMapDelegatingMailboxListener implements MessageListener<org.apache.james.mailbox.MailboxListener.Event>{

    private final static String MAILBOX_EVENT_TOPIC = "MAILBOX_EVENT_TOPIC";
    
    private ITopic<Event> eventTopic = Hazelcast.getTopic(MAILBOX_EVENT_TOPIC);
    
    public HazelcastDelegatingMailboxListener() {
        eventTopic.addMessageListener(this);
    }
    @Override
    public void event(Event event) {
        eventTopic.publish(event);

    }
    @Override
    public void onMessage(org.apache.james.mailbox.MailboxListener.Event  event) {
        super.event(event);
          
    }


}
