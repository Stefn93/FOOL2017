package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

/**
 * Created by crist on 08/06/2017.
 */
public class CallMethodNode implements Node {
    private String objectName;
    private String methodName;
    private ArrayList<Node> args;

    // chiamata di un metodo di una classe
    public CallMethodNode(String objectName, String methodName, ArrayList<Node> args) {
        this.objectName = objectName;
        this.methodName = methodName;
        this.args = args;
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
        ArrayList<SemanticError> res = new ArrayList<>();

        int j = env.nestingLevel;
        STentry tmp = null;
        boolean foundMethod = false;
        Node classNode = null;

        while (j >= 0 && classNode == null) {
            classNode = (env.symTable.get(j--)).get(objectName).getType();

            ArrayList<Node> methods = ((ClassNode) classNode).getMethods();
            for (Node decs : methods) {
                if (((FunNode) decs).getId().equals(methodName)) {
                    foundMethod = true;
                }
            }
        }
        if (!foundMethod) {
            res.add(new SemanticError("Id " + methodName + " not declared"));
        } else {
            System.out.println(classNode.toPrint(""));
        }

        if (tmp == null)
            res.add(new SemanticError("Id " + objectName + " not declared"));

        /*
        while (j >= 0 && tmp == null)
            tmp = (env.symTable.get(j--)).get(methodName);
        if (tmp == null)
            res.add(new SemanticError("Id " + methodName + " not declared"));

        else {
            this.entry = tmp;
            this.nestinglevel = env.nestingLevel;

            for (Node arg : args)
                res.addAll(arg.checkSemantics(env));
        }
        */
        return null;
    }
}
