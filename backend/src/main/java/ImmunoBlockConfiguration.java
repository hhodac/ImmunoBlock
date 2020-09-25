import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class ImmunoBlockConfiguration extends Configuration {
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }
}
