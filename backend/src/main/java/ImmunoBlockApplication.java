import dao.VaccinationDao;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import resource.VaccinationResource;

public class ImmunoBlockApplication extends Application<ImmunoBlockConfiguration> {
    public static void main(String[] args) throws Exception {
        new ImmunoBlockApplication().run(args);
    }

    public void initialize(Bootstrap<ImmunoBlockConfiguration> bootstrap) {
    }

    public void run(ImmunoBlockConfiguration immunoBlockConfiguration, Environment environment) throws Exception {
        final String ip = "45.113.233.149";
        final int port = 6738;
        final String username = "multichainrpc";
        final String password = "6vSwnHN2Ndy8qFXVyHneNiZidyFRimzEVhacnqUhY2nZ";
        final String chainName = "ImmunoBlock";
        final VaccinationDao vaccinationDao = new VaccinationDao(ip, port, username, password, chainName);
        final VaccinationResource vaccinationResource = new VaccinationResource(vaccinationDao);
        environment.jersey().register(vaccinationResource);
    }
}
