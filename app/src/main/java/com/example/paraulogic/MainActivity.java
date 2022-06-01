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
        this.set = new UnsortedArraySet<Character>(numButtons);
        this.buttons = new Button[numButtons];
        this.bst = new BSTMapping<String, Integer>();
        this.numWords = 0;
        this.treeSet = new TreeSet<String>();

        generateDictionariTree();
        generateArraySet();
    }

    /**
     * Método que genera un arbol rojo y negro del contenido del fichero
     *
     * @throws Exception
     */
    private void generateDictionariTree() throws Exception {
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
        // Variable que nos permitira iterar de forma sencilla entre los valores del array "set"
        Iterator iterator = set.iterator();

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
     * Método para generar el conjunto no ordenado de letras hasta que encuentre un conjunto Tuti
     *
     */
    private void generateArraySet() {
        generateRandomArraySet();

        while(!tuti()) {
            generateRandomArraySet();
        }
    }

    /**
     * Método para generar un conjunto no ordenado de letras con mínimo una vocal
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
     * Método que verifica si hay alguna palabra que cumpla la condición de Tuti dentro del
     * conjunto no ordenado de letras
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
     * Método que verifica si la palabra pasada por parámetro es Tuti o no
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
     * Método para verificar si una palabra tiene solución, es decir, que tiene únicamente palabras
     * de nuestro conjunto de letras
     *
     * @param word
     * @return
     */
    private boolean isSolution(String word) {
        String letter = (String) buttons[6].getText();
        boolean possible = word.contains(letter.toLowerCase());

        if (possible) {
            // Observamos is la palabra solo contiene letras de nuestro conjunto
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
     * Método para generar una letra aleatoria del abecedario
     *
     */
    private void generateLetter(){
        Random random = new Random(System.currentTimeMillis());
        int letter = 65 + random.nextInt(26);

        // Si se repite, añade una diferente
        while(!set.add((char) letter)){
            letter = 65 + random.nextInt(26);
        }
    }

    /**
     * Método para generar una vocal de forma aleatoria
     *
     */
    private void generateVowel(){
        Random random = new Random(System.currentTimeMillis());
        char[] vowels = { 'A', 'E', 'I', 'O', 'U' };

        set.add(vowels[random.nextInt(vowels.length - 1)]);
    }

    /**
     * Método que realiza la acción de barajar el array de letras contenidas en los botones azules
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
     * Método que devuelve un char[] de los elementos de la lista ya creada
     *
     * @return
     */
    public char[] unsortedArraySetToCharArray() {
        char[] words = new char[numButtons - 1];
        Iterator iterator = set.iterator();

        for (int i = 0; iterator.hasNext() && i < numButtons - 1 ; i++) {
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
        TextView word = findViewById ( R.id.word );
        TextView foundWords = findViewById ( R.id.palabras );
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
     * Método que verifica si una palabra es correcta o no
     *
     * @param word
     * @return
     */
    private boolean isCorrect(String word) {
        String letter = (String) buttons[6].getText();
        return (word.length() >= 3) && (word.contains(letter) && treeSet.contains(word.toLowerCase()));
    }

    /**
     * Método para transferir la información a la ventana extra
     *
     * @param view
     */
    public void visualizarPalabras(View view) {
        Intent intent = new Intent(this, MainActivity2.class) ;
        String message = getPalabrasValidas();
        intent.putExtra(EXTRA_MESSAGE, message) ;
        startActivity(intent) ;
    }

    /**
     * Método que devuelve el conjunto de palabras validas de la partida
     *
     * @return
     */
    private String getPalabrasValidas() {
        Iterator it = treeSet.iterator();
        String str = "";
        String word;

        while(it.hasNext()){
            word = it.next().toString();

            if(isSolution(word)){
                if(isTuti(word)){
                    str += "<font color = 'red'>" + word + " </font>, ";
                    System.out.println("Solución y Tuti: " + word);
                }else{
                    str += word + ", ";
                }
            }
        }

        return str;
    }

    /**
     * Método que genera la lista de palabras correctas introducidas por el usuario
     *
     * @return
     */
    private String Lista() {
        String str = "Has introducido " + numWords + " palabras:";
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