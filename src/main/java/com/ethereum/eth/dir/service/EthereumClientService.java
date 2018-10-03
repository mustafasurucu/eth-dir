package com.ethereum.eth.dir.service;

import com.ethereum.eth.dir.model.EthereumWallet;
import com.ethereum.eth.dir.model.request.TransferEtherRequest;

public interface EthereumClientService {

	EthereumWallet getWalletByAddress(String privateKey, String address);

	EthereumWallet getWalletByPrivateKey(String privateKey);

	String getAddressByPrivateKey(String privateKey);

	void transferEther(TransferEtherRequest request);

}
