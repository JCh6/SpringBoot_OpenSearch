package com.juanchaparro.opensearch.repository;

import com.juanchaparro.opensearch.document.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PersonRepository extends ElasticsearchRepository<Person, String> {
}
