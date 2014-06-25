/**
 * Copyright 2014 Brightcove Inc. All rights reserved.
 * @author Scott Kidder
 */
package com.brightcove.metrics.reporter;

import static org.junit.Assert.assertTrue;
import io.dropwizard.jackson.DiscoverableSubtypeResolver;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * Unit tests for the DatadogReporterFactory class.
 * 
 * @author Scott Kidder
 * 
 */
public class DatadogReporterFactoryTest {

    /**
     * Verify that the reporter factory is discovered using the
     * META-INF/services file.
     */
    @Test
    public void testRegisterJacksonSubTypes() {
        DatadogReporterFactory.register();
        final ImmutableList<Class<?>> discoveredSubtypes = new DiscoverableSubtypeResolver()
                .getDiscoveredSubtypes();
        assertTrue(discoveredSubtypes.contains(DatadogReporterFactory.class));
    }
}
