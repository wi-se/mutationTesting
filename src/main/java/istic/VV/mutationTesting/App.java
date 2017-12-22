package istic.VV.mutationTesting;


import java.util.LinkedList;
import java.util.List;

import istic.VV.BodyMethodRemover.VoidBodyRemover;
import istic.VV.Operator_Modifier.General_Operator_Modifier;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Mnemonic;

public class App 
{
    public static void main( String[] args ) throws NotFoundException, BadBytecode, Throwable
    {	
    	General_Operator_Modifier operator1 = new General_Operator_Modifier();
        List <String> modifiedOperations = new LinkedList<>(); 
        
    	ClassPool cp = ClassPool.getDefault();
    	CtClass cc = cp.get("istic.VV.Tested_Classes_package.Dummy_Code_to_Test");
    	CtMethod[] methods = cc.getDeclaredMethods();
    	
    	/*
    	VoidBodyRemover test = new VoidBodyRemover();
    	List <CtMethod> meth = test.VoidMethods(methods); 
    	test.BodyRemover(meth, cc.getDeclaredMethod("testons"));
    	*/
    	
    	
    	for (CtMethod method: methods) {
    		
    		//System.out.println("\nBytecode of the Method "+ method.getMethodInfo()+" is :");    		
    		MethodInfo minfo = method.getMethodInfo();
    		CodeAttribute ca = minfo.getCodeAttribute();
    		CodeIterator ci = ca.iterator();
    		
    		List<String> operations = new LinkedList<>();
    		    		    		
    		while (ci.hasNext()) {
    			int index = ci.next();
    			int op = ci.byteAt(index);
    			operations.add(Mnemonic.OPCODE[op]);    	
    			//System.out.println(Mnemonic.OPCODE[op]);
    		}  		
    		
    		/*modifiedOperations=operator1.OpModified_by_Index(operations, 4, "idiv", "imul");
    		modifiedOperations=operator1.OpModified_by_Index(operations, 2,"iadd","isub");
    		modifiedOperations.forEach(System.out::println);
    		*/
    	}    	
    	}
}
