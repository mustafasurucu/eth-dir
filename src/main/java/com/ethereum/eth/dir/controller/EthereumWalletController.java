package com.ethereum.eth.dir.controller;

import com.ethereum.eth.dir.model.EthereumWallet;
import com.ethereum.eth.dir.model.request.TransferEtherRequest;
import com.ethereum.eth.dir.service.EthereumClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EthereumWalletController {

	@Autowired
	private EthereumClientService ethereumClientService;

	@RequestMapping(value = "/getWalletByAddress/{address}", method = RequestMethod.GET)
	public ResponseEntity<EthereumWallet> getWalletByAddress(@PathVariable("address") String address) {
		return ResponseEntity.ok().body(ethereumClientService.getWalletByAddress(null, address));
	}

	@RequestMapping(value = "/getWalletByPrivateKey/{privateKey}", method = RequestMethod.GET)
	public ResponseEntity<EthereumWallet> getWalletByPrivateKey(@PathVariable("privateKey") String privateKey) {
		return ResponseEntity.ok().body(ethereumClientService.getWalletByPrivateKey(privateKey));
	}

	@RequestMapping(value = "/transferEther", method = RequestMethod.PUT)
	public ResponseEntity<Void> transferEther(@Valid TransferEtherRequest request) {
		ethereumClientService.transferEther(request);
		return ResponseEntity.accepted().body(null);
	}
}
