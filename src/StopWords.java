import java.util.ArrayList;
import java.util.List;

public class StopWords {
	
	private List<String> stopWords;
	
	
	public StopWords(){
		
		stopWords = new ArrayList<String>();
	//	stopWords.add("de");
	//	stopWords.add("que");
	//	stopWords.add("en");
	//	stopWords.add("el");
	//	stopWords.add("a");
	//	stopWords.add("la");
	//	stopWords.add("y");
	//	stopWords.add("los");
	//	stopWords.add("del");
	//	stopWords.add("para");
	//	stopWords.add("se");
	//	stopWords.add("un");
	//	stopWords.add("las");
	//	stopWords.add("cada");
	//	stopWords.add("con");
	//	stopWords.add("es");
	//	stopWords.add("como");
	//	stopWords.add("por");
	//	stopWords.add("al");
	//	stopWords.add("no");
	//	stopWords.add("una");
	//	stopWords.add("este");
	//	stopWords.add("más");
	//	stopWords.add("lo");
	//	stopWords.add("esta");
	//	stopWords.add("o");
	//	stopWords.add("así");
	//	stopWords.add("su");
	//	stopWords.add("me");
		
	//	stopWords.add("sus");
	//	stopWords.add("pero");
	//	stopWords.add("-");
		
		stopWords.add("4845");
		stopWords.add("carolina");
		stopWords.add("pérez");
		stopWords.add("4849");
		stopWords.add("4855");
		stopWords.add("laura");
		stopWords.add("josé");
		stopWords.add("•");
		stopWords.add("le");
		
		stopWords.add("4895");
		
		stopWords.add("-");
		stopWords.add("ende");
		stopWords.add("atentamente");
		stopWords.add("éstos");
		
		stopWords.add("respecta");
		 
		stopWords.add("igual");
		stopWords.add("c");
		stopWords.add("paz");
		stopWords.add("2006");
		stopWords.add("después");
		stopWords.add("todavía");
		
		stopWords.add("alguno");
		stopWords.add("va");
		stopWords.add("vamos");
		stopWords.add("09");
		stopWords.add("actualmente");
		
		
	}
	
	
	public List<String> filtarStopWords(List<String> lista){
		List<String> nuevaLista = new ArrayList<String>();
		boolean esUnaPalabraFea;
		for(String palabra : lista){
			esUnaPalabraFea = false;
			for(String palabraFea : stopWords){
				if(palabra.equalsIgnoreCase(palabraFea)){
					esUnaPalabraFea = true;
				}
				
			}
			if(!esUnaPalabraFea){
				nuevaLista.add(palabra);
			}
			
		}
		return nuevaLista;
		
	}

}
