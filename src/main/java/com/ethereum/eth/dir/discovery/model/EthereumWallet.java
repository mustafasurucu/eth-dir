package com.ethereum.eth.dir.discovery.model;

import java.math.BigDecimal;

public class EthereumWallet {

	private String address;
	private String privateKey;
	private BigDecimal balance;

	public EthereumWallet(String address, String privateKey, BigDecimal balance) {
		super();
		this.address = address;
		this.privateKey = privateKey;
		this.balance = balance;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
