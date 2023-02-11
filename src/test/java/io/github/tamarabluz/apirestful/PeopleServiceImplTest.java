package io.github.tamarabluz.apirestful;

import io.github.tamarabluz.apirestful.entities.Address;
import io.github.tamarabluz.apirestful.entities.People;
import io.github.tamarabluz.apirestful.entities.dtos.request.AddressRequest;
import io.github.tamarabluz.apirestful.entities.dtos.request.PeopleRequest;
import io.github.tamarabluz.apirestful.repositories.AddressRepository;
import io.github.tamarabluz.apirestful.repositories.PeopleRepository;
import io.github.tamarabluz.apirestful.services.exceptions.ObjectNotFoundException;
import io.github.tamarabluz.apirestful.services.impl.PeopleServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.modelmapper.Conditions.not;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class PeopleServiceImplTest {

    @InjectMocks
    private PeopleServiceImpl peopleService;

    @Mock
    private PeopleRepository peopleRepository;
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ModelMapper mapper;
    private PeopleRequest peopleRequest;
    private AddressRequest addressRequest;
    private People people;
    private Address address;
    private List<PeopleRequest> peopleRequests;
    private List<AddressRequest> addressRequests;


    @Before
    public void setUpPeople() {
        people = createNewPeople();
        peopleRequest = createNewPeopleRequest();
        peopleRequests = new ArrayList<>();
        peopleRequests.add(peopleRequest);
    }
    @Before
    public void setUpAddress(){
        address = createNewAddress();
        addressRequest = createNewAddressRequest();
        addressRequests = new ArrayList<>();
        addressRequests.add(addressRequest);
    }

    @Test
    void testCreatePeople() {
        peopleRequest = createNewPeopleRequest();
        people = createNewPeople();
        when(mapper.map(peopleRequest, People.class)).thenReturn(people);
        when(peopleRepository.save(people)).thenReturn(people);
        when(mapper.map(people, PeopleRequest.class)).thenReturn(peopleRequest);


        PeopleRequest result = peopleService.create(peopleRequest);

        assertEquals(peopleRequest, result);
        assertNotNull(result);

        verify(mapper).map(peopleRequest, People.class);
        verify(peopleRepository).save(people);
        verify(mapper).map(people, PeopleRequest.class);
    }

    @Test
    void testFindAll() {
        People people1 = people;
        People people2 = people;
        List<People> peopleList = Arrays.asList(people1, people2);

        PeopleRequest peopleRequest1 = peopleRequest;
        PeopleRequest peopleRequest2 = peopleRequest;
        List<PeopleRequest> expectedPeopleRequestList = Arrays.asList(peopleRequest1, peopleRequest2);

        when(peopleRepository.findAll()).thenReturn(peopleList);
        when(mapper.map(people1, PeopleRequest.class)).thenReturn(peopleRequest1);
        when(mapper.map(people2, PeopleRequest.class)).thenReturn(peopleRequest2);

        List<PeopleRequest> result = peopleService.findAll();
        assertEquals(expectedPeopleRequestList, result);
        assertNotNull(result);
    }

    @Test
    void testFindById_whenIdExists_shouldReturnPeopleRequest() {

        People people = createNewPeople();
        people.setId(1L);
        when(peopleRepository.findById(1L)).thenReturn(Optional.of(people));

        PeopleRequest peopleRequest = createNewPeopleRequest();
        when(mapper.map(people, PeopleRequest.class)).thenReturn(peopleRequest);

        PeopleRequest result = peopleService.findById(1L);

        verify(peopleRepository, times(1)).findById(1L);
        verify(mapper, times(1)).map(people, PeopleRequest.class);
        assertEquals(peopleRequest, result);
    }

    @Test
    void testFindById_whenIdNotExists_shouldThrowObjectNotFoundException() {

        when(peopleRepository.findById(1L)).thenReturn(empty());

        assertThrows(ObjectNotFoundException.class, () -> {
            peopleService.findById(1L);
        });

        verify(peopleRepository, times(1)).findById(1L);
        verify(mapper, times(0)).map(any(), any());
    }
    @Test
    void testUpdate_whenIdExists_shouldReturnPeopleRequest() {
        people = createNewPeople();
        peopleRequest = createNewPeopleRequest();

        when(peopleRepository.findById(1L)).thenReturn(Optional.of(people));
        when(mapper.map(peopleRequest, People.class)).thenReturn(people);
        when(mapper.map(people, PeopleRequest.class)).thenReturn(peopleRequest);

        PeopleRequest result = peopleService.update(1L, peopleRequest);
        assertEquals(peopleRequest, result);
        assertNotNull(result);

        verify(peopleRepository, times(1)).findById(1L);
        verify(peopleRepository, times(1)).save(people);
        verify(mapper, times(1)).map(peopleRequest, People.class);
        verify(mapper, times(1)).map(people, PeopleRequest.class);

    }

    @Test
    void testUpdate_whenIdDoesNotExist_shouldThrowObjectNotFoundException() {
        when(peopleRepository.findById(1L)).thenReturn(empty());

        ObjectNotFoundException exception = assertThrows(
                ObjectNotFoundException.class, () -> peopleService.update(1L, peopleRequest));

        assertEquals("Pessoa com id 1 não encontrada.", exception.getMessage());
        verify(peopleRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(peopleRepository);
        verifyZeroInteractions(mapper);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        people = createNewPeople();
        peopleRequest = createNewPeopleRequest();
        people.setId(id);
        when(peopleRepository.findById(id)).thenReturn(Optional.of(people));

        peopleService.delete(id);

        verify(peopleRepository).delete(people);
    }

    @Test
    void testDelete_NotFound() {
        Long id = 1L;
        people = createNewPeople();
        peopleRequest = createNewPeopleRequest();

        when(peopleRepository.findById(id)).thenReturn(empty());

        assertThrows(ObjectNotFoundException.class, () -> peopleService.delete(id));
    }

    @Test
    void addAddressToPeople_WhenAddressIsAddedSuccessfully_ShouldReturnPeopleRequest() {
        doReturn(people).when(peopleRepository).findById(people.getId());
        doReturn(address).when(addressRepository).save(address);
        doReturn(people).when(peopleRepository).save(people);
        doReturn(new PeopleRequest()).when(mapper).map(people, PeopleRequest.class);

        PeopleRequest result = peopleService.addAddressToPeople(people.getId(), addressRequest);

        verify(peopleRepository, times(1)).findById(people.getId());
        verify(addressRepository, times(1)).save(address);
        verify(peopleRepository, times(1)).save(people);
        verify(mapper, times(1)).map(people, PeopleRequest.class);

        assertNotNull(result);
    }

    @Test
    void addAddressToPeopleNotFoundTest() {
        Long id = 1L;
        addressRequest =createNewAddressRequest();
        when(peopleRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        peopleService.addAddressToPeople(1L, addressRequest);
        assertThrows(ObjectNotFoundException.class, () -> peopleService.findById(id));
    }

@Test
void addAddressToPeopleMaxAddressTest() {
    List<Address> addresses = new ArrayList<>();
    addresses.add(address);
    people.setAddresses(addresses);
    people.setAddresses(addresses);
    when(peopleRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(people));
    peopleService.addAddressToPeople(1L, addressRequest);
}

//    @Test
//    public void findAllAddressesToPeopleTest() {
//        address = createNewAddress();
//        addressRequest =createNewAddressRequest();
//        when(peopleRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(people));
//        when(addressRepository.findAddressByPeopleId(
//                any(Long.class))).thenReturn((List<Address>) address);
//        List<AddressRequest> result = peopleService.findAllAddressesToPeople(people.getId());
//        assertNotNull(result);
//        assertThat(result, is(not(empty())));
//        assertEquals(result, addressRequest);
//        verify(peopleRepository, times(1)).findById(any(Long.class));
//        verify(addressRepository, times(1)).findAddressByPeopleId(any(Long.class));
//    }




    private PeopleRequest createNewPeopleRequest() {
        return PeopleRequest.builder()
                .id(1L)
                .name("Tamara B Luz")
                .birthDate(LocalDate.of(1989, 12, 1))
                .addresses(null)
                .build();
    }
    private People createNewPeople() {
        return People.builder()
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
    private Address createNewAddress() {
        return Address.builder()
                .id(1L)
                .cep("9832839823")
                .logradouro("cloves")
                .numero(12L)
                .cidade("cachoeirinha")
                .isPrincipal(false)
                .people(null)
                .build();
    }
}