package com.example.paraulogic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final int[] idButtons = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7};
    private final int num_buttons = idButtons.length;
    private Button[] buttons;
    private UnsortedArraySet<Character> set;

    /**
     * Constructor de la clase
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    /**
     * Inicialización de los componentes
     *
     */
    private void initComponents() {
        this.set = new UnsortedArraySet<Character>(num_buttons);
        this.buttons = new Button[num_buttons];

        generateRandomArraySet();
        initButtons();
    }

    /**
     * Inicialización de los botones
     *
     */
    private void initButtons() {
        // Variable que nos permitira iterar de forma sencilla entre los vlaores del array "set"
        Iterator iterator = set.iterator();

        for (int i = 0; i < num_buttons; i++) {
            buttons[i] = findViewById(idButtons[i]);
            System.out.println((buttons[i].toString()));
            buttons[i].setText(iterator.next().toString());
        }
    }

    /**
     * Método que escribe la letra contenida en el botón que presiona el usuario para crear la
     * palabra deseada
     *
     * @param view
     */
    @SuppressLint("SetTextI18n")
    public void setLletra(View view) {
        // Adquirimos las variables de la interfaz que queremos modificar
        Button button = findViewById(view.getId());
        TextView word = findViewById(R.id.word);

        // Actualizamos el contenido
        String txtOriginal = (String) word.getText();
        word.setText(txtOriginal + button.getText());
    }

    /**
     * Método que genera un array random
     *
     */
    public void generateRandomArraySet() {
        for (int i = 0; i < num_buttons; i++) {
            generateLetter();
        }
    }

    /**
     * Método que genera la letra
     *
     */
    private void generateLetter() {
        // Generamos una letra aleatoria
        Random random = new Random(System.currentTimeMillis());
        int letter = 65 + random.nextInt(26);

        while(!set.add((char) letter)) {
            letter = 65 + random.nextInt(26);
        }
    }

    /**
     * Método que realiza la acción de barajar el array de letras contenidas en los botones azules
     *
     * @param view
     */
    public void shuffle(View view) {
        char[] letters = unsortedArraySetToCharArray();
        randomize(letters, num_buttons - 1);

        for (int i = 0; i < num_buttons - 1; i++) {
            buttons[i].setText(Character.toString(letters[i]));
        }
    }

    /**
     * Método que devuelve un char[] de los elementos de la lista ya creada
     *
     * @return
     */
    public char[] unsortedArraySetToCharArray() {
        char[] words = new char[num_buttons - 1];
        Iterator iterator = set.iterator();

        for (int i = 0; iterator.hasNext() && i < num_buttons - 1 ; i++) {
            words[i] = (char) iterator.next();
        }

        return words;
    }

    /**
     * Método que mezcla el array pasado por parámetro
     *
     * @param arr
     * @param n
     */
    public static void randomize(char[] arr, int n) {
        Random random = new Random();

        for (int i = n - 1; i > 0 ; i--) {
            int j = random.nextInt(i + 1);
            char tmp = arr[i];

            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    /**
     * Método que elimina la palabra del TextView introducida por el usuario
     *
     * @param view
     */
    public void suprimeix(View view) {
        TextView word = findViewById(R.id.word);
        String str = (String) word.getText();

        if (str.length() > 0) {
            word.setText(str.substring(0, str.length() - 1));
        }
    }
}