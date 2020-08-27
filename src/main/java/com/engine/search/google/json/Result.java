package com.engine.search.google.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.jsoup.nodes.Document;

@ToString
    public class Result {
        public String title;
        public String link;
        public String image;
        @JsonIgnore
        public Document document;
    }
