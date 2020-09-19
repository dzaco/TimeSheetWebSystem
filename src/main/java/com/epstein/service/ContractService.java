package com.epstein.service;

import com.epstein.entity.Contract;
import com.epstein.entity.User;
import com.epstein.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {

    @Autowired
    private ContractRepository repository;

    public Contract saveContract(Contract contract) {
        return repository.save(contract);
    }

    public List<Contract> getContracts() {
        return repository.findAll();
    }
    public Contract getContractById(int id) {
        return repository.findById(id).orElse(new Contract());
    }

    public String deleteContract(int id) {
        repository.deleteById(id);
        return "Rodzaj umowy " + id + " został usunięty";
    }


    public Contract updateContract(Contract contract) {
        Contract con = repository.findById(contract.getId()).orElse(new Contract());
        con.setName( contract.getName() );
        return repository.save(con);
    }


}
