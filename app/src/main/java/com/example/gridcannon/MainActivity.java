package com.example.gridcannon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    // todo Duncan please review this to make sure im not messing it up
    Stack<Card>[][] field;

    // The buttons that represent the stacks of cards on the table
    private ImageButton[][] fieldBtns;
    private ImageButton deckBtn;
    private ImageButton shameBtn;
    private Button newGameBtn;



    // The deck of cards
    private ArrayList<Card> deck = new ArrayList<Card>(54);

    // The stacks of cards
    private Stack<Card> royalStack;
    private Stack<Card> shameStack;

    TextView status;

    boolean playing;
    boolean reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldBtns = new ImageButton[5][5];
        field = new Stack[5][5];

        // Place-able Field Buttons
        fieldBtns[1][1] = findViewById(R.id.field0);
        fieldBtns[1][2] = findViewById(R.id.field1);
        fieldBtns[1][3] = findViewById(R.id.field2);

        fieldBtns[2][1] = findViewById(R.id.field3);
        fieldBtns[2][2] = findViewById(R.id.field4);
        fieldBtns[2][3] = findViewById(R.id.field5);

        fieldBtns[3][1] = findViewById(R.id.field6);
        fieldBtns[3][2] = findViewById(R.id.field7);
        fieldBtns[3][3] = findViewById(R.id.field8);

        // Royal Field Buttons
        fieldBtns[0][1] = findViewById(R.id.royal0);
        fieldBtns[0][2] = findViewById(R.id.royal1);
        fieldBtns[0][3] = findViewById(R.id.royal2);

        fieldBtns[1][0] = findViewById(R.id.royal3);
        fieldBtns[1][4] = findViewById(R.id.royal4);

        fieldBtns[2][0] = findViewById(R.id.royal5);
        fieldBtns[2][4] = findViewById(R.id.royal6);

        fieldBtns[3][0] = findViewById(R.id.royal7);
        fieldBtns[3][4] = findViewById(R.id.royal8);

        fieldBtns[4][1] = findViewById(R.id.royal9);
        fieldBtns[4][2] = findViewById(R.id.royal10);
        fieldBtns[4][3] = findViewById(R.id.royal11);

        // Deck, Shame, and new Game Buttons
        deckBtn = findViewById(R.id.deckBtn);
        shameBtn = findViewById(R.id.shameBtn);
        newGameBtn = findViewById(R.id.newGameBtn);
        status = findViewById(R.id.status);

        // Initialize Field and Deck
        setOnClick();
        newGame();
    }

    //pops the stack at the selected pos onto the bottom of the deck until the stack is empty
    private void resetStack(int x, int y) {

        Stack<Card> temp = new Stack<Card>();
        while (field[x][y].size() > 0) {
            temp.push(field[x][y].pop());
        }

        while (temp.size() > 0) {
            deck.add(temp.pop());
        }
    }

    private void trigger(int x, int y) {

        int[] dmg;
        dmg = dmg(x, y);

        if(dmg[0] > 0) {

            if (x == 1) {

                // King
                if(field[x+3][y].size() > 0 && field[x+3][y].peek().getValue() == 13) {
                    if (field[x + 3][y].peek().getValue() + field[x + 3][y].peek().getArmor() <= dmg[0] && field[x + 3][y].peek().getSuit() == field[x + 2][y].peek().getSuit() && field[x + 3][y].peek().getSuit() == field[x + 1][y].peek().getSuit() && field[x + 3][y].peek().getSuit() == field[x][y].peek().getSuit()) {
                        field[x + 3][y].peek().kill();
                    }
                }

                // Queen
                else if(field[x+3][y].size() > 0 && field[x+3][y].peek().getValue() == 12) {
                    if (field[x + 3][y].peek().getValue() + field[x + 3][y].peek().getArmor() <= dmg[0] && field[x + 3][y].peek().isRed() == field[x + 2][y].peek().isRed() && field[x + 3][y].peek().isRed() == field[x + 1][y].peek().isRed() && field[x + 3][y].peek().isRed() == field[x][y].peek().isRed()) {
                        field[x + 3][y].peek().kill();
                    }
                }

                else if (field[x + 3][y].size() > 0 && field[x + 3][y].peek().getValue() + field[x + 3][y].peek().getArmor() <= dmg[0]) {
                    field[x + 3][y].peek().kill();
                }

                else {

                }
            }

            else if (x == 3) {

                // King
                if (field[x - 3][y].size() > 0 && field[x - 3][y].peek().getValue() == 13) {
                    if (field[x - 3][y].peek().getValue() + field[x - 3][y].peek().getArmor() <= dmg[0] && field[x - 3][y].peek().getSuit() == field[x - 2][y].peek().getSuit() && field[x - 3][y].peek().getSuit() == field[x - 1][y].peek().getSuit() && field[x - 3][y].peek().getSuit() == field[x][y].peek().getSuit()) {
                        field[x - 3][y].peek().kill();
                    }
                }

                // Queen
                else if (field[x - 3][y].size() > 0 && field[x + 3][y].peek().getValue() == 12) {
                    if (field[x - 3][y].peek().getValue() + field[x - 3][y].peek().getArmor() <= dmg[0] && field[x - 3][y].peek().isRed() == field[x - 2][y].peek().isRed() && field[x - 3][y].peek().isRed() == field[x - 1][y].peek().isRed() && field[x - 3][y].peek().isRed() == field[x][y].peek().isRed()) {
                        field[x - 3][y].peek().kill();
                    }
                }

                else if (field[x - 3][y].size() > 0 && field[x - 3][y].peek().getValue() + field[x - 3][y].peek().getArmor() <= dmg[0]) {
                    field[x - 3][y].peek().kill();
                }

                else {
                    // nothing
                }
            }

            else {
                // nothing
            }

        }

        if (dmg[1] > 0) {

            if (y == 1) {

                // King
                if (field[x][y + 3].size() > 0 && field[x][y + 3].peek().getValue() == 13) {
                    if (field[x][y + 3].peek().getValue() + field[x][y + 3].peek().getArmor() <= dmg[0] && field[x][y + 3].peek().getSuit() == field[x][y + 2].peek().getSuit() && field[x][y + 3].peek().getSuit() == field[x][y + 1].peek().getSuit() && field[x][y + 3].peek().getSuit() == field[x][y].peek().getSuit()) {
                        field[x][y + 3].peek().kill();
                    }
                }
                // Queen
                else if (field[x][y + 3].size() > 0 && field[x + 3][y].peek().getValue() == 12) {
                    if (field[x][y + 3].peek().getValue() + field[x][y + 3].peek().getArmor() <= dmg[0] && field[x][y + 3].peek().isRed() == field[x][y + 2].peek().isRed() && field[x][y + 3].peek().isRed() == field[x][y + 1].peek().isRed() && field[x][y + 3].peek().isRed() == field[x][y].peek().isRed()) {
                        field[x][y + 3].peek().kill();
                    }
                }

                else if (field[x][y + 3].size() > 0 && field[x][y + 3].peek().getValue() + field[x][y + 3].peek().getArmor() <= dmg[0]) {
                    field[x][y + 3].peek().kill();
                }

                else {
                    //nothing. royal slot is null
                }

            }

            else if (y == 3) {

                // King
                if (field[x][y - 3].size() > 0 && field[x][y - 3].peek().getValue() == 13) {
                    if (field[x][y - 3].peek().getValue() + field[x][y - 3].peek().getArmor() <= dmg[0] && field[x][y - 3].peek().getSuit() == field[x][y - 2].peek().getSuit() && field[x][y - 3].peek().getSuit() == field[x][y - 1].peek().getSuit() && field[x][y - 3].peek().getSuit() == field[x][y].peek().getSuit()) {
                        field[x][y - 3].peek().kill();
                    }
                }

                // Queen
                else if (field[x][y - 3].size() > 0 && field[x - 3][y].peek().getValue() == 12) {
                    if (field[x][y - 3].peek().getValue() + field[x][y - 3].peek().getArmor() <= dmg[0] && field[x][y - 3].peek().isRed() == field[x][y - 2].peek().isRed() && field[x][y - 3].peek().isRed() == field[x][y - 1].peek().isRed() && field[x][y - 3].peek().isRed() == field[x][y].peek().isRed()) {
                        field[x][y - 3].peek().kill();
                    }
                }

                else if (field[x][y - 3].size() > 0 && field[x][y - 3].peek().getValue() + field[x][y - 3].peek().getArmor() <= dmg[0]) {
                    field[x][y - 3].peek().kill();
                }

                else {
                    //nothing. royal slot is null
                }
            }

            else {
                //nothing
            }
        }
    }

    //places card at selected position if all rules are met
    private void placeCard(int x, int y) {
        if (x == 9 && y == 9) {
            shameStack.push(deck.get(0));
            reset = true;
            shameBtn.setImageResource(getResources().getIdentifier(shameStack.peek().getImage(), "drawable", getPackageName()));
        }
        else if (deck.get(0).isWild()) {
            resetStack(x, y);
            field[x][y].push(deck.get(0));
            fieldBtns[x][y].setImageResource(getResources().getIdentifier(field[x][y].peek().getImage(), "drawable", getPackageName()));

            trigger(x, y);
        }
        else if (field[x][y].peek().getValue() <= deck.get(0).getValue()) {
            field[x][y].push(deck.get(0));
            fieldBtns[x][y].setImageResource(getResources().getIdentifier(field[x][y].peek().getImage(), "drawable", getPackageName()));
            deck.remove(0);
            trigger(x, y);
        }
        else {
            // nothing, invalid section.
        }

        if (!reset) {
            while(deck.get(0).isRoyal()) {
                royalStack.push(deck.get(0));
            }
            deck.remove(0);
            placeRoyals();

            checkGameOver();
        }
    }

    private int[] dmg(int x, int y) {
        int[] dmg = new int[2];     // opposing x, opposing y

        if(x == 1 && y == 1) {
            dmg[0] = field[x + 1][y].peek().getValue() + field[x + 2][y].peek().getValue();     //right
            dmg[1] = field[x][y + 1].peek().getValue() + field[x][y + 2].peek().getValue();     //down
        }

        else if(x == 1 && y == 3) {
                dmg[0] = field[x + 1][y].peek().getValue() + field[x + 2][y].peek().getValue();     //right
                dmg[1] = field[x][y - 1].peek().getValue() + field[x][y - 2].peek().getValue();     //up
            }

        else if(x == 1) {
                dmg[0] = field[x + 1][y].peek().getValue() + field[x + 2][y].peek().getValue();     //right
                dmg[1] = 0;
            }

        else if(x == 2 && y == 1) {
                dmg[0] = 0;
                dmg[1] = field[x][y + 1].peek().getValue() + field[x][y + 2].peek().getValue();     //down
            }

        else if(x == 2 && y == 3) {
                dmg[0] = 0;
                dmg[1] = field[x][y - 1].peek().getValue() + field[x][y - 2].peek().getValue();     //up
            }

        else if(x == 3 && y == 1) {
                dmg[0] = field[x = 1][y].peek().getValue() + field[x - 2][y].peek().getValue();     //left
                dmg[1] = field[x][y + 1].peek().getValue() + field[x][y + 2].peek().getValue();     //down
            }

        else if(x == 3 && y == 3) {
                dmg[0] = field[x - 1][y].peek().getValue() + field[x - 2][y].peek().getValue();     //left
                dmg[1] = field[x][y - 1].peek().getValue() + field[x][y - 2].peek().getValue();     //up
            }

        else if(x == 3) {
                dmg[0] = field[x - 1][y].peek().getValue() + field[x - 2][y].peek().getValue();     //left
                dmg[1] = 0;
            }

        return dmg;
    }

    private void lockGame(boolean win){
        playing = false;

        if(win)
            status.setText("Win");
        else
            status.setText("Loss");
    }

    private void checkGameOver(){
        int counter = 0;

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (field[i][j].size() > 0 && field[i][j].peek().isDead())
                    counter++;

        if(counter == 12)
            lockGame(true);

        if(deck.size() == 0)
            lockGame(false);
    }

    private void shuffle(){
        ArrayList<Card> temp = new ArrayList<Card>(54);
        Random random = new Random();

        for(int i = 0; i < 54; i++) {
            int rand = random.nextInt(54 - i);
            temp.add(deck.get(rand));
            deck.remove(rand);
        }

        for(int i = 0; i < 54; i++) {
            int rand = random.nextInt(54 - i);
            deck.add(temp.get(rand));
            temp.remove(rand);
        }
    }

    private void deal(){
        for(int i = 1; i < 4; i++)
            for(int j = 1; j < 4; j++)
                if(!(i == 2 && j == 2)){
                    while(deck.get(0).isRoyal()){
                        royalStack.push(deck.get(0));
                        deck.remove(0);
                    }
                    field[i][j].push(deck.get(0));
                    deck.remove(0);
                }

        placeRoyals();
    }

    private void placeRoyals(){
        while(royalStack.size() > 0){
            int x = 0, y = 0;

            for(int i = 1; i < 4; i++)
                for(int j = 1; j < 4; j++)
                    if(!(i == 2 && i == 2))
                        if((field[i+1][j].size() > 0 && !(field[i+1][j].peek().isRoyal() && (field[i][j+1].size() > 0 || field[i][j-1].size() > 0))) ||
                                (field[i-1][j].size() > 0 && !(field[i-1][j].peek().isRoyal() && (field[i][j+1].size() > 0 || field[i][j-1].size() > 0))) ||
                                (field[i][j+1].size() > 0 && !(field[i][j+1].peek().isRoyal() && (field[i+1][j].size() > 0 || field[i-1][j].size() > 0))) ||
                                (field[i][j-1].size() > 0 && !(field[i][j-1].peek().isRoyal() && (field[i+1][j].size() > 0 || field[i-1][j].size() > 0))))
                            if(field[i][j].peek().getSuit() == royalStack.peek().getSuit())
                                if(x == 0 || field[i][j].peek().getValue() > field[x][y].peek().getValue()) {
                                    x = i;
                                    y = j;
                                }
            if(x == 0)
                for(int i = 0; i < 4; i++)
                    for(int j = 0; j < 4; j++)
                        if(!(i == 2 && j == 2))
                            //Check surrounding cards to see if there is an available royals slot
                            if((field[i+1][j].size() > 0 && !(field[i+1][j].peek().isRoyal() && (field[i][j+1].size() > 0 || field[i][j-1].size() > 0))) ||
                                    (field[i-1][j].size() > 0 && !(field[i-1][j].peek().isRoyal() && (field[i][j+1].size() > 0 || field[i][j-1].size() > 0))) ||
                                    (field[i][j+1].size() > 0 && !(field[i][j+1].peek().isRoyal() && (field[i+1][j].size() > 0 || field[i-1][j].size() > 0))) ||
                                    (field[i][j-1].size() > 0 && !(field[i][j-1].peek().isRoyal() && (field[i+1][j].size() > 0 || field[i-1][j].size() > 0))))
                                if(field[i][j].peek().isRed() == royalStack.peek().isRed())
                                    if(x == 0 || field[i][j].peek().getValue() > field[x][y].peek().getValue()) {
                                        x = i;
                                        y = j;
                                    }
            if(x == 0)
                for(int i = 0; i < 4; i++)
                    for(int j = 0; j < 4; j++)
                        if(!(i == 2 && j == 2))
                            //Check surrounding cards to see if there is an available royals slot
                            if((field[i+1][j].size() > 0 && !(field[i+1][j].peek().isRoyal() && (field[i][j+1].size() > 0 || field[i][j-1].size() > 0))) ||
                                    (field[i-1][j].size() > 0 && !(field[i-1][j].peek().isRoyal() && (field[i][j+1].size() > 0 || field[i][j-1].size() > 0))) ||
                                    (field[i][j+1].size() > 0 && !(field[i][j+1].peek().isRoyal() && (field[i+1][j].size() > 0 || field[i-1][j].size() > 0))) ||
                                    (field[i][j-1].size() > 0 && !(field[i][j-1].peek().isRoyal() && (field[i+1][j].size() > 0 || field[i-1][j].size() > 0))))
                                if(x == 0 || field[i][j].peek().getValue() > field[x][y].peek().getValue()) {
                                    x = i;
                                    y = j;
                                }

            selectRoyal(x, y);
            if(!(x == 0 && (y == 0 || y == 5)) && !(x == 0 && (y == 0 || y == 5)))
                fieldBtns[x][y].setImageResource(getResources().getIdentifier(field[x][y].peek().getImage(), "drawable", getPackageName()));
        }
    }

    private void selectRoyal(int x, int y){
        if(x == 1)
            if(y == 1)
                if(field[x-1][y].size() > 0)
                    y--;
                else if(field[x][y-1].size() > 0)
                    x--;
                else //both are null
                    x--; //user selection later
        if(y == 2)
            x--;
        if(y == 3)
            if(field[x-1][y].size() > 0)
                y++;
            else if(field[x][y+1].size() > 0)
                x--;
            else //both are null
                y++; //user selection later
        else if(x == 2)
            if(y == 1)
                y--;
        if(y == 3)
            y++;
        else if(x == 3)
            if(y == 1)
                if(field[x+1][y].size() > 0)
                    y--;
                else if(field[x][y-1].size() > 0)
                    x++;
                else //both are null
                    y--; //user selection later
        if(y == 2)
            x++;
        if(y == 3)
            if(field[x+1][y].size() > 0)
                y++;
            else if(field[x][y+1].size() > 0)
                x++;
            else //both are null
                x++; //user selection later
        else{}
            //error

        field[x][y].push(royalStack.pop());
    }

    private void setOnClick(){
        fieldBtns[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playing)
                    placeCard(1, 1);
                if(reset){
                    resetStack(1, 1);
                    while(deck.get(0).isRoyal()){
                        royalStack.push(deck.get(0));
                        placeRoyals();
                    }
                }
            }
        });

        fieldBtns[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playing)
                    placeCard(1, 2);
                if(reset){
                    resetStack(1, 2);
                    while(deck.get(0).isRoyal()){
                        royalStack.push(deck.get(0));
                        placeRoyals();
                    }
                }
            }
        });

        fieldBtns[1][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playing)
                    placeCard(1, 3);
                if(reset){
                    resetStack(1, 3);
                    while(deck.get(0).isRoyal()){
                        royalStack.push(deck.get(0));
                        placeRoyals();
                    }
                }
            }
        });

        fieldBtns[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playing)
                    placeCard(1, 1);
                if(reset){
                    resetStack(1, 1);
                    while(deck.get(0).isRoyal()){
                        royalStack.push(deck.get(0));
                        placeRoyals();
                    }
                }
            }
        });

        fieldBtns[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playing)
                    placeCard(1, 2);
                if(reset){
                    resetStack(1, 2);
                    while(deck.get(0).isRoyal()){
                        royalStack.push(deck.get(0));
                        placeRoyals();
                    }
                }
            }
        });

        fieldBtns[2][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playing)
                    placeCard(1, 3);
                if(reset){
                    resetStack(1, 3);
                    while(deck.get(0).isRoyal()){
                        royalStack.push(deck.get(0));
                        placeRoyals();
                    }
                }
            }
        });

        fieldBtns[3][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playing)
                    placeCard(1, 1);
                if(reset){
                    resetStack(1, 1);
                    while(deck.get(0).isRoyal()){
                        royalStack.push(deck.get(0));
                        placeRoyals();
                    }
                }
            }
        });

        fieldBtns[3][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playing)
                    placeCard(1, 2);
                if(reset){
                    resetStack(1, 2);
                    while(deck.get(0).isRoyal()){
                        royalStack.push(deck.get(0));
                        placeRoyals();
                    }
                }
            }
        });

        fieldBtns[3][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playing)
                    placeCard(1, 3);
                if(reset){
                    resetStack(1, 3);
                    while(deck.get(0).isRoyal()){
                        royalStack.push(deck.get(0));
                        placeRoyals();
                    }
                }
            }
        });

        shameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playing)
                    placeCard(9, 9);
            }
        });

        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
            }
        });
    }

    private void newGame(){
        status.setText("Playing");
        playing = true;
        reset = false;
        royalStack = new Stack<Card>();
        shameStack = new Stack<Card>();

        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                if(!(i == 0 && (j == 0 || j == 5)) && !(i == 5 && (j == 0 || j == 5)))
                    field[i][j] = new Stack<Card>();

        init();
        deal();

        //shameBtn
    }

    private void init() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if((i == 0 && j == 0) || (i == 0 && j == 4) || (i == 4 && j == 0) || (i == 4 && j == 4))
                    continue;

                else
                    field[i][j] = new Stack<Card>();

            }
        }

        for(int i = 0; i < 54; i++) {

            // Jokers
            if (i < 2)  // +2
                deck.add(new Card(false, true, false, 0, 'J'));

                // Ace of Spades
            else if (i == 2)  // + 1
                deck.add(new Card(false, true, false, 1, 'S'));


                // Spades Numbers
            else if (i < 12)//+10
                deck.add(new Card(false, false, false, i - 1, 'S'));

                // Spades Royals
            else if (i < 15)//+3
                deck.add(new Card(true, false, false, i - 1, 'S'));

                // Ace of Clubs
            else if (i == 15) // + 1
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
        }
            // Initializing card images


            // Jokers
            deck.get(0).imageName = "joker";
            deck.get(1).imageName = "joker";

            // Ace of Spades
            deck.get(2).imageName = "acespades";

            // Spade Numbers
            deck.get(3).imageName = "spades2";
            deck.get(4).imageName = "spades3";
            deck.get(5).imageName = "spades4";
            deck.get(6).imageName = "spades5";
            deck.get(7).imageName = "spades6";
            deck.get(8).imageName = "spades7";
            deck.get(9).imageName = "spades8";
            deck.get(10).imageName = "spades9";
            deck.get(11).imageName = "spades10";

            // Spade Royals
            deck.get(12).imageName = "jackspades";
            deck.get(13).imageName = "queenspades";
            deck.get(14).imageName = "kingspades";

            // Ace of Clubs
            deck.get(15).imageName = "aceclubs";

            // Club Numbers
            deck.get(16).imageName = "clubs2";
            deck.get(17).imageName = "clubs3";
            deck.get(18).imageName = "clubs4";
            deck.get(19).imageName = "clubs5";
            deck.get(20).imageName = "clubs6";
            deck.get(21).imageName = "clubs7";
            deck.get(22).imageName = "clubs8";
            deck.get(23).imageName = "clubs9";
            deck.get(24).imageName = "clubs10";

            // Club Royals
            deck.get(25).imageName = "jackclubs";
            deck.get(26).imageName = "queenclubs";
            deck.get(27).imageName = "kingclubs";

            // Ace of Hearts
            deck.get(28).imageName = "acehearts";

            // Hearts Numbers
            deck.get(29).imageName = "hearts2";
            deck.get(30).imageName = "hearts3";
            deck.get(31).imageName = "hearts4";
            deck.get(32).imageName = "hearts5";
            deck.get(33).imageName = "hearts6";
            deck.get(34).imageName = "hearts7";
            deck.get(35).imageName = "hearts8";
            deck.get(36).imageName = "hearts9";
            deck.get(37).imageName = "hearts10";

            // Hearts Royals
            deck.get(38).imageName = "jackhearts";
            deck.get(39).imageName = "queenhearts";
            deck.get(40).imageName = "kinghearts";

            // Ace of Diamonds
            deck.get(41).imageName = "acediamonds";

            // Diamonds Numbers
            deck.get(42).imageName = "diamonds2";
            deck.get(43).imageName = "diamonds3";
            deck.get(44).imageName = "diamonds4";
            deck.get(45).imageName = "diamonds5";
            deck.get(46).imageName = "diamonds6";
            deck.get(47).imageName = "diamonds7";
            deck.get(48).imageName = "diamonds8";
            deck.get(49).imageName = "diamonds9";
            deck.get(50).imageName = "diamonds10";

            // Diamonds Royals
            deck.get(51).imageName = "jackdiamonds";
            deck.get(52).imageName = "queendiamonds";
            deck.get(53).imageName = "kingdiamonds";


            /*
            // Jokers
            deck.get(0).card.setImageResource(R.drawable.joker);
            deck.get(0).id = R.drawable.joker;

            deck.get(1).card.setImageResource(R.drawable.joker);
            deck.get(0).id = R.drawable.joker;

            // Ace of Spades
            deck.get(2).card.setImageResource(R.drawable.acespades);
            deck.get(2).id = R.drawable.acespades;

            // Spade Numbers
            deck.get(3).card.setImageResource(R.drawable.spades2);
            deck.get(3).id = R.drawable.spades2;

            deck.get(4).card.setImageResource(R.drawable.spades3);
            deck.get(4).id = R.drawable.spades3;

            deck.get(5).card.setImageResource(R.drawable.spades4);
            deck.get(5).id = R.drawable.spades4;

            deck.get(6).card.setImageResource(R.drawable.spades5);
            deck.get(6).id = R.drawable.spades5;

            deck.get(7).card.setImageResource(R.drawable.spades6);
            deck.get(7).id = R.drawable.spades6;

            deck.get(8).card.setImageResource(R.drawable.spades7);
            deck.get(8).id = R.drawable.spades7;

            deck.get(9).card.setImageResource(R.drawable.spades8);
            deck.get(9).id = R.drawable.spades8;

            deck.get(10).card.setImageResource(R.drawable.spades9);
            deck.get(10).id = R.drawable.spades9;

            deck.get(11).card.setImageResource(R.drawable.spades10);
            deck.get(11).id = R.drawable.spades10;

            // Spade Royals
            deck.get(12).card.setImageResource(R.drawable.jackspades);
            deck.get(12).id = R.drawable.jackspades;

            deck.get(13).card.setImageResource(R.drawable.queenspades);
            deck.get(13).id = R.drawable.queenspades;

            deck.get(14).card.setImageResource(R.drawable.kingspades);
            deck.get(14).id = R.drawable.kingspades;

            //=======================================================

            // Ace of Clubs
            deck.get(15).card.setImageResource(R.drawable.aceclubs);
            deck.get(15).id = R.drawable.aceclubs;


            // Clubs Numbers
            deck.get(16).card.setImageResource(R.drawable.clubs2);
            deck.get(16).id = R.drawable.clubs2;

            deck.get(17).card.setImageResource(R.drawable.clubs3);
            deck.get(17).id = R.drawable.clubs3;

            deck.get(18).card.setImageResource(R.drawable.clubs4);
            deck.get(18).id = R.drawable.clubs4;

            deck.get(19).card.setImageResource(R.drawable.clubs5);
            deck.get(19).id = R.drawable.clubs5;

            deck.get(20).card.setImageResource(R.drawable.clubs6);
            deck.get(20).id = R.drawable.clubs6;

            deck.get(21).card.setImageResource(R.drawable.clubs7);
            deck.get(21).id = R.drawable.clubs7;

            deck.get(22).card.setImageResource(R.drawable.clubs8);
            deck.get(22).id = R.drawable.clubs8;

            deck.get(23).card.setImageResource(R.drawable.clubs9);
            deck.get(23).id = R.drawable.clubs9;

            deck.get(24).card.setImageResource(R.drawable.clubs10);
            deck.get(24).id = R.drawable.clubs10;

            // Clubs Royals
            deck.get(25).card.setImageResource(R.drawable.jackclubs);
            deck.get(25).id = R.drawable.jackclubs;

            deck.get(26).card.setImageResource(R.drawable.queenclubs);
            deck.get(26).id = R.drawable.queenclubs;

            deck.get(27).card.setImageResource(R.drawable.kingclubs);
            deck.get(27).id = R.drawable.kingclubs;


            //=======================================================

            // Ace of Hearts
            deck.get(28).card.setImageResource(R.drawable.acehearts);
            deck.get(28).id = R.drawable.acehearts;

            // Hearts Numbers
            deck.get(29).card.setImageResource(R.drawable.hearts2);
            deck.get(29).id = R.drawable.hearts2;

            deck.get(30).card.setImageResource(R.drawable.hearts3);
            deck.get(30).id = R.drawable.hearts3;

            deck.get(31).card.setImageResource(R.drawable.hearts4);
            deck.get(31).id = R.drawable.hearts4;

            deck.get(32).card.setImageResource(R.drawable.hearts5);
            deck.get(32).id = R.drawable.hearts5;

            deck.get(33).card.setImageResource(R.drawable.hearts6);
            deck.get(33).id = R.drawable.hearts6;

            deck.get(34).card.setImageResource(R.drawable.hearts7);
            deck.get(34).id = R.drawable.hearts7;

            deck.get(35).card.setImageResource(R.drawable.hearts8);
            deck.get(35).id = R.drawable.hearts8;

            deck.get(36).card.setImageResource(R.drawable.hearts9);
            deck.get(36).id = R.drawable.hearts9;

            deck.get(37).card.setImageResource(R.drawable.hearts10);
            deck.get(37).id = R.drawable.hearts10;

            // Hearts Royals
            deck.get(38).card.setImageResource(R.drawable.jackhearts);
            deck.get(38).id = R.drawable.jackhearts;

            deck.get(39).card.setImageResource(R.drawable.queenhearts);
            deck.get(39).id = R.drawable.queenhearts;

            deck.get(40).card.setImageResource(R.drawable.kinghearts);
            deck.get(40).id = R.drawable.kinghearts;


            //=======================================================

            // Ace of Diamonds
            deck.get(41).card.setImageResource(R.drawable.acediamonds);
            deck.get(41).id = R.drawable.acediamonds;

            // Diamonds Numbers
            deck.get(42).card.setImageResource(R.drawable.diamonds2);
            deck.get(42).id = R.drawable.diamonds2;

            deck.get(43).card.setImageResource(R.drawable.diamonds3);
            deck.get(43).id = R.drawable.diamonds3;

            deck.get(44).card.setImageResource(R.drawable.diamonds4);
            deck.get(44).id = R.drawable.diamonds4;

            deck.get(45).card.setImageResource(R.drawable.diamonds5);
            deck.get(45).id = R.drawable.diamonds5;

            deck.get(46).card.setImageResource(R.drawable.diamonds6);
            deck.get(46).id = R.drawable.diamonds6;

            deck.get(47).card.setImageResource(R.drawable.diamonds7);
            deck.get(47).id = R.drawable.diamonds7;

            deck.get(48).card.setImageResource(R.drawable.diamonds8);
            deck.get(48).id = R.drawable.diamonds8;

            deck.get(49).card.setImageResource(R.drawable.diamonds9);
            deck.get(49).id = R.drawable.diamonds9;

            deck.get(50).card.setImageResource(R.drawable.diamonds10);
            deck.get(50).id = R.drawable.diamonds10;

            // Diamonds Royals
            deck.get(51).card.setImageResource(R.drawable.jackdiamonds);
            deck.get(51).id = R.drawable.jackdiamonds;

            deck.get(52).card.setImageResource(R.drawable.queendiamonds);
            deck.get(52).id = R.drawable.queendiamonds;

            deck.get(53).card.setImageResource(R.drawable.kingdiamonds);
            deck.get(53).id = R.drawable.kingdiamonds;

             */

            // Throw exception?


        shuffle();
    }
}


