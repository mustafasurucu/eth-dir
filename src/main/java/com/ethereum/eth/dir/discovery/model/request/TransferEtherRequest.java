package com.ethereum.eth.dir.discovery.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferEtherRequest {

    private String fromAddress;
    private String toAddress;
    private String privateKey;
    private BigInteger amount;
}
