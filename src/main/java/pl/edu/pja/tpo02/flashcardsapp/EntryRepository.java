package pl.edu.pja.tpo02.flashcardsapp;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EntryRepository {
    List<Entry> dictionary = new ArrayList<>();
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
                dictionary.get(i).setEng(word);
                entityManager.find(Entry.class, i+1).setEng(word);
                break;
            case 2:
                dictionary.get(i).setGer(word);
                entityManager.find(Entry.class, i+1).setGer(word);
                break;
            case 3:
                dictionary.get(i).setPol(word);
                entityManager.find(Entry.class, i+1).setPol(word);
                break;
        }
    }
    @Transactional
    public void delEntry(int i){
        dictionary.remove(i);
        Optional.ofNullable(entityManager.find(Entry.class, i+1)).ifPresent(entityManager::remove);
    }
    public void addToRep(Entry e){
        dictionary.add(e);
    }

    public List<Entry> getDictionary(){
        return dictionary;
    }
}
