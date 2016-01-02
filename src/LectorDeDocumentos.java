import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorDeDocumentos {
	
	
	public List<String> leer(String rutaDelDocumento){
		
		BufferedReader br=null;
		List<String> palabras = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(rutaDelDocumento));
		  
		    String line = br.readLine();

		    while (line != null) {
		    	String[] lasPalabrasDeLaLinea = line.split(" ");
		    	for(int i = 0; i<lasPalabrasDeLaLinea.length; i++){
		    		String palabraEnCuestion = lasPalabrasDeLaLinea[i].toLowerCase();
		    		
		    		while(palabraEnCuestion.endsWith(".") || palabraEnCuestion.endsWith(",")
		    				||palabraEnCuestion.endsWith("”")
		    				||palabraEnCuestion.endsWith(":")
		    				||palabraEnCuestion.endsWith("!")
		    				||palabraEnCuestion.endsWith(")")
		    				
		    				
		    				){
		    		
		    			
		    			palabraEnCuestion = palabraEnCuestion.substring(0, palabraEnCuestion.length()-1);
		    			//System.out.println("la palabra en cuestion es "+palabraEnCuestion);
		    		}	
		    		
		    		
		    		while(palabraEnCuestion.startsWith(".") 
		    				|| palabraEnCuestion.startsWith(",")
		    				||palabraEnCuestion.startsWith("”")
		    				||palabraEnCuestion.startsWith(":")
		    				||palabraEnCuestion.startsWith("!")
		    				||palabraEnCuestion.startsWith("“")
		    				||palabraEnCuestion.startsWith("¡")
		    				||palabraEnCuestion.startsWith("(")
		    				){
		    		
		    			
		    			palabraEnCuestion = palabraEnCuestion.substring(1, palabraEnCuestion.length());
		    			//System.out.println("la palabra en cuestion es "+palabraEnCuestion);
		    		}
		    		
		    		if(!palabraEnCuestion.trim().equals("")){
		    			palabras.add(palabraEnCuestion);
		    		}
		    		
		    		
		    	}
		        
		        line = br.readLine();
		    }
		    
		    return palabras;
		    
		}catch(Exception e){
			e.printStackTrace();
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	

}
