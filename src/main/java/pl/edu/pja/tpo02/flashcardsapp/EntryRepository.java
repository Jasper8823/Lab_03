package pl.edu.pja.tpo02.flashcardsapp;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EntryRepository {
    public final SpringRepository springRepository;

    public EntryRepository(SpringRepository springRepository) {
        this.springRepository = springRepository;
    }
    public void addEntry(Entry e){
        springRepository.save(e);
    }

    @Transactional
    public void newWord(int i, int l, String word){
        Entry e = springRepository.findById(i+1);
        switch (l){
            case 1:
                e.setEng(word);
                springRepository.save(e);
                break;
            case 2:
                e.setGer(word);
                springRepository.save(e);
                break;
            case 3:
                e.setPol(word);
                springRepository.save(e);
                break;
        }
    }
    @Transactional
    public void delEntry(int i){
        Entry e = springRepository.findById(i);
        springRepository.delete(e);
    }
    public List<Entry> getDictionary(){
        return (List<Entry>) springRepository.findAll();
    }
}
