package io.sim;
import java.security.InvalidKeyException;

public class Cryptography {

    private char[] key;  // A chave usada para inicializar o algoritmo RC4.
    private int[] sbox;  // O array S-box usado para embaralhar os bytes.
    private static final int SBOX_LENGTH = 256;  // O comprimento do array S-box.
    private static final int TAM_MIN_CHAVE = 5;  // O tamanho mínimo da chave.

    public Cryptography(String key) throws InvalidKeyException {
        // Inicializa o objeto Cryptography com a chave especificada.
        setKey(key);
    }

    public Cryptography() {
        // Construtor vazio.
    }

    public char[] decriptografa(final String msg) {
        // A decriptografia RC4 é idêntica à criptografia, pois o RC4 é uma cifra de fluxo.
        return encrypt(msg);
    }

    public char[] encrypt(final String data) {
        // Inicializa o array S-box com a chave.
        sbox = initSBox(key);
        char[] code = new char[data.length()]; // Fix: Change String to char[]
        int i = 0;
        int j = 0;
        for (int n = 0; n < data.length(); n++) {
            // Gera números aleatórios a partir do S-box.
            i = (i + 1) % SBOX_LENGTH;
            j = (j + sbox[i]) % SBOX_LENGTH;
            swap(i, j, sbox);
            int rand = sbox[(sbox[i] + sbox[j]) % SBOX_LENGTH];
            // Realiza a operação XOR para criptografar/decryptar o caractere da mensagem.
            code[n] = (char) (rand ^ (int) data.charAt(n));
        }
        return code;
    }

    private int[] initSBox(char[] key) {
        int[] sbox = new int[SBOX_LENGTH];
        int j = 0;
        // Inicializa o array S-box com valores sequenciais.
        for (int i = 0; i < SBOX_LENGTH; i++) {
            sbox[i] = i;
        }
        // Embaralha o S-box com base na chave.
        for (int i = 0; i < SBOX_LENGTH; i++) {
            j = (j + sbox[i] + key[i % key.length]) % SBOX_LENGTH;
            swap(i, j, sbox);
        }
        return sbox;
    }

    private void swap(int i, int j, int[] sbox) {
        // Função para trocar dois elementos do array S-box.
        int temp = sbox[i];
        sbox[i] = sbox[j];
        sbox[j] = temp;
    }

    public void setKey(String key) throws InvalidKeyException {
        // Define a chave e verifica se seu tamanho está dentro dos limites especificados.
        if (!(key.length() >= TAM_MIN_CHAVE && key.length() < SBOX_LENGTH)) {
            throw new InvalidKeyException("Tamanho da chave deve ser entre "
                    + TAM_MIN_CHAVE + " e " + (SBOX_LENGTH - 1));
        }
        this.key = key.toCharArray();
    }
}
