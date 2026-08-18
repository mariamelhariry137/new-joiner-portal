package com.newjoinerportal.content.service;

import com.newjoinerportal.content.exception.DataIntegrityException;
import com.newjoinerportal.content.exception.DuplicateResource;
import com.newjoinerportal.content.exception.ResourceNotFound;
import com.newjoinerportal.content.model.Contact;
import com.newjoinerportal.content.model.Policy;
import com.newjoinerportal.content.model.Team;
import com.newjoinerportal.content.repo.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id).
                orElseThrow(()-> new ResourceNotFound("Policy", id));    }

    public Policy createPolicy(Policy policy) {
        if(policyRepository.findByTitle(policy.getTitle()).isPresent()){
            throw new DuplicateResource("Policy with title"+policy.getTitle()+"already exists");
        }
        try{
            return policyRepository.save(policy);
        }catch (Exception e){
            throw new DataIntegrityException("failed to create policy: "+ e.getMessage());
        }
    }

    public Policy updatePolicy(Long id, Policy policyDetails) {
        Policy policy = getPolicyById(id);
        if(policyRepository.findByTitle(policyDetails.getTitle()).isPresent()
                && !policy.getTitle().equals(policyDetails.getTitle())){
            throw new DuplicateResource("Policy with title"+policyDetails.getTitle()+"already exists");
        }
        policy.setTitle(policyDetails.getTitle());
        policy.setDescription(policyDetails.getDescription());

        try{
            return policyRepository.save(policy);
        }catch (Exception e){
            throw new DataIntegrityException("failed to update policy: "+ e.getMessage());
        }
    }
    @Transactional
    public void deletePolicy(Long id) {
        Policy policy = getPolicyById(id);
        try{
            policyRepository.delete(policy);
        }catch (Exception e){
            throw new DataIntegrityException("failed to delete policy" + e.getMessage());
        }    }
}
