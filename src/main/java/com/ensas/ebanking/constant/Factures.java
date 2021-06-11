package com.ensas.ebanking.constant;

import com.ensas.ebanking.exceptions.domain.FactureNotFoundException;

import java.util.Dictionary;
import java.util.Hashtable;

public class Factures {

    private Dictionary<String, Double> factures = new Hashtable<>();

    public Factures(){
        factures.put("123456789", 300.00);
        factures.put("987654321", 120.00);
        factures.put("789632145", 560.24);
    }

    public Double get(String num_facture) throws FactureNotFoundException {

            Double result =  factures.get(num_facture);
            if (result == null) throw new FactureNotFoundException();
            return result;
        }
}

