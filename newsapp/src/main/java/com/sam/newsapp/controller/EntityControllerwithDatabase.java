package com.sam.newsapp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.newsapp.Services.UserServices;
import com.sam.newsapp.Services.journalEntryServices;
import com.sam.newsapp.entity.Users;
import com.sam.newsapp.entity.journalEntity;

import io.micrometer.core.instrument.Meter.Id;

@RestController
@RequestMapping("/entity")
public class EntityControllerwithDatabase {

    @Autowired
    private journalEntryServices JES;
    @Autowired
    private UserServices userServices;


    // Get All Entities of User//

    @GetMapping
   public ResponseEntity<?>getAllJournalEntitiesOfUser(){
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Username = authentication.getName();
    Users user =userServices.findByUsername(Username);
    List<journalEntity> all = user.getJournalEntities();
    if(all != null && !all.isEmpty()){
       return new ResponseEntity<>(all, HttpStatus.OK);
   }
   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

    /**
     * @param myData
     * @param Username
     * @return
     */

     // Add Entity to User//

    @PostMapping
    public ResponseEntity<journalEntity> create(@RequestBody journalEntity myData) {
       
        try {
             Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             String Username = authentication.getName();
            JES.SaveNewEntry(myData,Username);
            return new ResponseEntity<>(myData, HttpStatus.CREATED);
          
        }catch (RuntimeException e) {
        return new ResponseEntity<>(  HttpStatus.BAD_REQUEST);
    }  
        catch (Exception e) {
            e.printStackTrace(); // for debugging
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Entity by Id of User//

    @GetMapping("/find/{id}")
    public ResponseEntity<journalEntity> getById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Username = authentication.getName();
        Users user=userServices.findByUsername(Username);
        List<journalEntity> collect =user.getJournalEntities().stream().filter(x ->x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
             Optional<journalEntity> JournalEntity = JES.findById(id);
       if(JournalEntity.isPresent()){
              return new ResponseEntity<>(JournalEntity.get(), HttpStatus.OK);
       }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete Entity by Id of User//

    @DeleteMapping("/find/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Username = authentication.getName();
        Optional<journalEntity> JournalEntity = JES.findById(id);
        if (JournalEntity.isPresent()) {
            JES.deleteById(id,Username);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update Entity by Id of User//

 @PutMapping("/find/{id}")
public ResponseEntity<?> updtaeJournalById(
        @PathVariable Long id,
        @RequestBody journalEntity myData) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String Username = authentication.getName();
    Users user = userServices.findByUsername(Username);
    List<journalEntity> collect =user.getJournalEntities().stream().filter(x ->x.getId().equals(id)).collect(Collectors.toList());
    if(!collect.isEmpty()){
        Optional<journalEntity> existingEntityOpt = JES.findById(id);
        if (existingEntityOpt.isPresent()) {
            journalEntity old = existingEntityOpt.get();
            old.setTitle(myData.getTitle()!=null && !myData.getTitle().equals("") ? myData.getTitle() : old.getTitle());
            old.setDescription(myData.getDescription()!=null && !myData.getDescription().equals("") ? myData.getDescription() : old.getDescription());
            JES.SaveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}

}
        