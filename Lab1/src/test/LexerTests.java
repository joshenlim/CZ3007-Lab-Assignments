package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import frontend.Token;
import frontend.Token.Type;
import static frontend.Token.Type.*;

public class LexerTests {
	private final void runtest(String input, Token... output) {
		Lexer lexer = new Lexer(new StringReader(input));
		int i = 0;
		Token actual = new Token(MODULE, 0, 0, ""), expected;
		
		try {
			do {
				assertTrue(i < output.length);
				expected = output[i++];
				try {
					actual = lexer.nextToken();
					assertEquals(expected, actual);
				} catch(Error e) {
					if(expected != null)
						fail(e.getMessage());
					/* return; */
				}
			} while(!actual.isEOF());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDemo() {
		runtest("while (var_x == 0)\n{ var_x++ };",
				new Token(WHILE, 0, 0, "while"),
				new Token(LPAREN, 0, 6, "("),
				new Token(ID, 0, 7, "var_x"),
				new Token(EQEQ, 0, 13, "=="),
				new Token(INT_LITERAL, 0, 16, "0"),
				new Token(RPAREN, 0, 17, ")"),
				new Token(LCURLY, 1, 0, "{"),
				new Token(ID, 1, 2, "var_x"),
				new Token(PLUS, 1, 7, "+"),
				new Token(PLUS, 1, 8, "+"),
				new Token(RCURLY, 1, 10, "}"),
				new Token(SEMICOLON, 1, 11, ";"),
				new Token(EOF, 1, 12, ""));
	}

	@Test
	public void testKeywords() {
		runtest("module false return while boolean break else if import int public true type void",
				new Token(MODULE, 0, 0, "module"),
				new Token(FALSE, 0, 7, "false"),
				new Token(RETURN, 0, 13, "return"),
				new Token(WHILE, 0, 20, "while"),
				new Token(BOOLEAN, 0, 26, "boolean"),
				new Token(BREAK, 0, 34, "break"),
				new Token(ELSE, 0, 40, "else"),
				new Token(IF, 0, 45, "if"),
				new Token(IMPORT, 0, 48, "import"),
				new Token(INT, 0, 55, "int"),
				new Token(PUBLIC, 0, 59, "public"),
				new Token(TRUE, 0, 66, "true"),
				new Token(TYPE, 0, 71, "type"),
				new Token(VOID, 0, 76, "void"),
				new Token(EOF, 0, 80, ""));
	}
	
	@Test
	public void testPunctuations() {
		runtest("() [] {}",
				new Token(LPAREN, 0, 0, "("),
				new Token(RPAREN, 0, 1, ")"),
				new Token(LBRACKET, 0, 3, "["),
				new Token(RBRACKET, 0, 4, "]"),
				new Token(LCURLY, 0, 6, "{"),
				new Token(RCURLY, 0, 7, "}"),
				new Token(EOF, 0, 8, ""));
	}
	
	@Test
	public void testOperators() {
		runtest("/ ==  = >= > <= < - != + *",
				new Token(DIV, 0, 0, "/"),
				new Token(EQEQ, 0, 2, "=="),
				new Token(EQL, 0, 6, "="),
				new Token(GEQ, 0, 8, ">="),
				new Token(GT, 0, 11, ">"),
				new Token(LEQ, 0, 13, "<="),
				new Token(LT, 0, 16, "<"),
				new Token(MINUS, 0, 18, "-"),
				new Token(NEQ, 0, 20, "!="),
				new Token(PLUS, 0, 23, "+"),
				new Token(TIMES, 0, 25, "*"),
				new Token(EOF, 0, 26, ""));
	}

	@Test
	public void testStringLiteralWithDoubleQuote() {
		runtest("\"\"\"",
				new Token(STRING_LITERAL, 0, 0, ""),
				(Token)null,
				new Token(EOF, 0, 3, ""));
	}

	@Test
	public void testStringLiteral1() {
		runtest("\"\\n\"", 
				new Token(STRING_LITERAL, 0, 0, "\\n"),
				new Token(EOF, 0, 4, ""));
	}
	
	@Test
	public void testStringLiteral2() {
		runtest("\"abc\"", 
				new Token(STRING_LITERAL, 0, 0, "abc"),
				new Token(EOF, 0, 5, ""));
	}
	
	@Test
	public void testStringLiteral3() {
		runtest("\"\\\"", 
				new Token(STRING_LITERAL, 0, 0, "\\"),
				new Token(EOF, 0, 3, ""));
	}
	
	@Test
	public void testIntegerLiteral1() {
		runtest("123",
				new Token(INT_LITERAL, 0, 0, "123"),
				new Token(EOF, 0, 3, ""));
	}
	
	@Test
	public void testIntegerLiteral2() {
		runtest("00456",
				new Token(INT_LITERAL, 0, 0, "00456"),
				new Token(EOF, 0, 5, ""));
	}
	
	@Test
	public void testIdentifier1() {
		runtest("hello_123_world", 
				new Token(ID, 0, 0, "hello_123_world"),
				new Token(EOF, 0, 15, ""));
	}
	
	@Test
	public void testIdentifier2() {
		runtest("a1b2c3_d4e5f6", 
				new Token(ID, 0, 0, "a1b2c3_d4e5f6"),
				new Token(EOF, 0, 13, ""));
	}
	
	@Test
	public void testIdentifier3() {
		runtest("a____c", 
				new Token(ID, 0, 0, "a____c"),
				new Token(EOF, 0, 6, ""));
	}


}
