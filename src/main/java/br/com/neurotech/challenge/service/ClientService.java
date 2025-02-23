package br.com.neurotech.challenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.dto.NeurotechClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;

@Service
public class ClientService {

	@Autowired
	private NeurotechClientRepository repository;

	public List<NeurotechClient> getAll() {
		return repository.findAll();
	}

	public NeurotechClient save(NeurotechClientDTO clientDTO) {
		NeurotechClient client = new NeurotechClient();
		client.setName(clientDTO.getName());
		client.setAge(clientDTO.getAge());
		client.setIncome(clientDTO.getIncome());

		return repository.save(client);
	}

	public Optional<NeurotechClient> findById(Long clientId) {
		return repository.findById(clientId);
	}

	public List<NeurotechClient> getAllHatchEligibleClientsBetween23And49() {
		return repository.getAllHatchEligibleClientsBetween23And49();
	}
}
