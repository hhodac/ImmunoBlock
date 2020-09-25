import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import multichain.VaccinationStream;
import resource.VaccinationResource;

public class ImmunoBlockApplication  extends Application<ImmunoBlockConfiguration> {
    public static void main(String[] args) throws Exception {
        new ImmunoBlockApplication().run(args);
    }

    public void initialize(Bootstrap<ImmunoBlockConfiguration> bootstrap) {
    }

    public void run(ImmunoBlockConfiguration immunoBlockConfiguration, Environment environment) throws Exception {
        final VaccinationStream vaccinationStream = new VaccinationStream();
        final VaccinationResource vaccinationResource = new VaccinationResource(vaccinationStream);
        environment.jersey().register(vaccinationResource);
    }
}
