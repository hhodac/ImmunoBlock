package multichain;

import multichain.command.CommandElt;
import multichain.command.CommandManager;
import multichain.command.MultichainException;
import pojo.VaccinationRecord;

import java.util.ArrayList;
import java.util.List;

public class VaccinationStream {
    public List<VaccinationRecord> list() {
        CommandManager commandManager = new CommandManager("115.146.95.96", "6739", "ubuntu", "");

        Object addressResult = null;
        try {
            addressResult = commandManager.invoke(CommandElt.GETADDRESSES);
        } catch (MultichainException e) {
            e.printStackTrace();
        }
        try {
            addressResult = commandManager.invoke(CommandElt.GETADDRESSES, true);
        } catch (MultichainException e) {
            e.printStackTrace();
        }
        System.out.println(addressResult);

        return new ArrayList<>();
    }
}
