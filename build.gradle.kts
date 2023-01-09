plugins {
    application
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.splunk.example.RabbleMqMain")
    applicationDefaultJvmArgs = listOf(
        "-javaagent:splunk-otel-javaagent-1.19.0.jar",
        "-Dotel.javaagent.debug=true",
        "-Dotel.resource.attributes=deployment.environment=measure-ext",
        "-Dsplunk.metrics.enabled=true",
        "-Dsplunk.metrics.implementation=opentelemetry",
        "-Dotel.metric.export.interval=5000",
        "-Dotel.service.name=MeasureExternalsExample"
    )
}

dependencies {
    implementation("com.rabbitmq:amqp-client:5.16.0")
//    implementation("io.opentelemetry:opentelemetry-sdk:1.21.0")

    implementation("org.testcontainers:testcontainers:1.17.6")
    implementation("org.testcontainers:rabbitmq:1.17.6");
}
