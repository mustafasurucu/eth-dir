package com.ethereum.eth.dir.service;

import java.math.BigInteger;

public interface PrivateKeyService {

	String getRandomPrivateKey();

	String incrementPrivateKeyByIndex(String privateKey, int index);

	String incrementPrivateKey(String privateKey, BigInteger amount);

	String decrementPrivateKey(String privateKey, BigInteger amount);

}
