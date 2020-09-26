import com.fasterxml.jackson.annotation.JsonProperty;
import config.ChainFactory;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ImmunoBlockConfiguration extends Configuration {
    @Valid
    @NotNull
    private ChainFactory chainFactory = new ChainFactory();

    @JsonProperty("chain")
    public ChainFactory getChainFactory() {
        return chainFactory;
    }

    @JsonProperty("chain")
    public void setChainFactory(ChainFactory chainFactory) {
        this.chainFactory = chainFactory;
    }
}
