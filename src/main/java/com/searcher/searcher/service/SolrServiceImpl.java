package com.searcher.searcher.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.Http2SolrClient;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolrServiceImpl implements SolrService {

    @Value(value = "${solr.solr-host}")
    private String solrHost;

    private static String extractTextFromPDF(File pdfFile) throws IOException {
        PDDocument document = Loader.loadPDF(new File(pdfFile.getPath()));
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();
        return text;
    }

    @Override
    public void indexFile(String path, String identifier, String name) {
        try (SolrClient solrClient = new Http2SolrClient.Builder(solrHost).build()) {
            File pdfFile = new File(path);
            String fileContent = extractTextFromPDF(pdfFile);
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", identifier);
            doc.addField("name", name);
            doc.addField("content", fileContent);
            solrClient.add(doc);
            solrClient.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> searchByString(String value) {
        try (SolrClient solrClient = new Http2SolrClient.Builder(solrHost).build()) {
            SolrQuery query = new SolrQuery();
            query.set("defType", "edismax");
            query.set("q", value);
            query.add("qf", "name^2 content");
            query.add("pf", "name^2 content");
            query.set("fl", "*,score");
            QueryResponse response = solrClient.query(query);
            SolrDocumentList results = response.getResults();
            List<String> identifiers = new ArrayList<>();
            for (org.apache.solr.common.SolrDocument result : results) {
                identifiers.add(result.getFieldValue("id").toString());
            }
            return identifiers;
        } catch (Exception e) {
            return null;
        }
    }
}
