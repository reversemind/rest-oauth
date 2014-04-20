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
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/resources")
public class SimpleRestService {

    private final static Logger LOG = LoggerFactory.getLogger(SimpleRestService.class);

    @RequestMapping(value = "/simple", method = RequestMethod.GET)
    public
    @ResponseBody
    String createInfo() {
        LOG.info("touched for /resources/simple - VIA OAUTH2");


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOG.info("currentUser:" + principal);
        if(principal != null){
            IRemoteUser remoteUser = (IRemoteUser)principal;
            if(StringUtils.isNotBlank(remoteUser.getRoles())){
                LOG.info("User roles are:" + remoteUser.getRoles());
                if(remoteUser.getRoles().indexOf(SecurityConstants.ROLE_EXTERNAL_REST_CLIENT) >=0 ){
                    LOG.info("USER IS AUTHENTICATED");
                }else{
                    throw new InsufficientAuthenticationException("InsufficientAuthenticationException ");
                }
            }
        }

        return "touched for /resources/simple - VIA OAUTH2";
    }
}
