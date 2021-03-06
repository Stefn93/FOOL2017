package parser;

import util.VMResult;

import java.util.Arrays;

public class ExecuteVM {

    public static final int CODESIZE = 10000;
    public static final int MEMSIZE = 10000;

    public static final int PRINT_STACK = -1;
    public static final int PRINT_HEAP = -2;

    private int[] code;
    private int[] memory = new int[MEMSIZE];

    private int ip = 0;
    private int sp = MEMSIZE;

    private int hp = 0;
    private int op = 0;
    private int mo = 0;
    private int ro = 0;
    private int fp = MEMSIZE;
    private int ra;
    private int rv;

    public ExecuteVM(int[] code) {
        this.code = code;
    }

    public void cpu() {
        try {
            while (true) {
                int bytecode = code[ip++]; // fetch
                int v1, v2;
                int address;
                switch (bytecode) {
                    case SVMParser.PUSH:
                        push(code[ip++]);
                        break;
                    case SVMParser.POP:
                        pop();
                        break;
                    case SVMParser.ADD:
                        v1 = pop();
                        v2 = pop();
                        push(v2 + v1);
                        break;
                    case SVMParser.MULT:
                        v1 = pop();
                        v2 = pop();
                        push(v2 * v1);
                        break;
                    case SVMParser.DIV:
                        v1 = pop();
                        v2 = pop();
                        push(v2 / v1);
                        break;
                    case SVMParser.SUB:
                        v1 = pop();
                        v2 = pop();
                        push(v2 - v1);
                        break;
                    case SVMParser.STOREW: //
                        address = pop();
                        memory[address] = pop();
                        break;
                    case SVMParser.LOADW: //Devo aver già pushato sullo stack l'address da cui caricare la variabile
                        push(memory[pop()]);
                        break;
                    case SVMParser.BRANCH:
                        address = code[ip];
                        ip = address;
                        break;
                    case SVMParser.BRANCHEQ: //
                        address = code[ip++];
                        v1 = pop();
                        v2 = pop();
                        if (v2 == v1) ip = address;
                        break;
                    case SVMParser.BRANCHLESSEQ:
                        address = code[ip++];
                        v1 = pop();
                        v2 = pop();
                        if (v2 <= v1) ip = address;
                        break;
                    case SVMParser.JS: //
                        address = pop();
                        ra = ip;
                        ip = address;
                        //System.out.println("jumping to: " + address + "(= ip) and ra is " + ra);
                        break;
                    case SVMParser.STORERA: //
                        ra = pop();
                        break;
                    case SVMParser.LOADRA: //
                        push(ra);
                        break;
                    case SVMParser.STORERV: //
                        rv = pop();
                        break;
                    case SVMParser.LOADRV: //
                        push(rv);
                        break;
                    case SVMParser.LOADFP: //
                        push(fp);
                        break;
                    case SVMParser.STOREFP: //
                        fp = pop();
                        break;
                    case SVMParser.COPYFP: //
                        fp = sp;
                        break;
                    case SVMParser.STOREHP: //
                        hp = pop();
                        break;
                    case SVMParser.LOADHP: //
                        push(hp);
                        break;
                    case SVMParser.LOADOP:
                        push(op);
                        break;
                    case SVMParser.STOREOP:
                        op = pop();
                        break;
                    case SVMParser.LOADMO:
                        push(mo);
                        break;
                    case SVMParser.STOREMO:
                        mo = pop();
                        break;
                    case SVMParser.LOADRO:
                        push(ro);
                        break;
                    case SVMParser.STORERO:
                        ro = pop();
                        break;
                    case SVMParser.PRINT:
                        System.out.println((sp < MEMSIZE) ? memory[sp] : "Empty stack!");
                        break;
                    case SVMParser.HALT:
                        return;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("AOOB at line " + e.getStackTrace()[0].getLineNumber());
            System.out.println("ip: " + ip + " sp: " + sp + " hp: " + hp + " fp: " + fp);
        }
    }

    private int pop() {
        return memory[sp++];
    }

    private void push(int v) {
        memory[--sp] = v;
    }

    public void printState() {
        System.out.printf("ip: %d\nsp: %d\nhp: %d\nfp: %d\nra: %d\nrv: %d\ncode at ip is: %d\nmemory at sp: %d\nmemory at hp: %d\n",
                ip, sp, hp, fp, ra, rv, code[ip], memory[sp], memory[hp]);
    }

    public void printMemory(int mode) {
        int start = -1, end = -1;

        switch (mode) {
            case PRINT_STACK:
                System.out.println("STACK");
                start = sp;
                end = MEMSIZE;
                break;
            case PRINT_HEAP:
                System.out.println("HEAP");
                start = 0;
                end = hp;
                break;
        }

        for (int i = start; i < end; i++)
            System.out.println(i + " -> " + memory[i]);
    }

    public VMResult getResult() {
        return (sp < MEMSIZE && sp > -1) ? new VMResult(memory[sp], VMResult.OK) : new VMResult(0, VMResult.EMPTY_STACK);
    }
}