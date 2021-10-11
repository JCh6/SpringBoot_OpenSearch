package com.juanchaparro.opensearch.configuration;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(osUsername, osPassword));

        RestClientBuilder builder = RestClient.builder(new HttpHost(osHost, osPort, osProtocol))
                        .setHttpClientConfigCallback((httpClientBuilder) ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                );

        return new RestHighLevelClient(builder);
    }

}
