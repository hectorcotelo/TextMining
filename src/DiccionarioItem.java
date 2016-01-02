import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DiccionarioItem {
	
	private String palabraRaiz;
	private List<Documento> listaDeDocumentosQueContienenLaPalabraRaiz;
	
	public DiccionarioItem(String palabraRaiz){
		
		this.palabraRaiz = palabraRaiz;
		listaDeDocumentosQueContienenLaPalabraRaiz = new ArrayList<Documento>();		
	}		
	public String getPalabraRaiz() {
		return palabraRaiz;
	}
	public void setPalabraRaiz(String palabraRaiz) {
		this.palabraRaiz = palabraRaiz;
	}
	public List<Documento> getListaDeDocumentosQueContienenLaPalabraRaiz() {
		return listaDeDocumentosQueContienenLaPalabraRaiz;
	}
	public void setListaDeDocumentosQueContienenLaPalabraRaiz(List<Documento> listaDeDocumentosQueContienenLaPalabraRaiz) {
		this.listaDeDocumentosQueContienenLaPalabraRaiz = listaDeDocumentosQueContienenLaPalabraRaiz;
	}
	public int getCantidadDeVecesQueSeRepiteEnTodosLosDocumentos(){
		int cantidad = 0;
		for(Documento documento : listaDeDocumentosQueContienenLaPalabraRaiz){
			
			cantidad = cantidad +documento.cantidadDeVecesQueSeRepiteLaPalabraRaiz();
		}
		return cantidad;
	}

	public void agregaleUnaAparicion(String documento, String palabra) {
		for(Documento doc :listaDeDocumentosQueContienenLaPalabraRaiz){
			
			if(doc.getNombreDelDocumento().equals(documento)){
				//el documento ya tenia esa palabra
				doc.agregaleUnaAparicion(palabra);
				return;
			}
		}
		//es la primera vez que aparece la palabra en el documento
		Documento d = new Documento(documento);
		d.agregaleUnaAparicion(palabra);
		listaDeDocumentosQueContienenLaPalabraRaiz.add(d);
		
	}
	
	public static Comparator<DiccionarioItem> NameDiccionarioItemComparator = new Comparator<DiccionarioItem>() {

		public int compare(DiccionarioItem d1, DiccionarioItem d2) {

			String palabra1 = d1.getPalabraRaiz();
			String palabra2 = d2.getPalabraRaiz();

			//ascending order
			return palabra1.compareTo(palabra2);

			//descending order
			//return fruitName2.compareTo(fruitName1);
		}
	};
	
	
	public static Comparator<DiccionarioItem> CantidadDiccionarioItemComparator = new Comparator<DiccionarioItem>() {

		public int compare(DiccionarioItem d1, DiccionarioItem d2) {

			int cantidad1 = d1.getCantidadDeVecesQueSeRepiteEnTodosLosDocumentos();
			int cantidad2 = d2.getCantidadDeVecesQueSeRepiteEnTodosLosDocumentos();

			//ascending order
			return cantidad1-cantidad2;

			//descending order
			//return fruitName2.compareTo(fruitName1);
		}
	};


}
