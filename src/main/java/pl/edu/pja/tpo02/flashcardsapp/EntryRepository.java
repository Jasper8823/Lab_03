package pl.edu.pja.tpo02.flashcardsapp;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EntryRepository {
    private final EntityManager entityManager;

    public EntryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional
    public void addEntry(Entry e){
        entityManager.persist(e);
    }

    @Transactional
    public void newWord(int i, int l, String word){
        switch (l){
            case 1:
                entityManager.find(Entry.class, i).setEng(word);
                break;
            case 2:
                entityManager.find(Entry.class, i).setGer(word);
                break;
            case 3:
                entityManager.find(Entry.class, i).setPol(word);
                break;
        }
    }
    @Transactional
    public void delEntry(int i){
        Optional.ofNullable(entityManager.find(Entry.class, i+1)).ifPresent(entityManager::remove);
    }
    public List<Entry> getDictionary(){
        return entityManager.createQuery("SELECT e FROM Entry e", Entry.class).getResultList();
    }
}
