package br.com.neurotech.challenge.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.neurotech.challenge.dto.NeurotechClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Optional;

public class ClientServiceTest {

    @Mock
    private NeurotechClientRepository repository;

    @InjectMocks
    private ClientService clientService;

    private NeurotechClient client;

    @BeforeEach
    public void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);

        // Configura um cliente padrão para testar
        client = new NeurotechClient();
        client.setName("John Doe");
        client.setAge(30);
        client.setIncome(10000.0);
    }

    @Test
    public void testGetAll() {

        when(repository.findAll()).thenReturn(Arrays.asList(client));

        var result = clientService.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(client.getName(), result.get(0).getName());
    }

    @Test
    public void testSave() {

        NeurotechClientDTO clientDTO = new NeurotechClientDTO("Jane Doe", 28, 12000.0);

        when(repository.save(any(NeurotechClient.class))).thenReturn(client);

        NeurotechClient savedClient = clientService.save(clientDTO);

        assertNotNull(savedClient);
        assertEquals(client.getName(), savedClient.getName());
        assertEquals(client.getAge(), savedClient.getAge());
        assertEquals(client.getIncome(), savedClient.getIncome());
    }

    @Test
    public void testFindById() {

        when(repository.findById(1L)).thenReturn(Optional.of(client));

        Optional<NeurotechClient> result = clientService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(client.getName(), result.get().getName());
    }

    @Test
    public void testFindById_ClientNotFound() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<NeurotechClient> result = clientService.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetAllHatchEligibleClientsBetween23And49() {

        when(repository.getAllHatchEligibleClientsBetween23And49()).thenReturn(Arrays.asList(client));

        var result = clientService.getAllHatchEligibleClientsBetween23And49();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(client.getName(), result.get(0).getName());
    }
}
