package com.ethereum.eth.dir.discovery.controller;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ethereum.eth.dir.discovery.model.EthereumWallet;
import com.ethereum.eth.dir.discovery.service.EthereumClientService;
import com.ethereum.eth.dir.discovery.service.PrivateKeyService;

@RestController
public class EthereumWalletDiscoveryController {

	private PrivateKeyService privateKeyService;
	private EthereumClientService ethereumClientService;

	public EthereumWalletDiscoveryController(PrivateKeyService privateKeyService,
			EthereumClientService ethereumClientService) {
		super();
		this.privateKeyService = privateKeyService;
		this.ethereumClientService = ethereumClientService;
	}

	@RequestMapping("/getWalletByAddress/{address}")
	public EthereumWallet getWalletByAddress(@PathVariable("address") String address) {
		return ethereumClientService.getWalletByAddress(null, address);
	}

	@RequestMapping("/getWalletByPrivateKey/{privateKey}")
	public EthereumWallet getWalletByPrivateKey(@PathVariable("privateKey") String privateKey) {
		return ethereumClientService.getWalletByPrivateKey(privateKey);
	}

	@RequestMapping("/transferEther")
	public void transferEther() {
		String privateKey = "";
		String toAddress = "";
		ethereumClientService.transferEther(privateKey, toAddress, new BigInteger("10"));
	}

	@RequestMapping("/startRandomDiscovery")
	public void startRandomDiscovery() {
		for (int i = 0; i < 100000; i++) {
			String randomPrivateKey = privateKeyService.getRandomPrivateKey();
			EthereumWallet ethereumWallet = ethereumClientService.getWalletByPrivateKey(randomPrivateKey);
			if (ethereumWallet != null && ethereumWallet.getBalance().compareTo(BigDecimal.ZERO) > 0) {
				System.err.println("Address : " + ethereumWallet.getAddress() + " PK : "
						+ ethereumWallet.getPrivateKey() + " Balance : " + ethereumWallet.getBalance());
			}
			if (i % 1000 == 0) {
				System.out.println("Count : " + i);
				System.out.println("Random Private Key : " + randomPrivateKey);
			}
		}
	}

	@RequestMapping("/startIncrementDiscovery")
	public void startIncrementDiscovery() {
		String privateKey = privateKeyService.getRandomPrivateKey();
		System.out.println("Random Private Key : " + privateKey);
		for (int i = 0; i < 1000001; i++) {
			String orderedPrivateKey = privateKeyService.incrementPrivateKey(privateKey);
			privateKey = orderedPrivateKey;
			EthereumWallet ethereumWallet = ethereumClientService.getWalletByPrivateKey(orderedPrivateKey);
			if (ethereumWallet != null && ethereumWallet.getBalance().compareTo(BigDecimal.ZERO) > 0) {
				System.err.println("Address : " + ethereumWallet.getAddress() + " PK : "
						+ ethereumWallet.getPrivateKey() + " Balance : " + ethereumWallet.getBalance());
			}
			if (i > 0 && i % 100 == 0) {
				// System.out.println("Count : " + i);
				privateKey = privateKeyService.getRandomPrivateKey();
				// System.out.println("New generated Random Private Key : " + privateKey);
			}
		}
	}

	@RequestMapping("/startAddressDiscovery/{address}")
	public void startAddressDiscovery(@PathVariable("address") String address) {
		String randomPrivateKey = privateKeyService.getRandomPrivateKey();
		for (int i = 0; i < 100000; i++) {
			String addressByPrivateKey = ethereumClientService.getAddressByPrivateKey(randomPrivateKey);
			if (address.equals(addressByPrivateKey)) {
				System.err.println("Address : " + address + " PK : " + randomPrivateKey);
			}
			if (i % 1000 == 0) {
				System.out.println("Count : " + i);
			}
			randomPrivateKey = privateKeyService.incrementPrivateKey(randomPrivateKey);
		}
		System.out.println("Address discovery completed");
	}

}
