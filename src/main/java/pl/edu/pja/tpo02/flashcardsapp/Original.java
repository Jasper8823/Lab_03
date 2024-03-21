package pl.edu.pja.tpo02.flashcardsapp;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Service
@Profile("Org")
public class Original implements Printer{
    @Override
    public String getWord(String a){return a;}
}
