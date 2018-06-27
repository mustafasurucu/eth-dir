package com.ethereum.eth.dir.discovery.service.impl;

import java.math.BigInteger;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.ethereum.eth.dir.discovery.service.PrivateKeyService;

@Service
public class PrivateKeyServiceImpl implements PrivateKeyService {

	@Override
	public String getRandomPrivateKey() {
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		while (sb.length() < 64) {
			sb.append(Integer.toHexString(r.nextInt()));
		}
		return sb.toString().substring(0, 64);
	}

	@Override
	public String incrementPrivateKey(String privateKey, BigInteger amount) {
		BigInteger decimal = new BigInteger(privateKey, 16);
		decimal = decimal.add(amount);
		return decimal.toString(16);
	}

}
