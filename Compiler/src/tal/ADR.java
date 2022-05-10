package tal;

import static tal.Token.Type.*;

/**
 * Analizador sintáctico implementado mediante el método descendente recursivo.
 * <p>
 * En esta clase se debe implementar la gramática del lenguaje.
 */
public class ADR extends ASin {

    /**
     * Construye un analizador descendente recursivo.
     *
     * @param lex Analizador léxico.
     */
    public ADR(ALex lex) {
        super(lex);
    }

    /**
     * Símbolo inicial de la gramática.
     */
    public void programa() {
        declaracion();
        bloque();
        tokenRead(EOF);
    }

    private void declaracion() {
        switch (tokenType()) {
            case INTEGER:
                tokenRead(INTEGER);
                tokenRead(ID);
                codeVariableInteger();
                declaracion();
                break;

            case STRING:
                tokenRead(STRING);
                tokenRead(ID);
                codeVariableString();
                declaracion();
                break;
        }
    }

    private void bloque() {
        switch (tokenType()) {
            case ID:
                asignacion();
                bloque();
                break;
                
            case ECHO:
                impresion();
                bloque();
                break;
                
            case IF:
                condicion();
                bloque();
                break;
                
            case WHILE:
                iteracion();
                bloque();
                break;
        }
    }
    
    private void iteracion(){
        tokenRead(WHILE);
        codeWhile();
        expresion();
        codeIf();
        tokenRead(DO);
        bloque();
        tokenRead(END);
        codeEnd();
        
    }
    
    private void sino(){
        if(tokenType()==ELSE){
            tokenRead(ELSE);
            codeElse();
            bloque();
        }
    }
    
    private void condicion(){
        tokenRead(IF);
        expresion();
        codeIf();
        tokenRead(THEN);
        bloque();
        sino();
        tokenRead(END);
        codeEnd();
    }
    
    private void impresion(){
        tokenRead(ECHO);
        expresion();
        codePrint();
    }

    private void asignacion() {
        tokenRead(ID);
        codeVariableAssignment();
        tokenRead(ASSIGN);
        expresion();
        codeAssignment();
    }

    private void expresion() {
        vor();
        vor1();
    }

    private void vor() {
        vand();
        vand1();
    }

    private void vor1() {
        if (tokenType() == OR) {
            String op = tokenName();
            tokenRead(OR);
            vor();
            codeOperator(op);
            vor1();
        }
    }

    private void vand() {
        if (tokenType() == NEG) {
            String op = tokenName();
            tokenRead(NEG);
            vrel();
            codeOperator(op);
            vrel1();
        } else {
            vrel();
            vrel1();
        }
    }

    private void vand1() {
        if (tokenType() == AND) {
            String op = tokenName();
            tokenRead(AND);
            vand();
            codeOperator(op);
            vand1();
        }
    }

    private void vrel1() {
        if (tokenType() == REL) {
            String op = tokenName();
            tokenRead(REL);
            vrel();
            codeOperator(op);
        }
    }

    private void vrel() {
        if (tokenType() == SUM) {
            String op = tokenName();
            tokenRead(SUM);
            vsum();
            if(op.equals("-")){
                codeOperator("-1");
            }
            vsum1();
        } else {
            vsum();
            vsum1();
        }
    }

    private void vsum() {
        vmul();
        vmul1();
    }

    private void vsum1() {
        if (tokenType() == SUM) {
            String op = tokenName();
            tokenRead(SUM);
            vsum();
            codeOperator(op);
            vsum1();
        }
    }

    private void vmul1() {
        if (tokenType() == MUL) {
            String op = tokenName();
            tokenRead(MUL);
            vmul();
            codeOperator(op);
            vmul1();
        }
    }

    private void vmul() {
        if (tokenType() == IPAR) {
            tokenRead(IPAR);
            expresion();
            tokenRead(DPAR);
        } else {
            valor();
        }
    }

    private void valor() {
        switch (tokenType()) {
            case ID:
                tokenRead(ID);
                codeVariableExpression();
                break;
            case INTVAL:
                tokenRead(INTVAL);
                codeInteger();
                break;
            default:
                tokenRead(STRVAL);
                codeString();
                break;
        }
    }

} // ADR
