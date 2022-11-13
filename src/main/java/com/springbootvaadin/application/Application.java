package com.springbootvaadin.application;

import com.sun.codemodel.JCodeModel;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import userMgmtSerConsumer.UserMgmtSerConsume;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.time.Clock;
import java.util.List;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "appwoyo")
//@NoTheme
@PWA(name = "appwoyo", shortName = "woyo", offlinePath="offline.html", offlineResources = { "./images/offline.png"})

@Push
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

//        doRunOnceCarefully();
       // createWebClient();
        SpringApplication.run(Application.class, args);
    }
/*
    @Bean
    public WebClient rest(ClientRegistrationRepository clients, OAuth2AuthorizedClientRepository authz) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(clients, authz);
        return WebClient.builder()
                .filter(oauth2).build();
    }
  */
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    // for generating classes from json response
    public static void doRunOnceCarefully() throws IOException {
        //        String url ="http://localhost:8095/v2/api-docs";
        String url = "http://localhost:8095/users";
//        String url = "http://localhost:8095/users/rbac/roles";
//        String url = "http://localhost:8095/users/rbac/permissions";
//        String url = "http://localhost:8095/login";
////        String url ="https://api-live.woyo.co:8020/get_driver_details_panel?city=0&vehicle_type=0&category=2&limit=50&token=011c961996b1fd305bd96c29cb6d16ef9d7774e0c261b59dca58d6fa3d6f2c5f";
//        RestTemplate restTemplate = new RestTemplate();
//        Object respJsonSwag = restTemplate.getForObject(url, Object.class);
//        ObjectMapper mapper =new ObjectMapper();
//        String jsonString = respJsonSwag.toString();
//        System.out.println(jsonString);
//        jsonString= mapper.writeValueAsString(respJsonSwag);
//        System.out.println(jsonString);
////        URL jsonUrl= new URL(jsonString);
//        File pojoClassDirectory= new File("src/main/java/");
//        convertJsonToJavaClass(jsonString,pojoClassDirectory,"jsonRBAC","PojoUser");

        UserMgmtSerConsume userMgmtSerConsume =new UserMgmtSerConsume();
        userMgmtSerConsume.callAPI(url);

    }



    public static void convertJsonToJavaClass(String inputJsonUrl, File outputJavaClassDirectory, String packageName, String javaClassName)
            throws IOException {
        JCodeModel jcodeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(jcodeModel, javaClassName, packageName, inputJsonUrl);

        jcodeModel.build(outputJavaClassDirectory);
    }

}