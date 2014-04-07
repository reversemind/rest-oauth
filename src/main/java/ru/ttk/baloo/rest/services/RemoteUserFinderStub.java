/**
 * Copyright (c) 2013-2014 Eugene Kalinin
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.ttk.baloo.rest.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.ttk.baloo.rest.security.oauth.SampleUser;

/**
 * Remote emulation
 */
@Service
public class RemoteUserFinderStub implements IRemoteUserFinder {

    private final static Logger LOG = LoggerFactory.getLogger(RemoteUserFinderStub.class);

    // TODO cache
    public IRemoteUser findUser(String userName, String password) {

        LOG.debug("Going to find user:" + userName + " password:" + password);

        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
            if (userName.equals(SampleUser.USERNAME) && password.equals(SampleUser.PASSWORD)) {
                return new RemoteUser(SampleUser.USERNAME, SampleUser.PASSWORD, "");
            }
        }

        return null;
    }

    /**
     * Stub
     *
     * @param userName
     * @return
     */
    public RemoteUser findUser(String userName) {
        LOG.debug("Going to find user:" + userName);

        if (StringUtils.isNotBlank(userName)) {
            if (userName.equals(SampleUser.USERNAME)) {
                return new RemoteUser(SampleUser.USERNAME, SampleUser.PASSWORD, "");
            }
        }

        return null;
    }

}
