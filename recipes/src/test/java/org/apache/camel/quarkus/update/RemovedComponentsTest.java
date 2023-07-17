package org.apache.camel.quarkus.update;

import org.junit.jupiter.api.Test;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;
import org.openrewrite.test.TypeValidation;

import static org.openrewrite.test.RewriteTest.toRecipe;
import static org.openrewrite.maven.Assertions.pomXml;

public class RemovedComponentsTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new RemovedComponentsRecipe())
                .parser(JavaParser.fromJavaVersion()
                        .logCompilationWarningsAndErrors(true))
                .typeValidationOptions(TypeValidation.none());
        ;
    }

    @Test
    void testRemovedExchangePatternInOptionalOut() {
        rewriteRun(
                spec -> spec.recipe(toRecipe(() -> new RemovedComponentsRecipe().getVisitor())),
                pomXml(
                        """
                                    <project>
                                        <modelVersion>4.0.0</modelVersion>
                                    
                                        <artifactId>test</artifactId>
                                        <groupId>org.apache.camel.quarkus.test</groupId>
                                        <version>1.0.0</version>
                                    
                                    
                                        <properties>
                                            <quarkus.platform.version>2.13.3.Final</quarkus.platform.version>
                                        </properties>
                                    
                                        <dependencyManagement>
                                            <dependencies>
                                                <dependency>
                                                    <groupId>io.quarkus.platform</groupId>
                                                    <artifactId>quarkus-camel-bom</artifactId>
                                                    <version>2.13.7.Final</version>
                                                    <type>pom</type>
                                                    <scope>import</scope>
                                                </dependency>
                                            </dependencies>
                                        </dependencyManagement>
                                    
                                        <dependencies>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-activemq</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-atmos</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-avro-rpc</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-caffeine-lrucache</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-datasonnet</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-bean</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-dozer</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-elasticsearch-rest</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-hbase</artifactId>
                                            </dependency>
                                <!--<dependency>  org.openrewrite.maven.MavenDownloadingException: org.iota:java-md-doclet:2.2 failed. Unable to download POM. Tried repositories:
                                       <groupId>org.apache.camel.quarkus</groupId>
                                       <artifactId>camel-quarkus-iota</artifactId>
                                   </dependency>-->
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-jbpm</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-jclouds</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-johnzon</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-microprofile-metrics</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-milo</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-opentracing</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-optaplanner</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-rabbitmq</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-smallrye-reactive-messaging</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-solr</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-tika</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-vm</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-xmlsecurity</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-xstream</artifactId>
                                            </dependency>
                                        </dependencies>
                                    
                                    </project>
                                """
                        ,
                        """
                                    <project>
                                        <modelVersion>4.0.0</modelVersion>
                                    
                                        <artifactId>test</artifactId>
                                        <groupId>org.apache.camel.quarkus.test</groupId>
                                        <version>1.0.0</version>
                                    
                                    
                                        <properties>
                                            <quarkus.platform.version>2.13.3.Final</quarkus.platform.version>
                                        </properties>
                                    
                                        <dependencyManagement>
                                            <dependencies>
                                                <dependency>
                                                    <groupId>io.quarkus.platform</groupId>
                                                    <artifactId>quarkus-camel-bom</artifactId>
                                                    <version>2.13.7.Final</version>
                                                    <type>pom</type>
                                                    <scope>import</scope>
                                                </dependency>
                                            </dependencies>
                                        </dependencyManagement>
                                    
                                        <dependencies>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-activemq was removed, consider camel-quarkus-jms or camel-quarkus-sjms or camel-quarkus-amqp instead.-->camel-quarkus-activemq</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-atmos was removed.-->camel-quarkus-atmos</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-avro-rpc was removed.-->camel-quarkus-avro-rpc</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-caffeine-lrucache was removed, consider camel-quarkus-ignite or camel-quarkus-infinispan instead.-->camel-quarkus-caffeine-lrucache</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-datasonnet was removed, but should be reintroduced.-->camel-quarkus-datasonnet</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId>camel-quarkus-bean</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-dozer was removed.-->camel-quarkus-dozer</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-elasticsearch-rest was removed.-->camel-quarkus-elasticsearch-rest</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-hbase was removed.-->camel-quarkus-hbase</artifactId_removed>
                                            </dependency>
                                <!--<dependency>  org.openrewrite.maven.MavenDownloadingException: org.iota:java-md-doclet:2.2 failed. Unable to download POM. Tried repositories:
                                       <groupId>org.apache.camel.quarkus</groupId>
                                       <artifactId>camel-quarkus-iota</artifactId>
                                   </dependency>-->
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-jbpm was removed.-->camel-quarkus-jbpm</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-jclouds was removed.-->camel-quarkus-jclouds</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-johnzon was removed, consider camel-quarkus-jackson or camel-quarkus-fastjson or camel-quarkus-gson instead.-->camel-quarkus-johnzon</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-microprofile-metrics was removed, consider camel-quarkus-opentelemetry or camel-quarkus-micrometer instead.-->camel-quarkus-microprofile-metrics</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-milo was removed.-->camel-quarkus-milo</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-opentracing was removed, consider camel-quarkus-opentelemetry or camel-quarkus-micrometer instead.-->camel-quarkus-opentracing</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-optaplanner was removed.-->camel-quarkus-optaplanner</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-rabbitmq was removed, consider camel-quarkus-spring-rabbitmq??? instead.-->camel-quarkus-rabbitmq</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-smallrye-reactive-messaging was removed, but should be reintroduced.-->camel-quarkus-smallrye-reactive-messaging</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-solr was removed.-->camel-quarkus-solr</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-tika was removed, but should be reintroduced.-->camel-quarkus-tika</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-vm was removed.-->camel-quarkus-vm</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-xmlsecurity was removed, but should be reintroduced.-->camel-quarkus-xmlsecurity</artifactId_removed>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.apache.camel.quarkus</groupId>
                                                <artifactId_removed><!--Extension camel-quarkus-xstream was removed, consider camel-quarkus-jacksonxml instead.-->camel-quarkus-xstream</artifactId_removed>
                                            </dependency>
                                        </dependencies>
                                    
                                    </project>
                                """
                )
        );
    }
    @Test
    void testRemovedComponentsReal() {
        rewriteRun(
                spec -> spec.recipe(toRecipe(() -> new RemovedComponentsRecipe().getVisitor())),
                pomXml(
                        """
<?xml version="1.0" encoding="UTF-8"?>
<!--

    Licensed to the Apache Software Foundation (ASF) under one or more
    contributor license agreements.  See the NOTICE file distributed with
    this work for additional information regarding copyright ownership.
    The ASF licenses this file to You under the Apache License, Version 2.0
    (the "License"); you may not use this file except in compliance with
    the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.apache.camel.quarkus</groupId>
        <artifactId>camel-quarkus-build-parent-mi-it</artifactId>
        <version>2.13.4-SNAPSHOT</version>
        <relativePath>../../../poms/build-parent-mi-it/pom.xml</relativePath>
    </parent>

    <artifactId>camel-quarkus-migration-test-microprofile</artifactId>
    <name>Camel Quarkus :: Migration Tests :: MicroProfile</name>
    <description>Migration tests for Camel Quarkus MicroProfile extensions</description>

    <properties>

        <quarkus.version>2.13.8.Final</quarkus.version><!-- https://repo1.maven.org/maven2/io/quarkus/quarkus-bom/ -->
        <!-- Allow running our tests against alternative BOMs, such as io.quarkus.platform:quarkus-camel-bom https://repo1.maven.org/maven2/io/quarkus/platform/quarkus-camel-bom/ -->
        <quarkus.platform.group-id>io.quarkus</quarkus.platform.group-id>
        <quarkus.platform.artifact-id>quarkus-bom</quarkus.platform.artifact-id>
        <quarkus.platform.version>${quarkus.version}</quarkus.platform.version>
        <camel-quarkus.platform.group-id>org.apache.camel.quarkus</camel-quarkus.platform.group-id>
        <camel-quarkus.platform.artifact-id>camel-quarkus-bom</camel-quarkus.platform.artifact-id>
        <camel-quarkus.platform.version>2.13.4-SNAPSHOT</camel-quarkus.platform.version>
        <camel-quarkus.version>2.13.4-SNAPSHOT</camel-quarkus.version><!-- This needs to be set to the underlying CQ version from command line when testing against Platform BOMs -->

        <quarkus.banner.enabled>false</quarkus.banner.enabled>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>${quarkus.platform.group-id}</groupId>
                <artifactId>${quarkus.platform.artifact-id}</artifactId>
                <version>${quarkus.platform.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>${camel-quarkus.platform.group-id}</groupId>
                <artifactId>${camel-quarkus.platform.artifact-id}</artifactId>
                <version>${camel-quarkus.platform.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.apache.camel.quarkus</groupId>
                <artifactId>camel-quarkus-bom-test</artifactId>
                <version>${camel-quarkus.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-bean</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-direct</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-log</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-microprofile-fault-tolerance</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-microprofile-health</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-microprofile-metrics</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel.quarkus</groupId>
            <artifactId>camel-quarkus-mock</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-resteasy</artifactId>
        </dependency>

        <!-- test dependencies -->
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-junit5</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.awaitility</groupId>
            <artifactId>awaitility</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>


    <profiles>
        <profile>
            <id>native</id>
            <activation>
                <property>
                    <name>native</name>
                </property>
            </activation>
            <properties>
                <quarkus.package.type>native</quarkus.package.type>
            </properties>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-failsafe-plugin</artifactId>
                        <executions>
                            <execution>
                                <goals>
                                    <goal>integration-test</goal>
                                    <goal>verify</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
        </profile>
        <profile>
            <id>virtualDependencies</id>
            <activation>
                <property>
                    <name>!noVirtualDependencies</name>
                </property>
            </activation>
            <dependencies>
                <!-- The following dependencies guarantee that this module is built after them. You can update them by running `mvn process-resources -Pformat -N` from the source tree root directory -->
                <dependency>
                    <groupId>org.apache.camel.quarkus</groupId>
                    <artifactId>camel-quarkus-bean-deployment</artifactId>
                    <version>${project.version}</version>
                    <type>pom</type>
                    <scope>test</scope>
                    <exclusions>
                        <exclusion>
                            <groupId>*</groupId>
                            <artifactId>*</artifactId>
                        </exclusion>
                    </exclusions>
                </dependency>
                <dependency>
                    <groupId>org.apache.camel.quarkus</groupId>
                    <artifactId>camel-quarkus-direct-deployment</artifactId>
                    <version>${project.version}</version>
                    <type>pom</type>
                    <scope>test</scope>
                    <exclusions>
                        <exclusion>
                            <groupId>*</groupId>
                            <artifactId>*</artifactId>
                        </exclusion>
                    </exclusions>
                </dependency>
                <dependency>
                    <groupId>org.apache.camel.quarkus</groupId>
                    <artifactId>camel-quarkus-log-deployment</artifactId>
                    <version>${project.version}</version>
                    <type>pom</type>
                    <scope>test</scope>
                    <exclusions>
                        <exclusion>
                            <groupId>*</groupId>
                            <artifactId>*</artifactId>
                        </exclusion>
                    </exclusions>
                </dependency>
                <dependency>
                    <groupId>org.apache.camel.quarkus</groupId>
                    <artifactId>camel-quarkus-microprofile-fault-tolerance-deployment</artifactId>
                    <version>${project.version}</version>
                    <type>pom</type>
                    <scope>test</scope>
                    <exclusions>
                        <exclusion>
                            <groupId>*</groupId>
                            <artifactId>*</artifactId>
                        </exclusion>
                    </exclusions>
                </dependency>
                <dependency>
                    <groupId>org.apache.camel.quarkus</groupId>
                    <artifactId>camel-quarkus-microprofile-health-deployment</artifactId>
                    <version>${project.version}</version>
                    <type>pom</type>
                    <scope>test</scope>
                    <exclusions>
                        <exclusion>
                            <groupId>*</groupId>
                            <artifactId>*</artifactId>
                        </exclusion>
                    </exclusions>
                </dependency>
                <dependency>
                    <groupId>org.apache.camel.quarkus</groupId>
                    <artifactId>camel-quarkus-microprofile-metrics-deployment</artifactId>
                    <version>${project.version}</version>
                    <type>pom</type>
                    <scope>test</scope>
                    <exclusions>
                        <exclusion>
                            <groupId>*</groupId>
                            <artifactId>*</artifactId>
                        </exclusion>
                    </exclusions>
                </dependency>
                <dependency>
                    <groupId>org.apache.camel.quarkus</groupId>
                    <artifactId>camel-quarkus-mock-deployment</artifactId>
                    <version>${project.version}</version>
                    <type>pom</type>
                    <scope>test</scope>
                    <exclusions>
                        <exclusion>
                            <groupId>*</groupId>
                            <artifactId>*</artifactId>
                        </exclusion>
                    </exclusions>
                </dependency>
            </dependencies>
        </profile>
    </profiles>

</project>
                                """
                )
        );
    }
}