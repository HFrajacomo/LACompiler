package LACompiler;

import java.io.StringWriter;
import java.util.BitSet;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.omg.CORBA.portable.InputStream;

public class ErrorListener implements ANTLRErrorListener {

    SaidaParser saída;

    public ErrorListener(SaidaParser sp) {
        this.saída = sp;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> rcgnzr, Object o, int i, int i1, String string, RecognitionException re) {
       CommonToken tk = (CommonToken) o;
       String valorTk = tk.getText();
       char ultTk = valorTk.charAt(valorTk.length()-1);
       
       char c = 34; // '"'
       System.out.print(tk);
       

        if (!saída.isModificado()) {
            //Erro de EOF
            if (valorTk.equals("<EOF>"))
                saída.println("Linha " + i + ": erro sintatico proximo a EOF");
            else if (valorTk.startsWith("{")) //Erro de comentario não fechado
                saída.println("Linha " + (i+1) + ": comentario nao fechado");
            else if ((valorTk.startsWith(String.valueOf(c)) && (ultTk != c))) //Erro de aspas
                saída.println("Linha " + i + ": " +valorTk.substring(0,1) + " - simbolo nao identificado");
            else if (valorTk.indexOf('@') >= 0 || valorTk.indexOf('!') >= 0 || valorTk.indexOf('|') >= 0 ) //Erro de simbolos não definidos
                saída.println("Linha " + i + ": " +valorTk + " - simbolo nao identificado");
            else //Outros erros
                saída.println("Linha " + i + ": erro sintatico proximo a " + valorTk);
            saída.println("Fim da compilacao");
        }
    }

    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean bln, BitSet bitset, ATNConfigSet atncs) {}

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitset, ATNConfigSet atncs) {}

    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atncs) {}
}