package com.ethereum.eth.dir.discovery.service;

import java.math.BigInteger;

import com.ethereum.eth.dir.discovery.model.EthereumWallet;

public interface EthereumClientService {

	EthereumWallet getWalletWithAddress(String privateKey, String address);

	EthereumWallet getWalletWithPrivateKey(String privateKey);

	void transferEther(String fromPK, String toAddress, BigInteger amount);

}
