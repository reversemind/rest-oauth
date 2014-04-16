package ru.ttk.baloo.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ttk.baloo.rest.dto.HouseDTO;

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
    String createHouse(@RequestBody HouseDTO houseDTO) {
        LOG.info("HouseDTO:" + houseDTO);
        return "{\"code\" : \"OK\"}";
//        return new RestResponse(new Header(Header.CODE.OK, Header.MESSAGE.OK), new Payload());
    }

//    @RequestMapping(value = "/house2", method = RequestMethod.GET, consumes="application/json")
    @RequestMapping(value = "/house2", method = RequestMethod.GET)
    public
    @ResponseBody
    String createHouse() {
        LOG.info("HouseDTO:");
        return "{\"code\" : \"OK\"}";
//        return new RestResponse(new Header(Header.CODE.OK, Header.MESSAGE.OK), new Payload());
    }
}
