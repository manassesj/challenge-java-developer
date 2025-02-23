package br.com.neurotech.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;

public class CreditServiceTest {

    private CreditService creditService;

    @BeforeEach
    public void setUp() {
        creditService = new CreditService();
    }

    @Test
    public void testIsClientEligibleForCredit_Hatch() {
        NeurotechClient client = new NeurotechClient(1L, "Alice", 30, 10000.0);
        boolean isEligible = creditService.isClientEligibleForCredit(client, VehicleModel.HATCH);
        assertThat(isEligible).isTrue();
    }

    @Test
    public void testIsClientEligibleForCredit_SUV() {
        NeurotechClient client = new NeurotechClient(1L, "Bob", 25, 9000.0);
        boolean isEligible = creditService.isClientEligibleForCredit(client, VehicleModel.SUV);
        assertThat(isEligible).isTrue();
    }

    @Test
    public void testIsClientNotEligibleForCredit() {
        NeurotechClient client = new NeurotechClient(1L, "Charlie", 19, 4000.0);
        boolean isEligible = creditService.isClientEligibleForCredit(client, VehicleModel.SUV);
        assertThat(isEligible).isFalse();
    }
}
