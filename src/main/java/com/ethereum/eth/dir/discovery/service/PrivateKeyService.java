package com.ethereum.eth.dir.discovery.service;

import java.math.BigInteger;

public interface PrivateKeyService {

	String getRandomPrivateKey();

	String incrementPrivateKey(String privateKey, BigInteger amount);

}
