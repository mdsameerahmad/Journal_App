package com.sam.newsapp.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sam.newsapp.Rspository.journalEntityRepository;
import com.sam.newsapp.entity.Users;
import com.sam.newsapp.entity.journalEntity;



@Component
public class journalEntryServices {

    @Autowired
    private journalEntityRepository journalEntityRepo;
    @Autowired UserServices userServices;

    
    public void SaveNewEntry(journalEntity JE, String Username) {
        Users user = userServices.findByUsername(Username);
         if (user == null) {
        throw new RuntimeException("User not found: " + Username);
    }
        JE.setUser(user);
        journalEntity saved=journalEntityRepo.save(JE);
        user.getJournalEntities().add(saved);
        userServices.SaveUser(user);

    }

        public void SaveEntry(journalEntity JE) {
       journalEntityRepo.save(JE);
    }

    public List<journalEntity> getAllEntries() {
        return journalEntityRepo.findAll();
    }

    public Optional<journalEntity> findById(Object id) {
        return journalEntityRepo.findById((Long) id);
    }

    @Transactional
    public void deleteById(Long id,String Username) {
         try{
            Users user = userServices.findByUsername(Username);
         boolean removed =user.getJournalEntities().removeIf(x -> x.getId().equals(id));
        if(removed){
             userServices.SaveUser(user);
        journalEntityRepo.deleteById(id);
        }
         }catch(Exception e){
           System.out.println(e.getMessage());
         }
    }


    public List<journalEntity> findByUsername(String Username) {
        return null;
        
    }
    
}
