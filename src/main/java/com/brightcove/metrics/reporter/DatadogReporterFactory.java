/**
 * Copyright 2014 Brightcove Inc. All rights reserved.
 * @author Scott Kidder
 */
package com.brightcove.metrics.reporter;

import static com.codahale.metrics.datadog.DatadogReporter.Expansions.COUNT;
import static com.codahale.metrics.datadog.DatadogReporter.Expansions.MEDIAN;
import static com.codahale.metrics.datadog.DatadogReporter.Expansions.P95;
import static com.codahale.metrics.datadog.DatadogReporter.Expansions.P99;
import static com.codahale.metrics.datadog.DatadogReporter.Expansions.RATE_15_MINUTE;
import static com.codahale.metrics.datadog.DatadogReporter.Expansions.RATE_1_MINUTE;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.metrics.BaseReporterFactory;

import java.io.IOException;
import java.util.EnumSet;

import javax.validation.constraints.NotNull;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.datadog.DatadogReporter;
import com.codahale.metrics.datadog.DatadogReporter.Expansions;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Factory that simplifies the configuration of a DataDog reporter when using
 * DropWizard.
 * 
 * @author Scott Kidder
 * 
 */
@JsonTypeName("datadog")
public class DatadogReporterFactory extends BaseReporterFactory {
    @NotNull
    private String apiKey;

    @JsonProperty
    public String getApiKey() {
        return apiKey;
    }

    @JsonProperty
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Register the DatadogReporterFactory with Jackson so that it can be
     * discovered when parsing the DropWizard configuration file.
     */
    public static void register() {
        final ObjectMapper objectMapper = Jackson.newObjectMapper();
        objectMapper.getSubtypeResolver().registerSubtypes(
                DatadogReporterFactory.class);
    }

    @Override
    public ScheduledReporter build(MetricRegistry registry) {

        final EnumSet<Expansions> expansions = EnumSet.of(COUNT, RATE_1_MINUTE,
                RATE_15_MINUTE, MEDIAN, P95, P99);
        final DatadogReporter reporter;
        try {
            reporter = new DatadogReporter.Builder(registry).withEC2Host()
                    .withApiKey(apiKey).withExpansions(expansions).build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(
                    "Unable to construct DataDog metrics reporter", e);
        }
        return reporter;
    }
}
