package com.ethereum.eth.dir.service.impl;

import com.ethereum.eth.dir.model.EthereumWallet;
import com.ethereum.eth.dir.service.DiscoveryService;
import com.ethereum.eth.dir.service.EthereumClientService;
import com.ethereum.eth.dir.service.PrivateKeyService;
import com.ethereum.eth.dir.util.EthereumAddressUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscoveryServiceImpl implements DiscoveryService {

    private List<String> addressList = new ArrayList<>();
    private List<String> lowerCaseAddressList = new ArrayList<>();

    private PrivateKeyService privateKeyService;
    private EthereumClientService ethereumClientService;

    public DiscoveryServiceImpl(PrivateKeyService privateKeyService, EthereumClientService ethereumClientService) {
        this.privateKeyService = privateKeyService;
        this.ethereumClientService = ethereumClientService;
    }

    @PostConstruct
    public void init() {
        URL resource = this.getClass().getClassLoader().getResource("address/address.txt");
        try {
            addressList = EthereumAddressUtil.getAddressList(resource.getPath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void startAddressDiscovery() {
        if (CollectionUtils.isEmpty(addressList)) {
            return;
        }
        this.loadAddresses();

        while (true) {
            String randomPrivateKey = privateKeyService.getRandomPrivateKey();
            System.out.println("Random Private Key : " + randomPrivateKey);
            for (int i = 0; i < 1048576; i++) {
                String addressByPrivateKey = ethereumClientService.getAddressByPrivateKey(randomPrivateKey);
                if (lowerCaseAddressList.contains(addressByPrivateKey)) {
                    System.err.println("Address : " + addressByPrivateKey + " PK : " + randomPrivateKey);
                }
                randomPrivateKey = privateKeyService.incrementPrivateKey(randomPrivateKey, BigInteger.ONE);
            }
        }
    }

    @Override
    public void startRandomDiscovery() {
        for (int i = 0; i < 100000; i++) {
            String randomPrivateKey = privateKeyService.getRandomPrivateKey();
            EthereumWallet ethereumWallet = ethereumClientService.getWalletByPrivateKey(randomPrivateKey);
            if (ethereumWallet != null && ethereumWallet.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                System.err.println("Address : " + ethereumWallet.getAddress() + " PK : "
                        + ethereumWallet.getPrivateKey() + " Balance : " + ethereumWallet.getBalance());
            }
            if (i % 1000 == 0) {
                System.out.println("Count : " + i);
                System.out.println("Random Private Key : " + randomPrivateKey);
            }
        }
    }

    @Override
    public void startIncrementDiscovery() {
        String privateKey = privateKeyService.getRandomPrivateKey();
        System.out.println("Random Private Key : " + privateKey);
        for (int i = 0; i < 1048576; i++) {
            String orderedPrivateKey = privateKeyService.incrementPrivateKey(privateKey, BigInteger.ONE);
            privateKey = orderedPrivateKey;
            EthereumWallet ethereumWallet = ethereumClientService.getWalletByPrivateKey(orderedPrivateKey);
            if (ethereumWallet != null && ethereumWallet.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                System.err.println("Address : " + ethereumWallet.getAddress() + " PK : "
                        + ethereumWallet.getPrivateKey() + " Balance : " + ethereumWallet.getBalance());
            }
        }
    }

    private void loadAddresses() {
        addressList.forEach(s -> lowerCaseAddressList.add(s.toLowerCase()));
    }
}
