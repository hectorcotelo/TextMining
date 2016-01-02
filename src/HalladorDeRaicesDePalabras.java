
public class HalladorDeRaicesDePalabras {
	
	
	
	public String getRaiz(String palabra){
		
		if(palabra.equalsIgnoreCase("distrital")){
			palabra = "distrito";
		}
		if(palabra.equalsIgnoreCase("trabajo")){
			palabra = "trabajar";
		}
		
		if(palabra.equalsIgnoreCase("clubes")){
			palabra = "club";
		}
		
		if(palabra.equalsIgnoreCase("socios")){
			palabra = "socio";
		}
		
		if(palabra.equalsIgnoreCase("creo")){
			palabra = "creer";
		}
		if(palabra.equalsIgnoreCase("liderazgo")){
			palabra = "liderar";
		}
		
		
		
		
		
		return palabra;
		
	}

}
