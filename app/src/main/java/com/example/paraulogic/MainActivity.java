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
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    private final int[] idButtons = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7};
    private final int numButtons = idButtons.length;
    private Button[] buttons;
    private UnsortedArraySet<Character> set;
    private BSTMapping<String, Integer> bst;
    private int numWords;
    private TreeSet treeSet;

    public static final String EXTRA_MESSAGE = "com.example.paraulogic.MESSAGE";

    /**
     * Constructor de la classe
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
     * Inicialització dels components
     *
     */
    private void initComponents() throws Exception {
        this.set = new UnsortedArraySet<Character>(numButtons);
        this.buttons = new Button[numButtons];
        this.bst = new BSTMapping<String, Integer>();
        this.numWords = 0;
        this.treeSet = new TreeSet<String>();

        generateDictionariTree();
        generateArraySet();
    }

    /**
     * Mètode que genera un arbre vermell i negre del contingut del fitxer diccionari
     *
     * @throws Exception
     */
    private void generateDictionariTree() throws Exception {
        InputStream is = getResources().openRawResource(R.raw.catala_filtrat);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Llegim el diccionari i emmagatzemam les paraules de igual o més de tres lletres
        String word = br.readLine();
        while (word != null) {
            if (word.length() >= 3) {
                treeSet.add(word);
            }

            word = br.readLine();
        }
    }

    /**
     * Inicialització dels botons
     *
     */
    private void initButtons() {
        Iterator iterator = set.iterator();

        // Assignam tots els paràmetres als botons
        for (int i = 0; i < numButtons; i++) {
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
        // Adquirim les variables de l'interfaç que volem modificar
        Button button = findViewById(view.getId());
        TextView word = findViewById(R.id.word);

        // Llevam l'error
        word.setBackgroundColor(0);

        // Actualitzam el contingut
        String txtOriginal = (String) word.getText();
        word.setText(txtOriginal + button.getText());
    }

    /**
     * Mètode que genera el conjunt no ordenat de lletres fins a que trobi un conjunt que sigui tuti
     *
     */
    private void generateArraySet() {
        generateRandomArraySet();

        while(!tuti()) {
            generateRandomArraySet();
        }
    }

    /**
     * Mètode que genera un conjunt no ordenat de lletres amb mínim una vocal
     *
     */
    private void generateRandomArraySet() {
        set = new UnsortedArraySet<Character>(numButtons);

        generateVowel();
        for (int i = 0; i < numButtons - 1; i++) {
            generateLetter();
        }

        initButtons();
    }

    /**
     * Mètode que verifica si hi ha alguna paraula que compleix la condició de tuti adins del
     * conjunt no ordenat de lletres
     *
     * @return
     */
    private boolean tuti() {
        Iterator it = treeSet.iterator();
        String word;

        while(it.hasNext()){
            word = it.next().toString();

            if(isSolution(word)){
                if(isTuti(word)){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Mètode que verifica si una paraula pasada per paràmetre és tuti o no
     *
     * @param word
     * @return
     */
    private boolean isTuti(String word) {
        Iterator it = set.iterator();
        String str;

        while(it.hasNext()){
            str = it.next().toString();

            if(!word.contains(str.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Mètode que verifica si la paraula pasada per paràmetre, té solució o no
     *
     * @param word
     * @return
     */
    private boolean isSolution(String word) {
        String letter = (String) buttons[6].getText();
        boolean possible = word.contains(letter.toLowerCase());

        if (possible) {
            // Observam si la paraula només conté les lletres del nostre conjunt
            for (int i = 0; i < word.length(); i++) {
                if (!set.contains(Character.toUpperCase(word.charAt(i)))) {
                    return false;
                }
            }
        }else{
            return false;
        }

        return true;
    }

    /**
     * Mètode que genera una lletra aleatòria de l'abecedari
     *
     */
    private void generateLetter(){
        Random random = new Random(System.currentTimeMillis());
        int letter = 65 + random.nextInt(26);

        // Si es repeteix, s'afegeix una diferent
        while(!set.add((char) letter)){
            letter = 65 + random.nextInt(26);
        }
    }

    /**
     * Mètode que genera una vocal de forma aleatòria
     *
     */
    private void generateVowel(){
        Random random = new Random(System.currentTimeMillis());
        char[] vowels = { 'A', 'E', 'I', 'O', 'U' };

        set.add(vowels[random.nextInt(vowels.length - 1)]);
    }

    /**
     * Mètode que realitza el barrejat de l'array de lletres contingudes als botons blaus
     *
     * @param view
     */
    public void shuffle(View view) {
        char[] letters = unsortedArraySetToCharArray();
        randomize(letters, numButtons - 1);

        for (int i = 0; i < numButtons - 1; i++) {
            buttons[i].setText(Character.toString(letters[i]));
        }
    }

    /**
     * Mètode que retorna un conjunt de lletres dels elements de la llista ja creada
     *
     * @return
     */
    public char[] unsortedArraySetToCharArray() {
        char[] letters = new char[numButtons - 1];
        Iterator iterator = set.iterator();

        for (int i = 0; iterator.hasNext() && i < numButtons - 1 ; i++) {
            letters[i] = (char) iterator.next();
        }

        return letters;
    }

    /**
     * Mètode que barreja l'array pasat un valor per paràmetre
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
     * Mètode que elimina la paraula, introduïda per l'usuari, del component TextView
     *
     * @param view
     */
    public void suprimeix(View view) {
        TextView word = findViewById(R.id.word);
        String str = (String) word.getText();

        // Llevam l'error
        word.setBackgroundColor(0);

        if (str.length() > 0) {
            word.setText(str.substring(0, str.length() - 1));
        }
    }

    /**
     * Mètode que introdueix la paraula creada per l'usuari al component TextView
     *
     * @param view
     */
    public void introdueix(View view) throws IOException {
        TextView word = findViewById ( R.id.word );
        TextView foundWords = findViewById ( R.id.palabras );
        // Obtenim la lletra central
        String str = (String) word.getText();
        Integer times = 0;

        if(isCorrect(str)){
            // Obtenim les aparicions anteriors
            times = bst.get(str);
            System.out.println("{Palabara: " + str + ", Apariciones: " + times);

            // Si no hi ha aparicions de la paraula
            if(times == null){
                bst.put(str, 1);
                numWords++;
            // Si ja havia aparegut
            }else{
                bst.put(str, times + 1);
            }

            // Visualització de la llista de paraules
            foundWords.setText(list());

            // Esborram el TextView superior per a introduïr la següent paraula
            word.setText("");

        } else {
            word.setBackground(getResources().getDrawable(com.google.android.material.R.color.error_color_material_light));

            // Generam l'avís d'error
            Context context = getApplicationContext();
            CharSequence text = "";
            int duration = Toast.LENGTH_LONG;

            if (str.length() < 3) {
                text = "La palabra introducida es demasiado corta. ¡Prueba otra vez!";
            } else {
                text = "La palabra introducida no es correcta. ¡Prueba otra vez!";
            }

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * Mètode que verifica si una paraula és correcte o no
     *
     * @param word
     * @return
     */
    private boolean isCorrect(String word) {
        String letter = (String) buttons[6].getText();
        return (word.length() >= 3) && (word.contains(letter) && treeSet.contains(word.toLowerCase()));
    }

    /**
     * Mètode per a transferir l'informació a la finestra extra
     *
     * @param view
     */
    public void visualizeWords(View view) {
        Intent intent = new Intent(this, MainActivity2.class) ;
        String message = getValidWords();
        intent.putExtra(EXTRA_MESSAGE, message) ;
        startActivity(intent) ;
    }

    /**
     * Mètode que retorna el conjunt de paraules vàlides de la partida
     *
     * @return
     */
    private String getValidWords() {
        Iterator it = treeSet.iterator();
        String str = "";
        String word;

        while(it.hasNext()){
            word = it.next().toString();

            if(isSolution(word)){
                if(isTuti(word)){
                    str += "<font color = 'red'>" + word + " </font>, ";
                    System.out.println("Solució i Tuti: " + word);
                }else{
                    str += word + ", ";
                }
            }
        }

        return str;
    }

    /**
     * Mètode que genera la llista de paraules correctes introduides per l'usuari
     *
     * @return
     */
    private String list() {
        String str = "Has introduït " + numWords + " paraules: ";
        Iterator iterator = bst.IteratorBSTMappging();
        BSTMapping.Pair pair;

        if (iterator.hasNext()) {
            pair = (BSTMapping.Pair)iterator.next();
            str += pair.getKey()+" (" + pair.getValue() + ")";
        }

        while (iterator.hasNext()) {
            pair = (BSTMapping.Pair)iterator.next();
            str += ", " + pair.getKey() + " (" + pair.getValue() + ")";
        }

        return str;
    }
}