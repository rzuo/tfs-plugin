package hudson.plugins.tfs.commands;

import java.util.Calendar;

import hudson.Util;
import hudson.plugins.tfs.util.DateUtil;
import hudson.plugins.tfs.util.MaskedArgumentListBuilder;
import hudson.util.ArgumentListBuilder;

public abstract class AbstractCommand implements Command {

    private final ServerConfigurationProvider config;
    
    public AbstractCommand(ServerConfigurationProvider configurationProvider) {
        this.config = configurationProvider;
    }

	//
	// modified by rzuo @ 2015.12.27
	//
	// With -server argument in tf command, only DefaultCollection is valid
	// if there are many collections other DefaultCollection, the -server argument can not access them.
	// So by my working experience, I use -collection argument other than -server argument to solve this problem.
	//
    protected void addServerArgument(ArgumentListBuilder arguments) {
        arguments.add(String.format("-collection:%s", config.getUrl()));
    }
    
    protected void addLoginArgument(MaskedArgumentListBuilder arguments) {
        if ((Util.fixEmpty(config.getUserName()) != null) && (config.getUserPassword()!= null)) {
            arguments.addMasked(String.format("-login:%s,%s", 
                    config.getUserName(), 
                    config.getUserPassword()));
        }
    }

    public ServerConfigurationProvider getConfig() {
        return config;
    }
}
