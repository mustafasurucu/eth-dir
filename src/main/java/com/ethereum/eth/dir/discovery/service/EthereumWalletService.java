package com.ethereum.eth.dir.discovery.service;

public interface EthereumWalletService {

	String getFromAddress();

	String getToAddress();

	String getPrivateKey();

	String getRandomPK();

	String incrementPK(String privateKey);

}
