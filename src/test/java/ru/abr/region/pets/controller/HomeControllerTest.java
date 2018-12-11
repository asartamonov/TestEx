package ru.abr.region.pets.controller;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.abr.region.pets.PetsApplication;
import ru.abr.region.pets.config.TestConfig;
import ru.abr.region.pets.repository.PetRepository;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {PetsApplication.class, TestConfig.class})
@Transactional(isolation = Isolation.READ_UNCOMMITTED) // little bit meaningless in case of REST but whatever
@Rollback // little bit meaningless in case of REST but whatever
@ActiveProfiles("test")
public class HomeControllerTest {

    private static final String EXISTING_UUID = "6F9619FF-8B86-D011-B42D-00CF4FC964FF";
    private static final String NON_EXISTING_UUID = "00000-8B86-D011-B42D-00CF4FC964FF";
    private static final String NOT_UUID = "dfdfadwe";
    @Autowired
    EntityManager em;
    @LocalServerPort
    private int port;
    @Autowired
    private PetRepository petRepository;

    @Test
    public void shouldSavePetsAndReturn200() throws IOException {
        String pets = "[{ \"id\": \"6F9619FF-8B86-D011-B42D-00CF4FC964FF\", \"name\": \"кошка 1\", \"breed\": \"порода 1\", \"food\": \"корм 1\" }, " +
                "{ \"id\": \"6F9619FF-8B86-D012-B42D-00CF4FC964FF\", \"name\": \"кошка 4\", \"breed\": \"порода 32\", \"food\": \"корм 2\" } ]";
        StringEntity entity = new StringEntity(pets,
                ContentType.APPLICATION_JSON);
        HttpUriRequest request = new HttpPost("http://localhost:" + port + "/pets/");
        ((HttpPost) request).setEntity(entity);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void shouldNotFindPetAndReturn404() throws IOException {
        String petId = UUID.randomUUID().toString();
        HttpUriRequest request = new HttpGet("http://localhost:" + port + "/pet/" + petId);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void shouldFindPetAndReturn200() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:" + port + "/pet/" + EXISTING_UUID);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void shouldFindPetAndReturn404() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:" + port + "/pet/" + NON_EXISTING_UUID);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void shouldFindPetAndReturn500() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:" + port + "/pet/" + NOT_UUID);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_BAD_REQUEST));
    }
}