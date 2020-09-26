package dao;

import multichain.chain.Chain;
import multichain.chain.Method;
import multichain.chain.MultichainService;

import java.util.ArrayList;
import java.util.List;

public class VaccinationDao {
    private final static String STREAM_NAME = "vaccination_records";
    private final Chain immunoBlockChain;
    private final MultichainService multichainService;

    public VaccinationDao(String ip, int port, String username, String password, String chainName) {
        immunoBlockChain = Chain.initialize(ip, port, username, password, chainName);
        multichainService = MultichainService.setChain(immunoBlockChain);
        List<Object> params = new ArrayList<>();
        params.add(STREAM_NAME);
        multichainService.apiCall(params, Method.SUBSCRIBE, immunoBlockChain.getChainName());
    }

    public String list() {
        List<Object> params = new ArrayList<>();
        params.add(STREAM_NAME);
        return multichainService.apiCall(params, Method.LIST_STREAM_ITEMS, immunoBlockChain.getChainName());
    }

    public String getItem(String txid) {
        List<Object> params = new ArrayList<>();
        params.add(STREAM_NAME);
        params.add(txid);
        return multichainService.apiCall(params, Method.GET_STREAM_ITEM, immunoBlockChain.getChainName());
    }
}