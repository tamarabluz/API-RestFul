
package io.github.tamarabluz.apirestful;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.tamarabluz.apirestful.controllers.PeopleController;
import io.github.tamarabluz.apirestful.entities.dtos.request.AddressRequest;
import io.github.tamarabluz.apirestful.entities.dtos.request.PeopleRequest;
import io.github.tamarabluz.apirestful.services.PeopleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class PeopleControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private PeopleService service;

    @InjectMocks
    private PeopleController controller;

    private PeopleRequest peopleRequest;

    private AddressRequest addressRequest;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        peopleRequest = createNewPeopleRequest();

        addressRequest = createNewAddressRequest();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_ShouldReturnResponseEntityWithStatusCreated() {
        peopleRequest = createNewPeopleRequest();
        peopleRequest.setId(1L);

        when(service.create(peopleRequest)).thenReturn(peopleRequest);

        ResponseEntity<PeopleRequest> response = controller.create(peopleRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation().toString()).isEqualTo("/people?id=1");

        verify(service).create(peopleRequest);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findAll_ShouldReturnResponseEntityWithAllPeople() {
        List<PeopleRequest> people = Arrays.asList(peopleRequest);

        when(service.findAll()).thenReturn(people);

        ResponseEntity<List<PeopleRequest>> response = controller.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(people);

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }
    @Test
    void shouldReturnOkWhenFindPersonById() throws Exception {
        PeopleRequest expectedPeople = createNewPeopleRequest();
        given(service.findById(1L)).willReturn(expectedPeople);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MvcResult result = mockMvc.perform(get("/people/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        PeopleRequest people = objectMapper.readValue(content, PeopleRequest.class);
        assertThat(people).isEqualToComparingFieldByField(expectedPeople);
    }
    @Test
    public void shouldReturnNoContentWhenUpdatePerson() throws Exception {
         peopleRequest = createNewPeopleRequest();

        mockMvc.perform(put("/people/1").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(peopleRequest)))
                .andExpect(status().isNoContent());

        verify(service, times(1)).update(1L, peopleRequest);
    }

    @Test
    public void shouldReturnNoContentWhenDeletePerson() throws Exception {
        mockMvc.perform(delete("/people/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }
    @Test
    public void shouldReturnNoContentWhenAddAddressToPerson() throws Exception {
         addressRequest = createNewAddressRequest();

        mockMvc.perform(patch("/people/1/add-address").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(addressRequest)))
                .andExpect(status().isNoContent());

        verify(service, times(1)).addAddressToPeople(1L, addressRequest);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    private PeopleRequest createNewPeopleRequest() {
        return PeopleRequest.builder()
                .id(1L)
                .name("Tamara B Luz")
                .birthDate(LocalDate.of(1989, 12, 1))
                .addresses(null)
                .build();
    }


    private AddressRequest createNewAddressRequest() {
        return AddressRequest.builder()
                .id(1L)
                .cep("9832839823")
                .logradouro("cloves")
                .numero(12L)
                .cidade("cachoeirinha")
                .isPrincipal(false)
                .peopleId(1L)
                .build();
    }
}