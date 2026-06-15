import java.util.List;
import semantic.SemanticAnalyzer;
import generator.CGenerator;

import ast.ASTNode;
import ast.ProgramNode;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import java.nio.file.Files;
import java.nio.file.Path;
import generator.ErrorWriter;

public class Main {

    public static void main(String[] args) {

    	String source;

    	try {

    	    source = Files.readString(
    	            Path.of(
    	                    "examples/program.pas"
    	            )
    	    );

    	} catch (Exception e) {

    	    System.out.println(
    	            "Could not read source file."
    	    );

    	    return;
    	}

    	try {

    	    Lexer lexer =
    	            new Lexer(source);

    	    List<Token> tokens =
    	            lexer.tokenize();

    	    Parser parser =
    	            new Parser(tokens);

    	    ProgramNode program =
    	            parser.parse();

    	    SemanticAnalyzer analyzer =
    	            new SemanticAnalyzer();

    	    analyzer.analyze(program);

    	    CGenerator generator =
    	            new CGenerator();

    	    generator.generate(
    	            program,
    	            "output/output.c"
    	    );
    	    Files.deleteIfExists(
    	            Path.of(
    	                    "output/error.invalid"
    	            )
    	    );

    	    System.out.println(
    	            "Compilation successful."
    	    );

    	}
    	catch (Exception e) {

    	    ErrorWriter.writeError(
    	            e.getMessage()
    	    );

    	    System.out.println(
    	            "Compilation failed."
    	    );

    	    System.out.println(
    	            e.getMessage()
    	    );
        }
    }
}