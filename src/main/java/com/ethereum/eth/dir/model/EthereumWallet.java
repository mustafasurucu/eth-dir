package com.ethereum.eth.dir.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EthereumWallet {

	private String address;
	private String privateKey;
	private BigDecimal balance;
}
