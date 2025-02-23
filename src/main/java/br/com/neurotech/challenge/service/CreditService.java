package br.com.neurotech.challenge.service;

import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;

@Service
public class CreditService {

	public boolean isClientEligibleForCredit(NeurotechClient client, VehicleModel vehicleModel) {
		if (vehicleModel == VehicleModel.HATCH) {
			return client.getIncome() >= 5000 && client.getIncome() <= 15000;
		} else if (vehicleModel == VehicleModel.SUV) {
			return client.getIncome() > 8000 && client.getAge() > 20;
		}
		return false;
	}

	public CreditType determineCreditType(NeurotechClient client) {
		int age = client.getAge();
		double income = client.getIncome();

		if (age >= 18 && age <= 25) {
			return CreditType.FIXED_INTEREST;
		} else if (age >= 21 && age <= 65 && income >= 5000 && income <= 15000) {
			return CreditType.VARIABLE_INTEREST;
		} else if (age > 65) {
			return CreditType.CONSIGNED;
		}

		return null;
	}

}
