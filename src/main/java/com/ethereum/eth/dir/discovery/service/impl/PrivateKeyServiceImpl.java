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

		// sb.setCharAt(0, '7');

		return sb.toString().substring(0, 64);
	}

	@Override
	public String incrementPrivateKey(String privateKey) {
		BigInteger decimal = new BigInteger(privateKey, 16);
		decimal = decimal.add(BigInteger.ONE);
		return decimal.toString(16);
	}

}
