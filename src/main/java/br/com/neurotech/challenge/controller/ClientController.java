package br.com.neurotech.challenge.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.neurotech.challenge.dto.EligibleClientDTO;
import br.com.neurotech.challenge.dto.NeurotechClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private CreditService creditService;

    @PostMapping
    public ResponseEntity<NeurotechClient> createClient(@Valid @RequestBody NeurotechClientDTO clientDTO) {
        NeurotechClient createdClient = clientService.save(clientDTO);

        URI location = URI.create(String.format("/api/client/%d", createdClient.getId()));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(createdClient, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getClientById(@PathVariable Long id) {
        try {
            Optional<NeurotechClient> client = clientService.findById(id);

            if (client.isEmpty()) {
                return ResponseEntity.status(404).body("Client not found");
            }

            return ResponseEntity.ok(client.get());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{id}/credit")
    public ResponseEntity<String> checkCreditEligibility(
            @PathVariable Long id,
            @RequestParam VehicleModel vehicleModel) {

        try {

            Optional<NeurotechClient> client = clientService.findById(id);

            if (client.isEmpty()) {
                return ResponseEntity.status(404).body("Client not found");
            }

            NeurotechClient neurotechClient = client.get();

            boolean isEligible = creditService.isClientEligibleForCredit(neurotechClient, vehicleModel);

            if (isEligible) {
                return ResponseEntity.ok("Client is eligible for credit");
            } else {
                return ResponseEntity.status(403).body("Client is not eligible for credit");
            }
        }

        catch (

        Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/eligible/fixed-interest/hatch")
    public ResponseEntity<List<EligibleClientDTO>> getEligibleClientsForFixedInterestHatch() {
        List<NeurotechClient> eligibleClients = clientService.getAllHatchEligibleClientsBetween23And49();

        List<EligibleClientDTO> result = eligibleClients.stream()
                .map(client -> new EligibleClientDTO(client.getName(), client.getIncome()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

}