package com.ethereum.eth.dir.discovery.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ethereum.eth.dir.discovery.model.EthereumWallet;
import com.ethereum.eth.dir.discovery.service.EthereumClientService;
import com.ethereum.eth.dir.discovery.service.PrivateKeyService;
import com.ethereum.eth.dir.util.EthereumAddressUtil;

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

	@RequestMapping("/startAddressDiscovery")
	public void startAddressDiscovery() {
		URL resource = this.getClass().getClassLoader().getResource("address/address.txt");
		List<String> addressList = new ArrayList<>();
		List<String> lowerCaseAddressList = new ArrayList<>();
		try {
			addressList = EthereumAddressUtil.getAddressList(resource.getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (CollectionUtils.isEmpty(addressList)) {
			return;
		}

		addressList.forEach(s -> lowerCaseAddressList.add(s.toLowerCase()));

		while (true) {
			String randomPrivateKey = privateKeyService.getRandomPrivateKey();
			System.out.println("Random Private Key : " + randomPrivateKey);
			for (int i = 0; i < 100000; i++) {
				String addressByPrivateKey = ethereumClientService.getAddressByPrivateKey(randomPrivateKey);
				if (lowerCaseAddressList.contains(addressByPrivateKey)) {
					System.err.println("Address : " + addressByPrivateKey + " PK : " + randomPrivateKey);
				}
				randomPrivateKey = privateKeyService.incrementPrivateKey(randomPrivateKey);
			}
		}
	}

}
