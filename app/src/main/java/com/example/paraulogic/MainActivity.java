package com.example.paraulogic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    private final int[] idButtons = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7};
    private final int num_buttons = idButtons.length;
    private Button[] buttons;
    private UnsortedArraySet<Character> set;
    private BSTMapping<String, Integer> bst;
    private int numWords;
    private TreeSet treeSet;

    public static final String EXTRA_MESSAGE = "com.example.paraulogic.MESSAGE";

    /**
     * Constructor de la clase
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            initComponents();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * Inicialización de los componentes
     *
     */
    private void initComponents() throws Exception {
        this.set = new UnsortedArraySet<Character>(num_buttons);
        this.buttons = new Button[num_buttons];
        this.bst = new BSTMapping<String, Integer>();
        this.numWords = 0;
        this.treeSet = new TreeSet<String>();

        generarArbolDiccionario();
        generateRandomArraySet();
        initButtons();
    }

    private void generarArbolDiccionario() throws Exception {
        InputStream is = getResources().openRawResource(R.raw.catala_filtrat);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String word = br.readLine();
        while (word != null) {
            if (word.length() >= 3) {
                treeSet.add(word);
            }

            word = br.readLine();
        }
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

        // Quitamos el error
        word.setBackgroundColor(0);

        // Actualizamos el contenido
        String txtOriginal = (String) word.getText();
        word.setText(txtOriginal + button.getText());
    }

    /**
     * Método que genera un array random
     *
     */
    public void generateRandomArraySet() {
        // Mientras el conjunto de letras no forme un tuti, seguimos creando conjuntos
        while (!isTuti()) {
            System.out.println("No es tuti");
            for (int i = 0; i < num_buttons; i++) {
                // Generamos una letra aleatoria
                Random random = new Random(System.currentTimeMillis());
                int letter = 65 + random.nextInt(26);

                while(!set.add((char) letter)) {
                    letter = 65 + random.nextInt(26);
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public boolean isTuti() {
        Iterator diccionario = treeSet.iterator();
        Iterator setIt;
        int count;

        // Diccionari {Kas}
        // SET {K, A, T, G, Y, J, L}
        while (diccionario.hasNext()) {
            String word = (String) diccionario.next();

            count = 0;
            setIt = set.iterator();
            for (int i = 0; i < 7; i++) {
                String letter = setIt.next().toString();

                System.out.println("Caracter set: " + letter);

                if (word.contains("" + letter)) {
                    count++;
                }

            }

            if (count == idButtons.length) {
                return true;
            }
        }

        return false;
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

        // Quitamos el error
        word.setBackgroundColor(0);

        if (str.length() > 0) {
            word.setText(str.substring(0, str.length() - 1));
        }
    }

    /**
     * Método que introduce una palabra en el TextView
     *
     * @param view
     */
    public void introdueix(View view) throws IOException {
        TextView word = (TextView) findViewById ( R.id.word );
        TextView foundWords = (TextView) findViewById ( R.id.palabras );
        // Obtenemos la palabra central
        String str = (String) word.getText();
        Integer times = 0;

        if(isCorrect(str)){
            // Obtenemos las apariciones anteriores
            times = bst.get(str);
            System.out.println("{Palabara: " + str + ", Apariciones: " + times);

            // Si no hay apariciones de esa palabra
            if(times == null){
                bst.put(str, 1);
                numWords++;
            // Si ya había aparecido
            }else{
                bst.put(str, times + 1);
            }

            // Visualización de la lista de palabras
            foundWords.setText(Lista());

            // Borramos el TextView superior para introducir la siguiente palabra
            word.setText("");

        } else {
            word.setBackground(getResources().getDrawable(com.google.android.material.R.color.error_color_material_light));

            // Generamos el aviso de error
            Context context = getApplicationContext();
            CharSequence text = "";
            int duracion = Toast.LENGTH_LONG;

            if (str.length() < 3) {
                text = "La palabra introducida es demasiado corta. ¡Prueba otra vez!";
            } else {
                text = "La palabra introducida no es correcta. ¡Prueba otra vez!";
            }

            Toast toast = Toast.makeText(context, text, duracion);
            toast.show();
        }
    }

    /**
     *
     * @param word
     * @return
     */
    private boolean isCorrect(String word) throws IOException {
        return ((word.length() >= 3) && (word.contains((String) buttons[6].getText())) && (treeSet.contains(word)));
    }

    public void putExtra() {
        Intent intent = new Intent(this, MainActivity2.class);
        String message = "Hello World";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    private String Lista(){
        String str = "Has introducido "+ numWords +" palabras:";
        Iterator iterator = bst.IteratorBSTMappging();
        BSTMapping.Pair pair;

        if(iterator.hasNext()){
            pair = (BSTMapping.Pair)iterator.next();
            str += pair.getKey()+" ("+pair.getValue()+")";
        }

        while(iterator.hasNext()){
            pair = (BSTMapping.Pair)iterator.next();
            str += ", "+pair.getKey()+" ("+pair.getValue()+")";
        }

        return str;
    }
}