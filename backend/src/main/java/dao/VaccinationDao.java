package dao;

import com.google.gson.Gson;
import multichain.chain.Chain;
import multichain.chain.Method;
import multichain.chain.MultichainService;
import pojo.VaccinationRecord;
import util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VaccinationDao {
    private final Gson gson = new Gson();
    private final static String STREAM_NAME = "vaccination_records";
    private final Chain immunoBlockChain;
    private final MultichainService multichainService;

    public VaccinationDao(Chain immunoBlockChain) {
        this.immunoBlockChain = immunoBlockChain;
        this.multichainService = MultichainService.setChain(immunoBlockChain);
        List<Object> params = new ArrayList<>();
        params.add(STREAM_NAME);
        this.multichainService.apiCall(params, Method.SUBSCRIBE, immunoBlockChain.getChainName());
    }

    public String list() {
        List<Object> params = new ArrayList<>();
        params.add(STREAM_NAME);
        return multichainService.apiCall(params, Method.LIST_STREAM_ITEMS, immunoBlockChain.getChainName());
    }

    public String retrieve(String txid) {
        List<Object> params = new ArrayList<>();
        params.add(STREAM_NAME);
        params.add(txid);
        return multichainService.apiCall(params, Method.GET_STREAM_ITEM, immunoBlockChain.getChainName());
    }

    public String addNewItem(VaccinationRecord vaccinationRecord) {
        List<Object> params = new ArrayList<>();
        params.add(STREAM_NAME);
        List<String> keys = new ArrayList<>();
        keys.add(vaccinationRecord.getPassportNumber());
        keys.add(vaccinationRecord.getClinicId());
        params.add(keys);
        String data = StringUtils.stringToHex(gson.toJson(vaccinationRecord));
        params.add(data);
        return multichainService.apiCall(params, Method.PUBLISH, immunoBlockChain.getChainName());
    }

    public String searchByPassportNumber(String passportNumber) {
        List<Object> params = new ArrayList<>();
        params.add(STREAM_NAME);
        params.add(passportNumber);
        return multichainService.apiCall(params, Method.LIST_STREAM_KEY_ITEMS, immunoBlockChain.getChainName());
    }
}
