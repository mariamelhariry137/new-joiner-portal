package com.newjoinerportal.content.service;

import com.newjoinerportal.content.model.Policy;
import com.newjoinerportal.content.repo.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id).orElse(null);
    }

    public Policy createPolicy(Policy policy) {
        return policyRepository.save(policy);
    }

    public Policy updatePolicy(Long id, Policy policyDetails) {
        Policy policy = getPolicyById(id);
        if (policy != null) {
            policy.setTitle(policyDetails.getTitle());
            policy.setDescription(policyDetails.getDescription());
            return policyRepository.save(policy);
        }
        return null;
    }

    public void deletePolicy(Long id) {
        policyRepository.deleteById(id);
    }
}