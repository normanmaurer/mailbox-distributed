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

import java.util.concurrent.locks.Lock;

import org.apache.james.mailbox.MailboxException;
import org.apache.james.mailbox.MailboxPath;
import org.apache.james.mailbox.MailboxSession;
import org.apache.james.mailbox.store.AbstractMailboxPathLocker;

import com.hazelcast.core.Hazelcast;

/**
 * Use Hazelcast for a distributed Lock. The {@link MailboxPath#toString()} is used as key
 * 
 * @author Norman Maurer
 *
 */
public class HazelcastMailboxPathLocker extends AbstractMailboxPathLocker{


    /*
     * (non-Javadoc)
     * @see org.apache.james.mailbox.store.AbstractMailboxPathLocker#lock(org.apache.james.mailbox.MailboxSession, org.apache.james.mailbox.MailboxPath)
     */
    protected void lock(MailboxSession session, MailboxPath path) throws MailboxException {
        Lock lock = Hazelcast.getLock(path.getFullName(session.getPathDelimiter()));
        lock.lock();        
    }

    /*
     * (non-Javadoc)
     * @see org.apache.james.mailbox.store.AbstractMailboxPathLocker#unlock(org.apache.james.mailbox.MailboxSession, org.apache.james.mailbox.MailboxPath)
     */
    protected void unlock(MailboxSession session, MailboxPath path) throws MailboxException {
        Lock lock = Hazelcast.getLock(path.getFullName(session.getPathDelimiter()));
        lock.unlock();
    }

}
