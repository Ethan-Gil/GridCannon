package com.example.gridcannon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Represents the stacks of cards on the field.
    CardStack[][] field;

    // The buttons that represent the stacks of cards on the table
    private Button[][] fieldBtns;
    private Button shameBtn;
    private Button newGameBtn;

    // The deck of cards
    private ArrayList deck = new ArrayList<Card>(54);

    // Royal & Shame stacks of cards
    private CardStack royalDeck;
    private CardStack shameDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldBtns = new Button[5][5];

        // Initialize Field and Deck
        init();
    }

    //pops the stack at the selected pos onto the bottom of the deck until the stack is empty
    void resetStack(int x, int y) {

        CardStack temp = new CardStack();
        while (field[x][y].size > 0) {
            temp.push(field[x][y].pop());
        }

        while (temp.size > 0) {
            deck.add(temp.pop());
        }
    }

    public void init() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if((i == 0 && j == 0) || (i == 0 && j == 4) || (i == 4 && j == 0) || (i == 4 && j == 4))
                    continue;

                else
                    field[i][j] = new CardStack();

            }
        }

    //dont do setonclick or newgame

        // work on reset stack && place card

        for(int i = 0; i < 54; i++) {

            // Jokers
            if (i < 2)
                deck.add(new Card(false, true, false, 0, 'J'));

            // Ace of Spades
            else if (i == 2)
                deck.add(new Card(false, true, false, 1, 'S'));

            // Spades Numbers
            else if (i < 12)//+10
                deck.add(new Card(false, false, false, i - 1, 'S'));

            // Spades Royals
            else if (i < 15)//+3
                deck.add(new Card(true, false, false, i - 1, 'S'));

            // Ace of Clubs
            else if (i == 15)
                deck.add(new Card(false, true, false, 1, 'C'));

            // Clubs Number
            else if (i < 25)//+10
                deck.add(new Card(false, false, false, i - 14, 'C'));

            // CLubs Royals
            else if (i < 28)//+3
                deck.add(new Card(true, false, false, i - 14, 'C'));

            // Ace of Hearts
            else if (i == 28)
                deck.add(new Card(false, true, false, 1, 'H'));

            // Hearts Numbers
            else if (i < 38)//+10
                deck.add(new Card(false, false, false, i - 28, 'H'));

            // Hearts Royals
            else if (i < 41)//+3
                deck.add(new Card(true, false, false, i - 28, 'H'));

            // Ace of Diamonds
            else if (i == 41)
                deck.add(new Card(false, true, false, 1, 'D'));

            // Diamonds Numbers
            else if (i < 51)//+10
                deck.add(new Card(false, false, false, i - 41, 'D'));

            // Diamonds Royals
            else if (i < 54)//+3
                deck.add(new Card(true, false, false, i - 41, 'D'));

            // Throw exception?
        }

    }


}
