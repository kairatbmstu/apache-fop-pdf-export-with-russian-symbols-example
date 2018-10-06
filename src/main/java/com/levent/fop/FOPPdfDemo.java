package com.levent.fop;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

public class FOPPdfDemo {

    public static final String RESOURCES_DIR;
    public static final String OUTPUT_DIR;

    static {
        RESOURCES_DIR = "src//main//resources//";
        OUTPUT_DIR = "src//main//resources//output//";
    }

    public static void main(String[] args) throws SAXException, IOException {
        FOPPdfDemo fOPPdfDemo = new FOPPdfDemo();
        try {
            fOPPdfDemo.convertToPDF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void convertToPDF() throws IOException, FOPException, TransformerException, SAXException {
        File xsltFile = new File(RESOURCES_DIR + "//template.xsl");
        StreamSource xmlSource = new StreamSource(new File(RESOURCES_DIR + "//Employees.xml"));
        URL url = Object.class.getResource("/fop-config.xml");
        System.out.println("url: "+url);
        File  file = new File(url.getPath());
        FopFactory fopFactory = FopFactory.newInstance(file);
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        OutputStream out = new java.io.FileOutputStream(OUTPUT_DIR + "//employee.pdf");

        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(xmlSource, res);
        } finally {
            out.close();
        }
    }

}
