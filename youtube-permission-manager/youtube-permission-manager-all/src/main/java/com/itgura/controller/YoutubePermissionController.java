package com.itgura.controller;


import com.itgura.service.YoutubePermissionService;
import com.itgura.util.URIPathVariable;
import com.itgura.util.URIPrefix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(URIPrefix.API + URIPrefix.V1 + URIPathVariable.YOUTUBE_PERMISSION_SERVICE)
public class YoutubePermissionController {

    @Autowired
    private YoutubePermissionService youtubePermissionService;


}

