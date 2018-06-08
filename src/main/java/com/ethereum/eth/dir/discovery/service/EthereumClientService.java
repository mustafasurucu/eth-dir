package com.ethereum.eth.dir.discovery.service;

import java.math.BigInteger;

import com.ethereum.eth.dir.discovery.model.EthereumWallet;

public interface EthereumClientService {

	EthereumWallet getWalletByAddress(String privateKey, String address);

	EthereumWallet getWalletByPrivateKey(String privateKey);

	void transferEther(String fromPK, String toAddress, BigInteger amount);

}
