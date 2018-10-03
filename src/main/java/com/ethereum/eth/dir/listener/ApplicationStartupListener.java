package com.ethereum.eth.dir.listener;

import com.ethereum.eth.dir.service.DiscoveryService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private DiscoveryService discoveryService;

    public ApplicationStartupListener(DiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        discoveryService.startAddressDiscovery();
    }
}
