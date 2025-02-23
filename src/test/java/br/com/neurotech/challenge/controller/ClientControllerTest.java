package br.com.neurotech.challenge.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.neurotech.challenge.dto.NeurotechClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private CreditService creditService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateClient() throws Exception {
        NeurotechClientDTO clientDTO = new NeurotechClientDTO("Alice", 30, 10000.0);
        NeurotechClient savedClient = new NeurotechClient(1L, "Alice", 30, 10000.0);

        when(clientService.save(Mockito.any(NeurotechClientDTO.class))).thenReturn(savedClient);

        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/client/1"));
    }

    @Test
    public void testGetClientById() throws Exception {
        NeurotechClient client = new NeurotechClient(1L, "Bob", 40, 10000.0);

        when(clientService.findById(1L)).thenReturn(Optional.of(client));

        mockMvc.perform(get("/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(jsonPath("$.age").value(40))
                .andExpect(jsonPath("$.income").value(10000.0));
    }

    @Test
    public void testCheckCreditEligibility() throws Exception {
        NeurotechClient client = new NeurotechClient(1L, "Bob", 30, 12000.0);

        when(clientService.findById(1L)).thenReturn(Optional.of(client));
        when(creditService.isClientEligibleForCredit(client, VehicleModel.HATCH)).thenReturn(true);

        mockMvc.perform(get("/client/1/credit?vehicleModel=HATCH"))
                .andExpect(status().isOk())
                .andExpect(content().string("Client is eligible for credit"));
    }

    @Test
    public void testGetEligibleClientsForFixedInterestHatch() throws Exception {
        NeurotechClient client1 = new NeurotechClient(1L, "John", 30, 10000.0);
        NeurotechClient client2 = new NeurotechClient(2L, "Doe", 40, 12000.0);

        when(clientService.getAllHatchEligibleClientsBetween23And49()).thenReturn(Arrays.asList(client1, client2));

        mockMvc.perform(get("/client/eligible/fixed-interest/hatch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].income").value(10000.0))
                .andExpect(jsonPath("$[1].name").value("Doe"))
                .andExpect(jsonPath("$[1].income").value(12000.0));
    }

}
