package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.NeurotechClient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NeurotechClientRepository extends JpaRepository<NeurotechClient, Long> {

    @Query("SELECT nc FROM NeurotechClient nc WHERE nc.income BETWEEN 5000 AND 15000 AND nc.age BETWEEN 23 AND 49")
    List<NeurotechClient> getAllHatchEligibleClientsBetween23And49();

}
