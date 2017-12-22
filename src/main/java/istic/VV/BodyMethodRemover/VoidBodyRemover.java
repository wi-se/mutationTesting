package istic.VV.BodyMethodRemover;

import java.util.LinkedList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class VoidBodyRemover {

	/*
	 * method that returns the list of all the void methods
	 */

	public List<CtMethod> VoidMethods(CtMethod[] methods) throws NotFoundException, CannotCompileException {

		List<CtMethod> VoidMet = new LinkedList<>();

		for (CtMethod method1 : methods) {
			if (method1.getReturnType().equals(CtClass.voidType)) {
				VoidMet.add(method1);
			}
		}
		return VoidMet;
	}

	/*-
	 * remove the body of a given method existing in the list of void methods / if doesn't exist, do nothing
	 */
	public void BodyRemover(List<CtMethod> methods, CtMethod method) throws NotFoundException, CannotCompileException {

		if (methods.contains(method)) {
			method.setBody(null);
		} else {
			System.out.println("La méthode demandée n'existe pas ou n'est pas du type void");
		}
	}
}
