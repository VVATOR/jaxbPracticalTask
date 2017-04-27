package com.epam.training;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

public class Cli {
	private static final Logger LOG = Logger.getLogger(Cli.class.getName());
	private String[] args = null;
	private Options options = new Options();

	private String in;
	private String out;

	public String getIn() {
		return in;
	}

	public String getOut() {
		return out;
	}

	public Cli(String[] args) {
		this.args = args;
		options.addOption("i", "in", true, "in parameter. Path to input XML file, URL");
		options.addOption("o", "out", true, "out parameter. Path to output XML file, URL");
	}

	public void parse() {
		CommandLineParser parser = new BasicParser();

		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("i")) {
				this.in = cmd.getOptionValue("i");
				LOG.info("in: " + this.in);
			} else {
				LOG.info("Missing \"in\" option");
				help();
			}

			if (cmd.hasOption("o")) {
				this.out = cmd.getOptionValue("o");
				LOG.info("out: " + this.out);

			} else {
				LOG.info("Missing \"out\" option");
				help();
			}

		} catch (ParseException e) {
			LOG.error("Failed to parse comand line properties", e);
			help();
		}
	}

	private void help() {
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Main", options);
		System.exit(0);
	}
}