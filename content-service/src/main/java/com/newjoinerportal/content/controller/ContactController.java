package com.newjoinerportal.content.controller;

import com.newjoinerportal.content.model.Contact;
import com.newjoinerportal.content.service.ContactService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Contact contact = contactService.getContactById(id);
        return ResponseEntity.ok(contact);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Contact>> getContactsByTeamId(@PathVariable Long teamId) {
        return ResponseEntity.ok(contactService.getContactsByTeamId(teamId));
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact, @RequestParam(required = false) Long teamId) {
        Contact createdContact = contactService.createContact(contact,teamId);
        return new ResponseEntity<>(createdContact,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @Valid @RequestBody Contact contact, @RequestParam(required = false)Long teamId) {
        Contact updatedContact = contactService.updateContact(id, contact, teamId);
        return ResponseEntity.ok(updatedContact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
