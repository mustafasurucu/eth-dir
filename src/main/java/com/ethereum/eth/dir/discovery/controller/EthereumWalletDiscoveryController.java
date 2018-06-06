package com.ethereum.eth.dir.discovery.controller;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ethereum.eth.dir.discovery.model.EthereumWallet;
import com.ethereum.eth.dir.discovery.service.EthereumClientService;
import com.ethereum.eth.dir.discovery.service.EthereumWalletService;

@RestController
public class EthereumWalletDiscoveryController {

	private EthereumWalletService ethereumWalletService;
	private EthereumClientService ethereumClientService;

	public EthereumWalletDiscoveryController(EthereumWalletService ethereumWalletService,
			EthereumClientService ethereumClientService) {
		this.ethereumWalletService = ethereumWalletService;
		this.ethereumClientService = ethereumClientService;
	}

	@RequestMapping("/getWalletWithAddress")
	public EthereumWallet getWalletWithAddress() {
		String address = ethereumWalletService.getFromAddress();
		EthereumWallet ethereumWallet = ethereumClientService.getWalletWithAddress(null, address);
		if (ethereumWallet != null && ethereumWallet.getBalance().compareTo(BigDecimal.ZERO) > 0) {
			System.out.println("Balance greater than 0");
		}
		return ethereumWallet;
	}

	@RequestMapping("/getWalletWithPK")
	public EthereumWallet getWalletWithPK() {
		String privateKey = ethereumWalletService.getPrivateKey();
		return ethereumClientService.getWalletWithPrivateKey(privateKey);
	}

	@RequestMapping("/transferEther")
	public void transferEther() {
		String privateKey = ethereumWalletService.getPrivateKey();
		String toAddress = ethereumWalletService.getToAddress();
		ethereumClientService.transferEther(privateKey, toAddress, new BigInteger("10"));
	}

	@RequestMapping("/startDiscovery")
	public void startDiscovery() {
		for (int i = 0; i < 100000; i++) {
			String randomPK = ethereumWalletService.getRandomPK();
			EthereumWallet ethereumWallet = ethereumClientService.getWalletWithPrivateKey(randomPK);
			if (ethereumWallet != null && ethereumWallet.getBalance().compareTo(BigDecimal.ZERO) > 0) {
				System.out.println("Address : " + ethereumWallet.getAddress() + " PK : "
						+ ethereumWallet.getPrivateKey() + " Balance : " + ethereumWallet.getBalance());
			}
			if (i % 1000 == 0) {
				System.out.println("Count : " + i);
			}
		}
	}

	@RequestMapping("/startDiscoveryIncrement")
	public void startDiscoveryIncrement() {
		String privateKey = ethereumWalletService.getRandomPK();
		for (int i = 0; i < 100001; i++) {
			String orderedPK = ethereumWalletService.incrementPK(privateKey);
			privateKey = orderedPK;
			EthereumWallet ethereumWallet = ethereumClientService.getWalletWithPrivateKey(orderedPK);
			if (ethereumWallet != null && ethereumWallet.getBalance().compareTo(BigDecimal.ZERO) > 0) {
				System.out.println("Address : " + ethereumWallet.getAddress() + " PK : "
						+ ethereumWallet.getPrivateKey() + " Balance : " + ethereumWallet.getBalance());
			}
			if (i > 0 && i % 1000 == 0) {
				System.out.println("Count : " + i);
				privateKey = ethereumWalletService.getRandomPK();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
