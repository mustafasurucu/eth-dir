package com.ethereum.eth.dir.discovery.service;

public interface PrivateKeyService {

	String getRandomPrivateKey();

	String incrementPrivateKey(String privateKey);

}
