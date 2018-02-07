package com.example.logandmeasure;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.boot.actuate.endpoint.MetricsEndpointMetricReader;
import org.springframework.boot.actuate.metrics.statsd.StatsdMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 5/12/17.
 */
@Configuration
@ConditionalOnProperty(prefix = "statsd", name = "enabled")
public class MetricConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${spring.application.name}.${metrics.env}")
    private String prefix;

    @Value("${statsd.host}")
    private String host;

    @Value("${statsd.port}")
    private int port;

    @Bean
    public StatsDClient statsd() {
        log.debug("Using statsd on {}:{}", host, port);
        return new NonBlockingStatsDClient(prefix, host, port);
    }

    @Bean
    public MetricsEndpointMetricReader metricsEndpointMetricReader(final MetricsEndpoint metricsEndpoint) {
        return new MetricsEndpointMetricReader(metricsEndpoint);
    }

    @Bean
    @ExportMetricWriter
    public MetricWriter statsdMetricWriter() {
        return new StatsdMetricWriter(prefix, host, port);
    }

}
