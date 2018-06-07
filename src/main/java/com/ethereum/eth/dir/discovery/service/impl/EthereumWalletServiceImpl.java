package com.ethereum.eth.dir.discovery.service.impl;

import com.ethereum.eth.dir.discovery.service.EthereumWalletService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Random;

@Service
public class EthereumWalletServiceImpl implements EthereumWalletService {

    @Override
    public String getFromAddress() {
        String address = "0x3f17f1962b36e491b30a40b2405849e597ba5fb5";
        return address;
    }

    @Override
    public String getToAddress() {
        String address = "0xB9081850BbeA342edd36E6ed43fb7b308E4B25d5";
        return address;
    }

    @Override
    public String getPrivateKey() {
        String privateKey = "0000000000000000000000000000000000000000000000000000000000000000";
        return privateKey;
    }

    @Override
    public String getRandomPK() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        sb.append(Integer.toHexString(8));
        while (sb.length() < 63) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, 64);
    }

    @Override
    public String incrementPK(String privateKey) {
        BigInteger decimal = new BigInteger(privateKey, 16);
        decimal = decimal.add(BigInteger.ONE);
        return decimal.toString(16);
    }

}
