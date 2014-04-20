package ru.ttk.baloo.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ttk.baloo.rest.dto.HouseDTO;

import java.security.Principal;

/**
 *
 */
@Controller
@RequestMapping("/resources/create")
public class JSONDTOSimpleController {

    private final static Logger LOG = LoggerFactory.getLogger(JSONDTOSimpleController.class);

    @RequestMapping(value = "/house", method = RequestMethod.POST, consumes="application/json")
    public
    @ResponseBody
    String createHouse(@RequestBody HouseDTO houseDTO, OAuth2Authentication auth) {

        LOG.info("FFFFF:" + auth);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LOG.info("currentUser:" + principal);

        LOG.info("HouseDTO:" + houseDTO);
        return "{\"code\" : \"OK\"}";
//        return new RestResponse(new Header(Header.CODE.OK, Header.MESSAGE.OK), new Payload());
    }

//    @RequestMapping(value = "/house2", method = RequestMethod.GET, consumes="application/json")
    @RequestMapping(value = "/house2", method = RequestMethod.POST)
    public
    @ResponseBody
    String createHouse(OAuth2Authentication auth) {
        LOG.info("HHHH:" + SecurityContextHolder.getContext());
        LOG.info("GGGG:" + auth);


        LOG.info("HouseDTO:");
        return "{\"code\" : \"OK\"}";
//        return new RestResponse(new Header(Header.CODE.OK, Header.MESSAGE.OK), new Payload());
    }
}