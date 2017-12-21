package istic.VV.mutationTesting;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Operator_Modifier{
	
	//return the indexes of the operator To Modify found in the list
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
	
	public List<String> OpModified_by_Index(List <String> operations, int Index, String Op, String OpModifier){
		
		//list of the indexes of idiv in operations list
		List<Integer> Div_Indexes= IndexesOpToModify(operations,Op);
		if (Div_Indexes.contains(Index)){
		System.out.println("Bon index de "+Op+" , sera changé en "+ OpModifier);
		operations.set(Index, OpModifier);
		}
		else {System.out.println("L'index donné pour "+Op+" n'est pas bon ou "+Op+" n'est pas présent dans le bytecode, pas de modification opérée");}
		return operations;
		
		}

}
