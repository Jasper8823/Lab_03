package pl.edu.pja.tpo02.flashcardsapp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class FileService {

    static EntryRepository entryRepository;

    @Autowired
    public FileService(EntryRepository er) {
        entryRepository=er;
        String url = "jdbc:h2:file:./FlashcardsApp";
        String username = "jasper";
        String password = "123456";
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT(*) FROM ENTRY";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            int rowCount = resultSet.getInt(1);
            if (rowCount == 0) {
                addToDb("cat","Katze"	,"kot");
                addToDb("dog","Hund"	,"pies");
                addToDb("mouse","Maus"	,"mysz");
                addToDb("stone","Stein"	,"kamie");
                addToDb("tree","Baum"	,"drzewo");
                addToDb("river","Fluss"	,"rzeka");
                addToDb("ear","Ohr"	,"ucho");
                addToDb("forest","Wald"	,"las");
                addToDb("Cabinet","Kabinett"	,"szafka");
                addToDb("bed","Bett"	,"ko");
                addToDb("rug","Teppich"	,"dywan");
                addToDb("window","Fenster"	,"okno");
                addToDb("lake","See"	,"jezioro");
                addToDb("sky","Himmel"	,"niebo");
                addToDb("cloud","Wolke"	,"chmura");
            } else {
                query = "SELECT * FROM ENTRY";
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String ang = resultSet.getString("ANG");
                    String ger = resultSet.getString("GER");
                    String pol = resultSet.getString("POL");
                    addEntry(ang,ger,pol);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void modifyWord(int ind,int lang,String newWord){
        entryRepository.newWord(ind,lang,newWord);
    }
    public void deleteEntry(int i){
        entryRepository.delEntry(i);
    }
    public List<Entry> getAllWods(){
        return entryRepository.getDictionary();
    }
    public void addToDb(String ang,String ger,String pol){
        Entry e = new Entry(entryRepository.getDictionary().size()+1,ang,ger,pol);
        entryRepository.addEntry(e);
        entryRepository.addToRep(e);
    }
    public void addEntry(String ang,String ger,String pol){
        Entry e = new Entry(entryRepository.getDictionary().size()+1,ang,ger,pol);
        entryRepository.addToRep(e);
    }
}
