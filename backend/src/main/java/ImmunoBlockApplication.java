import dao.VaccinationDao;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import multichain.chain.Chain;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import resource.VaccinationResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class ImmunoBlockApplication extends Application<ImmunoBlockConfiguration> {
    public static void main(String[] args) throws Exception {
        new ImmunoBlockApplication().run(args);
    }

    public void initialize(Bootstrap<ImmunoBlockConfiguration> bootstrap) {
    }

    public void run(ImmunoBlockConfiguration immunoBlockConfiguration, Environment environment) throws Exception {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        // DO NOT pass a preflight request to down-stream auth filters
        // unauthenticated preflight requests should be permitted by spec
        cors.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, Boolean.FALSE.toString());

        final Chain chain = immunoBlockConfiguration.getChainFactory().build(environment);
        final VaccinationDao vaccinationDao = new VaccinationDao(chain);
        final VaccinationResource vaccinationResource = new VaccinationResource(vaccinationDao);

        environment.jersey().register(vaccinationResource);
    }
}
