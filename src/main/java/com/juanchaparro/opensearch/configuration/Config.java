package com.juanchaparro.opensearch.configuration;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.juanchaparro.opensearch.repository")
@ComponentScan(basePackages = {"com.juanchaparro.opensearch"})
public class Config {

    Logger logger = LoggerFactory.getLogger(Config.class);

    @Value("${cloud.aws.opensearch.region}")
    private String osRegion;

    @Value("${cloud.aws.opensearch.credentials.username}")
    private String osUsername;

    @Value("${cloud.aws.opensearch.credentials.password}")
    private String osPassword;

    @Value("${cloud.aws.opensearch.domain.name}")
    private String osDomainName;

    @Value("${cloud.aws.opensearch.domain.host}")
    private String osHost;

    @Value("${cloud.aws.opensearch.domain.port}")
    private Integer osPort;

    @Value("${cloud.aws.opensearch.domain.protocol}")
    private String osProtocol;

    @Bean
    public RestHighLevelClient openSearchClient() {
        logger.info("Connecting to domain {}...", osDomainName);
        return new RestHighLevelClient(getRestClientBuilder());
    }

    private RestClientBuilder getRestClientBuilder() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(osUsername, osPassword));

        return RestClient.builder(new HttpHost(osHost, osPort, osProtocol))
                .setHttpClientConfigCallback(cb -> cb.setDefaultCredentialsProvider(credentialsProvider));
    }

}
