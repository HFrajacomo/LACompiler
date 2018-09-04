
package LACompiler;

import java.io.IOException;
import java.io.PrintWriter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.RecognitionException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

/**
 *
 * @author henri
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, RecognitionException  {
        
        SaidaParser sp = new SaidaParser();
   
        try{
            
            // Cria o arquivo de saída
            File out = new File(args[1]);
            FileWriter writeout = new FileWriter(out, true);
            BufferedWriter buffer = new BufferedWriter(writeout);
            out.createNewFile();
            
            // Instancia as partes do compilador e lê o arquivo de entrada
            CharStream input = CharStreams.fromFileName(args[0]);
            LALexer lexer = new LALexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LAParser parser = new LAParser(tokens);

            // Reseta o Analisador Sintático
            parser.removeErrorListeners();
            parser.addErrorListener(new ErrorListener(sp)); //Analisador Sintatico

            // Roda a compilação
            LAParser.ProgramaContext context = parser.programa();
            
            // Escreve na saída
            //System.out.print(sp.toString());
            buffer.write(sp.toString());
            buffer.close();
            writeout.close();
               
        } catch (ParseCancellationException pce) {
            if (pce.getMessage() != null) {
                sp.println(pce.getMessage());
                sp.println("Fim da compilacao");
                System.out.print("Caiu na exceção");
                            
                File out = new File(args[1]);
                FileWriter writeout = new FileWriter(out, true);
                BufferedWriter buffer = new BufferedWriter(writeout);
                out.createNewFile();
                
                buffer.write(sp.toString());
                buffer.close();
                writeout.close();
                
            }
        }
        
        }       
// TODO code application logic here
    }
    
