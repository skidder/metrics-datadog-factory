metrics-datadog-factory
=======================

Factory for registering DataDog metrics reporters in a DropWizard application.

##Usage:

1. Add the metrics-datadog-factory dependency to your Maven POM:

    ```xml
    <dependency>
      <groupId>com.brightcove</groupId>
      <artifactId>metrics-datadog-factory</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency>
    ```
2. Register the factory in your DropWizard Application's `initialize` method:

    ```java
    public class MyApplication extends
            Application<MyConfiguration> {
    
        @Override
        public void initialize(Bootstrap<LicenseServerConfiguration> bootstrap) {
            registerJacksonSubTypes();
        }
    ```
3. Include the 'datadog' metric in the DropWizard configuration file using the [Metrics Configuration](https://dropwizard.github.io/dropwizard/manual/configuration.html#metrics) section:

    ```yaml
    metrics:
      reporters:
        - type: datadog
          apiKey: abc123
    ```
