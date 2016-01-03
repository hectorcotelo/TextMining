import java.util.ArrayList;
import java.util.List;

public class Documento {
	
	private double TFIDF = 0.0;
	private String nombreDelDocumento;
	private List<PalabraCantidad> listaDePalabrasConSusCantidades;
	
	public void setTFIDF(double tfidf){
		
		this.TFIDF = tfidf;
	}
	
	
	public double getTFIDF(){
		return this.TFIDF;
		
	}
		
	public Documento (String nombre){
		this.nombreDelDocumento = nombre;
		
		listaDePalabrasConSusCantidades = new ArrayList<PalabraCantidad>();
	}	
	
	public String getNombreDelDocumento() {
		return nombreDelDocumento;
	}

	public void setNombreDelDocumento(String nombreDelDocumento) {
		this.nombreDelDocumento = nombreDelDocumento;
	}

	public List<PalabraCantidad> getListaDePalabrasConSusCantidades() {
		return listaDePalabrasConSusCantidades;
	}

	public void setListaDePalabrasConSusCantidades(List<PalabraCantidad> listaDePalabrasConSusCantidades) {
		this.listaDePalabrasConSusCantidades = listaDePalabrasConSusCantidades;
	}

	public int cantidadDeVecesQueSeRepiteLaPalabraRaiz(){
		int cantidad = 0;
		for(PalabraCantidad pc : listaDePalabrasConSusCantidades){
			cantidad = cantidad + pc.getCantidad();
			
		}
		return cantidad;
	}

	
	
	public void agregaleUnaAparicion(String palabra) {
		for(PalabraCantidad palcant : listaDePalabrasConSusCantidades){
			if(palabra.equalsIgnoreCase(palcant.getPalabra())){
				//ya habia aparecido esa palabra en el documento, le incremento la cantidad
				palcant.setCantidad(palcant.getCantidad()+1);
				return;
				
			}
			
		}
		//todavia no habia aparecido esa palabra en el documento, se la voy a asociar
		PalabraCantidad pc = new PalabraCantidad();
		pc.setCantidad(1);
		pc.setPalabra(palabra);
		listaDePalabrasConSusCantidades.add(pc);
		
	}

}
