package tal;

import java.io.*;
import static tal.Token.Type.*;

/**
 * Analizador léxico implementado mediante un
 * autómata finito determinista.
 * <p>Cada estado del autómata se implementa con un objeto Runnable.
 */
public class AFD extends ALex
{
/**
 * Construye el autómata.
 * @param fichero Fichero de texto que se debe analizar.
 * @throws IOException
 */
public AFD(String fichero) throws IOException
{
    super(fichero);
    setStart(this::inicio);
}

private void inicio()
{
    if(isChar('i'))
        state(this::if_); //Ponemos guión bajo xq la palabra if ya esta reservada
    else if(isChar('/'))
        state(this::com1);
    else if(isChar('s'))
        state(this::string);
    else if(isChar('t'))
        state(this::then);
    else if(isChar('e'))
        state(this::else1);
    else if(isChar('w'))
        state(this::while1);
    else if(isChar('d'))
        state(this::do1);
    else if(isIdCharStart())
        state(this::id);
    else if(isDigitChar()) //Devulve true si es un digito(numeros)
        state(this::intval);
    else if(isChar('('))
        state(this::ipar);
    else if(isChar(')'))
        state(this::dpar);
    else if(isChar('!'))
        state(this::neg);
    else if(isChar('='))
        state(this::assign);
    else if(isChar('+') || isChar('-'))
        state(this::sum);
    else if(isChar('*'))
        state(this::mul);
    else if(isChar('|'))
        state(this::or);
    else if(isChar('&'))
        state(this::and);
    else if(isChar('<') || isChar('>'))
        state(this::rel1);
    else if(isChar('"'))
        stateNoChar(this::strval);
    else if(isSpaceChar())
        restart();
    else if(isEofChar())
        token(EOF);
    else
        error();
}

private void com1(){
    if(isChar('/'))
        state(this::com2);
    else if(isChar('*'))
        state(this::comBloque);
    else
        token(MUL);
}

private void comBloque(){
    if(isIdChar())
        state(this::comBloque);
    else if(isChar('*'))
        state(this::comBloque1);
}

private void comBloque1(){
    if(isIdChar() || isSpaceChar())
        state(this::comBloque);
    else if(isChar('/'))
        restart();
}

private void com2(){
    if(isIdChar())
        state(this::com2);
    else if(isChar('\n'))
        restart();
}

private void strval(){
    if(isChar('"'))
        stateNoChar(this::strval1);
    else if(isChar('\n') || isEofChar())
        error();
    else
        state(this::strval);
}

private void strval1(){
    token(STRVAL);
}

private void do1(){
    if(isChar('o'))
        state(this::do2);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void do2(){
    if(isIdChar())
        state(this::id);
    else
        token(DO);
}

private void while1(){
    if(isChar('h'))
        state(this::while2);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void while2(){
    if(isChar('i'))
        state(this::while3);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void while3(){
    if(isChar('l'))
        state(this::while4);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void while4(){
    if(isChar('e'))
        state(this::while5);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void while5(){
    if(isIdChar())
        state(this::id);
    else
        token(WHILE);
}

private void end(){
    if(isChar('d'))
        state(this::end1);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void end1(){
    if(isIdChar())
        state(this::id);
    else
        token(END);
}

private void else1(){
    if(isChar('l'))
        state(this::else2);
    else if(isChar('n'))
        state(this::end);
    else if(isChar('c'))
        state(this::echo);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void echo(){
    if(isChar('h'))
        state(this::echo1);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void echo1(){
    if(isChar('o'))
        state(this::echo2);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void echo2(){
    if(isIdChar())
        state(this::id);
    else
        token(ECHO);
}

private void else2(){
    if(isChar('s'))
        state(this::else3);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void else3(){
    if(isChar('e'))
        state(this::else4);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void else4(){
    if(isIdChar())
        state(this::id);
    else
        token(ELSE);
}

private void then(){
    if(isChar('h'))
        state(this::then1);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void then1(){
    if(isChar('e'))
        state(this::then2);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void then2(){
    if(isChar('n'))
        state(this::then3);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void then3(){
    if(isIdChar())
        state(this::id);
    else
        token(THEN);
}

private void string(){
    if(isChar('t'))
        state(this::string1);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void string1(){
    if(isChar('r'))
        state(this::string2);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void string2(){
    if(isChar('i'))
        state(this::string3);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void string3(){
    if(isChar('n'))
        state(this::string4);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void string4(){
    if(isChar('g'))
        state(this::string5);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void string5(){
    if(isIdChar())
        state(this::id);
    else
        token(STRING);
}

private void rel1(){
    if(isChar('='))
        state(this::rel);
    else
        token(REL);
}

private void and(){
    if(isChar('&'))
        state(this::and1);
    else
        error();
}     

private void and1(){
    token(AND);
}

private void or(){
    if(isChar('|'))
        state(this::or1);
    else
        error();
}     

private void or1(){
    token(OR);
}

private void if_(){
    if(isChar('f'))
        state(this::if1);
    else if(isChar('n'))
        state(this::integer1);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void integer1(){
    if(isChar('t'))
        state(this::integer2);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void integer2(){
    if(isChar('e'))
        state(this::integer3);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void integer3(){
    if(isChar('g'))
        state(this::integer4);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void integer4(){
    if(isChar('e'))
        state(this::integer5);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void integer5(){
    if(isChar('r'))
        state(this::integer6);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void integer6(){
    if(isIdChar())
        state(this::id);
    else
        token(INTEGER);
}

private void if1(){
    if(isIdChar())
        state(this::id);
    else
        token(IF);
}

private void id(){
    if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void intval()
{
    if(isDigitChar())
        state(this::intval);
    else if(isIdChar())
        error();
    else
        token(INTVAL);
}

private void ipar(){
    token(IPAR);
}

private void dpar(){
    token(DPAR);
}

private void neg(){
    if(isChar('='))
        state(this::rel);
    else
        token(NEG);
}

private void assign(){
    if(isChar('='))
        state(this::rel);
    else
        token(ASSIGN);
}

private void rel(){
    token(REL);
}

private void sum(){
    token(SUM);
}

private void mul(){
    token(MUL);
}

} // AFD
