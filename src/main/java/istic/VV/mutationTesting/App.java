package istic.VV.mutationTesting;


import java.util.LinkedList;
import java.util.List;

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
    	Operator_Modifier operator1 = new Operator_Modifier();
        List <String> modifiedOperations = new LinkedList<>(); 
        
    	ClassPool cp = ClassPool.getDefault();
    	CtClass cc = cp.get("test_package.Dummy_Code_to_Test");
    	CtMethod[] methods = cc.getDeclaredMethods();
    	
    	VoidBodyRemover test = new VoidBodyRemover();
    	List <CtMethod> meth = test.VoidMethods(methods); 
    	test.BodyRemover(meth, cc.getDeclaredMethod("testons"));
    	  
    	
    	
    	for (CtMethod method: methods) {
    		
    		System.out.println("\nBytecode of the Method "+ method.getMethodInfo()+" is :");    		
    		MethodInfo minfo = method.getMethodInfo();
    		CodeAttribute ca = minfo.getCodeAttribute();
    		CodeIterator ci = ca.iterator();
    		
    		List<String> operations = new LinkedList<>();
    		    		    		
    		while (ci.hasNext()) {
    			int index = ci.next();
    			int op = ci.byteAt(index);
    			operations.add(Mnemonic.OPCODE[op]);    	
    			System.out.println(Mnemonic.OPCODE[op]);
    		}  		
    		/*
    		modifiedOperations=operator1.OpModified_by_Index(operations, 4, "idiv", "imul");
    		modifiedOperations=operator1.OpModified_by_Index(operations, 8,"iadd","isub");
    		modifiedOperations.forEach(System.out::println);
    		*/    		    		   		
    		//cc.toClass();
    	}    	
    	}
}
