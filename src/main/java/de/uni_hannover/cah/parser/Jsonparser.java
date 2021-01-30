package de.uni_hannover.cah.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.uni_hannover.cah.card.Answer;
import de.uni_hannover.cah.card.Card;
import de.uni_hannover.cah.card.Question;
/**
 * Parsing the json-file to get all cards
 */
public class Jsonparser {

    public static class CardsJSON {
        public HashMap<String, Card[]> cards;
        public HashMap<String, Deck> decks;
    }

    public static class Deck {
        public String icon;
        public boolean offical;
        public String description;
        public String name;
        public int[] white;
        public int[] black;
    }

    /**
     * creates a hashmap with all cards
     */
    public static CardsJSON getAllCards() {
        InputStream inputStream = Jsonparser.class.getResourceAsStream("/compact.md.json");
        try {
            if (inputStream == null) {
                inputStream = new FileInputStream("./src/main/resources/compact.md.json");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(inputStream);
        Gson gson = new Gson();
        CardsJSON allCards = gson.fromJson(reader, CardsJSON.class);
        return allCards;
    }

    /**
     * Creates a questiondeck at the start of the game
     */
    public static ArrayList<Question> getQuestionDeck() {
        InputStream inputStream = Jsonparser.class.getResourceAsStream("/compact.md.json"); //.getResourceAsStream("compact.md.json");
        try {
            if (inputStream == null) {
                inputStream = new FileInputStream("./src/main/resources/compact.md.json");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(inputStream);
        Gson gson = new Gson();
        CardsJSON allCards = gson.fromJson(reader, CardsJSON.class);
        ArrayList<Question> questionDeck = new ArrayList<Question>();
        for (int i = 0; i < allCards.cards.get("black").length; i++) {
            questionDeck.add(new Question(allCards.cards.get("black")[i].text, allCards.cards.get("black")[i].pick, i));
        }
        return questionDeck;
    }

    /**
     * creates an answerdeck from which every player draw cards
     */
    public static ArrayList<Answer> getAnswerDeck() {
        InputStream inputStream = Jsonparser.class.getResourceAsStream("/compact.md.json");
        try {
            if (inputStream == null) {
                inputStream = new FileInputStream("./src/main/resources/compact.md.json");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(inputStream);
        Gson gson = new Gson();
        CardsJSON allCards = gson.fromJson(reader, CardsJSON.class);
        ArrayList<Answer> answerDeck = new ArrayList<Answer>();
        for (int i = 0; i < allCards.cards.get("white").length; i++) {
            answerDeck.add(new Answer(allCards.cards.get("white")[i].text, i));
        }
        return answerDeck;
    }
}
