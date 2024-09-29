package com.itgura.service;

import com.itgura.request.dto.PermissionGrantDto;
import com.itgura.request.dto.PermissionRevokeDto;

public interface YoutubePermissionConsumer {
    void consumer(PermissionGrantDto permissionGrantDto);
    void consumer(PermissionRevokeDto permissionRevokeDto); ;
}
