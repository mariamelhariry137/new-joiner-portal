package com.newjoinerportal.content.service;

import com.newjoinerportal.content.exception.DataIntegrityException;
import com.newjoinerportal.content.exception.DuplicateResource;
import com.newjoinerportal.content.exception.ResourceNotFound;
import com.newjoinerportal.content.model.Contact;
import com.newjoinerportal.content.model.Team;
import com.newjoinerportal.content.repo.ContactRepository;
import com.newjoinerportal.content.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private TeamRepository teamRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id).
                orElseThrow(()-> new ResourceNotFound("Contact", id));
    }

    public List<Contact> getContactsByTeamId(Long teamId) {
        if(!teamRepository.existsById(teamId)){
            throw new ResourceNotFound("Team", teamId);
        }
        return contactRepository.findByTeamId(teamId);
    }

    public Contact createContact(Contact contact, Long teamId) {
        if(contactRepository.findByEmail(contact.getEmail()).isPresent()){
            throw new DuplicateResource("Contact with email"+contact.getEmail()+"already exists");
        }
        if(teamId != null){
            Team team = teamRepository.findById(teamId).orElseThrow(()-> new ResourceNotFound("Team", teamId));
            contact.setTeam(team);
        }
        try{
            return contactRepository.save(contact);
        }catch (Exception e){
            throw new DataIntegrityException("failed to create contact: "+ e.getMessage());
        }
    }

    public Contact updateContact(Long id, Contact contactDetails, Long teamId) {
        Contact contact = getContactById(id);
        if(contactRepository.findByEmail(contactDetails.getEmail()).isPresent()
        && !contact.getEmail().equals(contactDetails.getEmail())){
            throw new DuplicateResource("Contact with email"+contactDetails.getEmail()+"already exists");
        }
        contact.setName(contactDetails.getName());
        contact.setEmail(contactDetails.getEmail());
        contact.setPhone(contactDetails.getPhone());

        if(teamId != null){
            Team team = teamRepository.findById(teamId).orElseThrow(()-> new ResourceNotFound("Team", teamId));
            contact.setTeam(team);
        }
        try{
            return contactRepository.save(contact);
        }catch (Exception e){
            throw new DataIntegrityException("failed to update contact: "+ e.getMessage());
        }
    }

    @Transactional
    public void deleteContact(Long id) {
        Contact contact = getContactById(id);
        try{
            contactRepository.delete(contact);
        }catch (Exception e){
            throw new DataIntegrityException("failed to delete contact" + e.getMessage());
        }
    }
}