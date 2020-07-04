package com.Controller;

import com.Service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityController {
    @Autowired
    private CommunityService communityService;


}
