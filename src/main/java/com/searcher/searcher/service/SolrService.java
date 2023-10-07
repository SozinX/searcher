package com.searcher.searcher.service;

import java.util.List;

public interface SolrService {

    void indexFile(String path, String identifier, String name);

    List<String> searchByString(String value);
}
