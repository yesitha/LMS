package com.itgura.service.impl;

import com.itgura.request.dto.PermissionGrantDto;
import com.itgura.request.dto.PermissionRevokeDto;
import com.itgura.service.YoutubePermissionConsumer;
import com.itgura.service.YoutubePermissionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class YoutubePermissionConsumerImpl  implements YoutubePermissionConsumer {

    @Autowired
    private YoutubePermissionService youtubePermissionService;
    @Override
    @RabbitListener(queues = "${rabbitmq.queue.permissionGrant}")
    public void consumer(PermissionGrantDto permissionGrantDto) {
        log.info("Consumed {} from Queue", permissionGrantDto);
        youtubePermissionService.grantPermission(permissionGrantDto.getVideoUrl(), permissionGrantDto.getEmails());

    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.permissionRevoke}")
    public void consumer(PermissionRevokeDto permissionRevokeDto) {
        log.info("Consumed {} from Queue", permissionRevokeDto);
       youtubePermissionService.revokePermission(permissionRevokeDto.getVideoUrl(), permissionRevokeDto.getEmails());
    }
}
