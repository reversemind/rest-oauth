package ru.ttk.baloo.rest.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

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
@Component
@Path("/service")
public class Simple {

    private final static Logger LOG = LoggerFactory.getLogger(Simple.class);

    @GET
	@Path("/create")
	public String createInfo(){
		return "/service/create";
	}
	
	@GET
	@Path("/get")
	public String getMyInfo(){
		return "/service/get";
	}

	@GET
	@Path("/update")
	public String updateMyInfo(){
		return "/service/update";
	}

}
