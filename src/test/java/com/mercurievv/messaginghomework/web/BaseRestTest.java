package com.mercurievv.messaginghomework.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 2/23/2016
 * Time: 2:28 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
//use this: @RunWith(SpringJUnit4ClassRunner.class)
@SuppressWarnings({"WeakerAccess", "unused"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan
//@RunWith(SpringJUnit4ClassRunner.class)
public class BaseRestTest {
    @Value("${local.server.port}")
    protected int port;
    ObjectMapper objectMapper;
    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private WebApplicationContext context;
    private RequestSpecification spec;

    protected ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper;
    }

    protected static String getAdminAuth() {
        return getAuth("admin", "jvsfkdjds;kjk9-48982fhr892");
    }

    @SuppressWarnings("SameParameterValue")
    protected static String getAuth(final String email, final String pass) {
        try {
            return "Basic " + Base64.getEncoder().encodeToString((email + ":" + pass).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        this.objectMapper = createObjectMapper();
        MockUtil mockUtil = new MockUtil();
        this.spec = new RequestSpecBuilder()
                .build();
    }

    protected RequestSpecification given() {
        return given(null);
    }

    protected RequestSpecification given(final String description) {
        return given(true, description);
    }

    protected RequestSpecification given(@SuppressWarnings("SameParameterValue") boolean createDocs, @Nullable String description) {
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> objectMapper));


        return RestAssured.given(this.spec)
                .accept("application/json")
                .contentType("application/json")
                .when()
                .port(port)
                ;
    }


    protected ValidatableResponse get(String path, final Header... headers) {
        return getNok(path, headers)
                .statusCode(200);
    }

    protected ValidatableResponse getNok(String path) {
        return getNok(path, (Header[]) null);
    }

    protected ValidatableResponse getNok(String path, final Header... headers) {
        List<Header> headerList = Arrays.stream(headers == null ? new Header[0] : headers)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        RequestSpecification given = given();
        if (!headerList.isEmpty())
            given = given.headers(new Headers(headerList));

        return given
                .get(path)
                .then().log().body()
                .assertThat();
    }

    protected ValidatableResponse delete(String description, String auth, String path) {
        return given(description)
                .delete(path)
                .then().log().body()
                .and().assertThat().statusCode(204);
    }

    protected ValidatableResponse post_anon(final String path, String body) throws UnsupportedEncodingException {
        return given().body(body)
                .log().body()
                .post(path)
                .then().log().body()
                .assertThat().statusCode(201);
    }

    protected ValidatableResponse post(final String path, String body) {
        return postNok(path, body);
    }



    protected ValidatableResponse post(final String path, String body, String auth) {
        return postNok(path, body)
                .statusCode(Matchers.lessThan(210));
    }

    protected ValidatableResponse post(final String path, String body, Header... header) {
        return postNok(path, body, header)
                .statusCode(201);
    }

    protected ValidatableResponse postNok(String path, String body, Header... headers) {
        RequestSpecification requestSpecification = given().body(body).header(new Header("deviceCurrencyCode", "USD"));
        if (headers != null && headers.length > 0)
            requestSpecification = requestSpecification.headers(new Headers(headers));
        return requestSpecification
                .log().body()
                .post(path)
                .then().log().body()
                .assertThat();
    }


    protected <C> C getObject(String path, String jsonPath, Class<C> cClass) {
        return get(path, new Header[0])
                .extract().jsonPath()
                .getObject(jsonPath, cClass);
    }

    protected ValidatableResponse put(String path, String body) {
        RequestSpecification requestSpecification = given().body(body).header(new Header("deviceCurrencyCode", "USD"));
        return requestSpecification
                .log().body()
                .put(path)
                .then().log().body()
                .assertThat().statusCode(Matchers.lessThan(210));
    }
}
