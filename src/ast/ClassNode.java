package ast;

import util.DispatchTable;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Stefano on 06/06/2017.
 */
public class ClassNode implements Node {

    private String id;
    private String superclass;
    private ArrayList<Node> fields;
    private ArrayList<Node> methods;

    public ClassNode(String id, String superclass, ArrayList<Node> fields, ArrayList<Node> methods) {
        this.id = id;
        this.superclass = superclass;
        this.fields = fields;
        this.methods = methods;
    }

    public String getId() { return id; }

    public ArrayList<Node> getFields(){
        return fields;
    }

    public ArrayList<Node> getMethods(){
        return methods;
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        env.incNestingLevel();
        //TODO: dà errore quando cerca il nesting level 1 perché non inseriamo la STentry per la dich di classe.
        env.decOffset();
        STentry classEntry = new STentry(env.getNestingLevel(), env.getOffset());
        HashMap<String,STentry> hm = new HashMap<>();
        env.getSymTable().add(hm);
        // TODONE, da controllare
        System.out.println("Il nesting level in questa classe è: " + env.getNestingLevel());

        ArrayList<SemanticError> res = new ArrayList<>();
        //controllare ID
        if (id.equals(superclass)) {
            res.add(new SemanticError(id + " cannot extend itself."));
        }
        else {
            //checksemantics field e methods classe attuale
            for (Node field : fields){
                //env.symTable.get(env.nestingLevel).put(, clsEntry)
                res.addAll(field.checkSemantics(env));
            }

            STentry mtdEntry;
            String mtdName;

            // processing di tutti i nomi dei metodi ==> uso prima delle dichiarazioni è possibbile
            for (Node method : methods) {
                env.decOffset();
                mtdEntry = new STentry(env.getNestingLevel(), env.getOffset());
                mtdName = ((FunNode) method).getId();
                if (env.getSymTable().get(env.getNestingLevel()).put(mtdName, mtdEntry) != null)
                    res.add(new SemanticError("Method " + mtdName + " already declared."));
            }

            for (Node method : methods)
                res.addAll(method.checkSemantics(env));

            /* // all class names are at nesting level 0
            if ((env.symTable.get(0).put(id, entry)) != null) {
                res.add(new SemanticError("Class " + id + " has been already declared"));
            } */

            DispatchTable classDT = new DispatchTable();
            //controllare ID superclasse
            if (!superclass.equals("")) {
                STentry superClassEntry = (env.getSymTable().get(0)).get(superclass);
                if (superClassEntry == null) {
                    res.add(new SemanticError("Extended class " + superclass + " has not been declared"));
                } else {
                    // crea dispatch table usando anche la tabella della superclasse
                    DispatchTable superclassDT = env.getDispatchTables().get(superclass);
                    classDT.buildDispatchTable(methods, superclassDT);
                }
            } else {
                classDT.buildDispatchTable(methods);
            }
        }

        env.decNestingLevel();
        env.getSymTable().remove(env.getNestingLevel());
        return res;
    }
}