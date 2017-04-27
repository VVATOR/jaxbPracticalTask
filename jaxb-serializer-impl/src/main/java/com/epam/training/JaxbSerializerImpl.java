package com.epam.training;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import https.www_epam_com.products.Product;
import https.www_epam_com.products.Products;

public class JaxbSerializerImpl {
    private static final String DEFAULT_XSD_NAME = "products.xsd";
    private static final Logger LOG = Logger.getLogger(JaxbSerializerImpl.class);
    private Products products = new Products();
    private URL xsdLocationPath;

    public JaxbSerializerImpl(String xsdPath) {
        final File file = new File(xsdPath);
        if (file.exists() && xsdPath != null) {
            try {
                xsdLocationPath = new URL(xsdPath);
            } catch (MalformedURLException e) {
                LOG.error("error resorce "+xsdPath);                
            }
        } else {
            xsdLocationPath = getClass().getClassLoader().getResource(DEFAULT_XSD_NAME);
            LOG.info("file shema \"" + xsdPath + "\" is not exist. ");
            LOG.info("load XSD from jar " + xsdLocationPath);
        }
        LOG.info("Use shema location: " + xsdLocationPath);

    }

    public void validateXmlByXsd(String xmlFilePath) {
        LOG.info("\n\nVALIDATE");
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = factory.newSchema(xsdLocationPath);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFilePath));
            LOG.info("XML file \"" + xmlFilePath + "\" is valid for XSD \"" + xsdLocationPath + "\"");
        } catch (SAXException | IOException e) {
            LOG.error("XML validation error \t+> " + e);
        }
    }

    public void printProduct(Product product) {
        StringBuilder builder = new StringBuilder();
        builder.append("Product [name=");
        builder.append(product.getName());
        builder.append(", price=");
        builder.append(product.getPrice());
        builder.append(", amount=");
        builder.append(product.getAmount());
        builder.append(", description=");
        builder.append(product.getDescription());
        builder.append(", type=");
        builder.append(product.getType());
        builder.append("]");
        LOG.info(builder.toString());
    }

    public void unmarshalling(String inPath) {
        LOG.info("\n\nUNMARSHALLING");
        try {
            File file = new File(inPath);
            JAXBContext jaxbContext = JAXBContext.newInstance(Products.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            products = (Products) jaxbUnmarshaller.unmarshal(file);
            for (Product product : products.getProduct()) {
                printProduct(product);
            }
        } catch (JAXBException e) {
            LOG.error("Unmarshalling error \t=> " + e);
        }
    }

    public void marshalling(String outPath) {
        LOG.info("\n\nMARSHALLING");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Products.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(products, new File(outPath));
            marshaller.marshal(products, System.out);
        } catch (JAXBException e) {
            LOG.error("Marshalling error \t=> " + e);
        }

    }
}
