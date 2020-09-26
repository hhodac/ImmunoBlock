package config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import multichain.chain.Chain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ChainFactory {
    @NotEmpty
    private String ip;

    @Min(1)
    @Max(65535)
    private int port = 5672;

    @NotEmpty
    private String username = "multichainrpc";

    @NotEmpty
    private String password = "6vSwnHN2Ndy8qFXVyHneNiZidyFRimzEVhacnqUhY2nZ";

    @NotEmpty
    private String chainName = "ImmunoBlock";

    @JsonProperty
    public String getIp() {
        return ip;
    }

    @JsonProperty
    public void setIp(String ip) {
        this.ip = ip;
    }

    @JsonProperty
    public int getPort() {
        return port;
    }

    @JsonProperty
    public void setPort(int port) {
        this.port = port;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty
    public String getChainName() {
        return chainName;
    }

    @JsonProperty
    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public Chain build(Environment environment) {
        Chain chain = new Chain(getIp(), getPort(), getUsername(), getPassword(), getChainName());
        environment.lifecycle().manage(new Managed() {
            public void start() {
            }

            public void stop() {
            }
        });
        return chain;
    }
}
