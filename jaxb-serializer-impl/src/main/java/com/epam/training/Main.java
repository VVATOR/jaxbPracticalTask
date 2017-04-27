package com.epam.training;

import org.apache.log4j.Logger;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);
    private static final String XSD_PRODUCTS = "products.xsd";
    
    public static void main(String[] args) {
        LOG.info("READ PARAMS COMAND LINE");
        Cli cli = new Cli(args);
        cli.parse();
        
        JaxbSerializerImpl impl = new JaxbSerializerImpl(XSD_PRODUCTS);
        
              
        impl.validateXmlByXsd(cli.getIn());

        impl.unmarshalling(cli.getIn());
        
        impl.marshalling(cli.getOut());   
        
        impl.validateXmlByXsd(cli.getOut());

    }
  
}
