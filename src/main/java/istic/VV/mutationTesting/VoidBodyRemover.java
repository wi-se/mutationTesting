package istic.VV.mutationTesting;

import java.util.LinkedList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class VoidBodyRemover{
	
	
	/*
	 * method that returns the list of all the void methods
	 */
	
	public List<CtMethod> VoidMethods(CtMethod[] methods) throws NotFoundException, CannotCompileException {
		
		List <CtMethod> VoidMet = new LinkedList <> () ;
		
		for (CtMethod method1: methods) {
			if(method1.getReturnType().equals(CtClass.voidType)){
			VoidMet.add(method1);
			}		    			
					}
		return VoidMet;
	}
	
	/*-
	 * remove the body of a given method existing in the list/ if doesn't exist, do nothing
	 */
	public void BodyRemover(List <CtMethod> methods, CtMethod method) throws NotFoundException, CannotCompileException {
		
		if (methods.contains(method)){
			method.setBody(null);
		}
		/*
		for (CtMethod method1: methods) {
			if(method1.getName().equals(method)){
				method1.setBody(null);
			}*/
		else {
				System.out.println("La méthode demandée n'existe pas ou n'est pas du type void");
			}
					}
	}
	
	/*
	 * BodyRemover takes a tab of methods and set to null the body of void methods only  
	 */
	/*
	public void bodyRemover(CtMethod[] methods) throws NotFoundException, CannotCompileException {
	
	for (CtMethod method1: methods) {
		if(method1.getReturnType().equals(CtClass.voidType)		System.out.println("\nVoid Method "+ method1.getMethodInfo());
		method1.setBody(null);
		}		    			
				}
	}
	*/

