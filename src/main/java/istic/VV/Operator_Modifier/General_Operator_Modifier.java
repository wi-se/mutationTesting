package istic.VV.Operator_Modifier;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class General_Operator_Modifier{
	
	/*@ param :The method takes the list containing the ByteCode and the operator we want to modify
	 * return : The list of the indexes of the operator we want To Modify found in the Bytecode list
	*/
	public List<Integer> IndexesOpToModify(List <String> operations, String opToModify){
		
		List <Integer> indexes = new LinkedList<>();
		for (int i = 0; i < operations.size(); i++)
	        if(opToModify.equals(operations.get(i)))
	            indexes.add(i);
		System.out.print("La liste des indexes de "+opToModify+" est : [");
		indexes.forEach(System.out::println);
		System.out.println("]");
	    return indexes;
	}		
	
	/* @param : The original list containing the bytecode, the original operator to replace, the Index of 
	 * that operator and the operator that replace it  
	 * return : The new list containing the MODIFIED Bytecode 
	 */
	public List<String> OpModified_by_Index(List <String> operations, int Index, String Op, String OpModifier){
		
		//list of the indexes of the Operator to change "Op" in operations list
		List<Integer> Op_Indexes= IndexesOpToModify(operations,Op);
		if (Op_Indexes.contains(Index)){
		System.out.println("Bon index de "+Op+" , sera changé en "+ OpModifier);
		operations.set(Index, OpModifier);
		}
		else {System.out.println("L'index donné pour "+Op+" n'est pas bon ou "+Op+" n'est pas présent dans le bytecode, pas de modification opérée");}
		return operations;
		
		}

}
