import java.util.ArrayList;
import java.util.List;

public class Diccionario {

	List<DiccionarioItem> listaDeItems;

	public Diccionario() {

		listaDeItems = new ArrayList<DiccionarioItem>();
	}

	public List<DiccionarioItem> getDiccionario() {

		return listaDeItems;
	}
	
	
	public boolean yaTenes(String raizDeLaPalabra) {
		for (DiccionarioItem item : listaDeItems) {
			if (item.getPalabraRaiz().equalsIgnoreCase(raizDeLaPalabra)) {
				return true;
			}
		}
		return false;
	}

	public DiccionarioItem getDiccionarioItem(String term) {
		for (DiccionarioItem item : listaDeItems) {
			if (item.getPalabraRaiz().equalsIgnoreCase(term)) {
				return item;
			}
		}
		return null;
	}

	public void agregaleUnaAparicion(String documento, String raizDeLaPalabra, String palabra) {
		// System.out.println("ESTOY EN EL METODO agregaleUnaAparicion DE LA
		// CLASE Diccionario. VOY A AGREGARLE AL DOCUMENTO "+documento+" LA
		// PALABRA "+palabra+" CUYA RAIZ ES "+raizDeLaPalabra );
		for (DiccionarioItem item : listaDeItems) {
			if (item.getPalabraRaiz().equalsIgnoreCase(raizDeLaPalabra)) {
				item.agregaleUnaAparicion(documento, palabra);
				return;
			}

		}

	}

	public void agregarItem(String documento, String raizDeLaPalabra, String palabra) {
		// System.out.println("ESTOY EN EL METODO agregarItem DE LA CLASE
		// Diccionario. VOY A AGREGARLE AL DOCUMENTO "+documento+" LA PALABRA
		// "+palabra+" CUYA RAIZ ES "+raizDeLaPalabra );
		DiccionarioItem item = new DiccionarioItem(raizDeLaPalabra);

		item.agregaleUnaAparicion(documento, palabra);
		listaDeItems.add(item);
	}

	public void imprimirEntradas() {
		for (DiccionarioItem item : listaDeItems) {

			System.out.println(item.getPalabraRaiz());
		}

	}

	public void imprimirEntradasConLaCantidadDeVecesQueSeRepiteEnTodaLaColeccion() {
		for (DiccionarioItem item : listaDeItems) {

			System.out.println(item.getPalabraRaiz() + " " + item.getCantidadDeVecesQueSeRepiteEnTodosLosDocumentos());
		}

	}

	public void ordernarItemsPorCantidad() {
		// List<DiccionarioItem> listaDeItems;
		DiccionarioItem temp;
		for (int i = 0; i < listaDeItems.size(); i++) {
			for (int j = i; j < listaDeItems.size() - 1; j++) {
				if (listaDeItems.get(i).getCantidadDeVecesQueSeRepiteEnTodosLosDocumentos() < listaDeItems.get(j + 1)
						.getCantidadDeVecesQueSeRepiteEnTodosLosDocumentos()) {
					temp = listaDeItems.get(j + 1);
					listaDeItems.set(j + 1, listaDeItems.get(i));
					listaDeItems.set(i, temp);
				}
			}
		}

		// listaDeItems.sort(DiccionarioItem.CantidadDiccionarioItemComparator);

	}

	public void imprimirPalabrazRaizConSusDerivadas() {
		for (DiccionarioItem item : listaDeItems) {

			String lineaAImprimir = "La palabra raiz " + item.getPalabraRaiz() + " se repite "
					+ item.getCantidadDeVecesQueSeRepiteEnTodosLosDocumentos() + " siendo sus derivadas: ";
			for (Documento d : item.getListaDeDocumentosQueContienenLaPalabraRaiz()) {
				for (PalabraCantidad pc : d.getListaDePalabrasConSusCantidades()) {
					lineaAImprimir = lineaAImprimir + pc.getPalabra() + ", ";
				}

			}
			lineaAImprimir = lineaAImprimir.substring(0, lineaAImprimir.length() - 2);
			System.out.println(lineaAImprimir);
		}

	}
	
	public double cantidadDePalabrasDeTodoElCorpus(){
		List<String> documentosDistintos = this.getDocumentosDistintos();
		double cantidadDePalabras = 0.0;
		for(String documento : documentosDistintos){
			cantidadDePalabras = cantidadDePalabras + this.getCantidadDePalabrasDelDocumento(documento);
			
		}
		
		return cantidadDePalabras;
	}
	
	
	public double avgdl(){
		return cantidadDePalabrasDeTodoElCorpus()/this.getDocumentosDistintos().size();
		
	}
	
	
	public void imprimirTFIDFParaTodosLosDocumentos(String palabra){
		this.setTFIDFForAllTermsForAllDocuments(3);
		palabra = new HalladorDeRaicesDePalabras().getRaiz(palabra);
		DiccionarioItem di = this.getDiccionarioItem(palabra);
		for(Documento d : di.getListaDeDocumentosQueContienenLaPalabraRaiz()){
			System.out.println("Para la palabra "+palabra+ " el documento "+d.getNombreDelDocumento()+" tiene un TFIDF de "+d.getTFIDF()+" siendo que en el documento se repiten "+d.cantidadDeVecesQueSeRepiteLaPalabraRaiz());
			
		}
		
		
	}
	
	public double IDF(String queryItem){
		return Math.log((this.getCantidadDeDocumentos()-this.getCantidadDeDocumentosQueTienenPalabra(queryItem)+0.5)/(this.getCantidadDeDocumentosQueTienenPalabra(queryItem)+0.5));
		
		
	}
	
	public double OkapiBM25(String document, String query){
		
		String[] querytokens = query.split(" ");
		double score = 0 ;
		double k = 1.5;
		double b = 0.75;
		for(int i = 0; i<querytokens.length; i++){
			String oneToken = new HalladorDeRaicesDePalabras().getRaiz(querytokens[i]);
			score = score + IDF(oneToken)*((this.termFrecuency(oneToken, document, "log normalization", -1.0)*(k+1))/(this.termFrecuency(oneToken, document, "log normalization", -1.0)+k*(1-b+b*this.getCantidadDePalabrasDelDocumento(document)/this.avgdl())));
		}
		return score;
	}
	
	public void buscarDocumentos(String query){
		setTFIDFForAllTermsForAllDocuments(3);
		List<String> documentos = this.getDocumentosDistintos();
		for(String documento : documentos){
			double value = OkapiBM25(documento, query);
			System.out.println(documento+" "+value);
			
		}
		
		
		
	}
	
	
	public double TFIDF(String term, String document, int weightingScheme){
		if(weightingScheme==1){
			return this.termFrecuency(term, document, "raw frequency", -1.0)*this.InverseDocumentFrequency(term, "inverse document frequency");			
		}else if(weightingScheme==2){
			return this.termFrecuency(term, document, "log normalization", -1.0);
			
		}else if(weightingScheme==3){
			return this.termFrecuency(term, document, "log normalization", -1.0)*this.InverseDocumentFrequency(term, "inverse document frequency");
			
		}
		
		
		
		return -1.0;
	}
	
	
	public int getCantidadDePalabrasDelDocumento(String documento){
		int cantidadDePalabras = 0;
		for(DiccionarioItem di : listaDeItems){
			for(Documento d : di.getListaDeDocumentosQueContienenLaPalabraRaiz()){
				if(documento.equals(d.getNombreDelDocumento())){
					cantidadDePalabras = cantidadDePalabras + d.cantidadDeVecesQueSeRepiteLaPalabraRaiz();
					
					
				}
			}
			
			
		}
		return cantidadDePalabras;
		
	}
	
	
	
	public void setTFIDFForAllTermsForAllDocuments(int tFIDFScheme){
		for(DiccionarioItem di : listaDeItems){
			List<Documento> documentos = di.getListaDeDocumentosQueContienenLaPalabraRaiz();
			for(Documento documento : documentos){
				documento.setTFIDF(this.TFIDF(di.getPalabraRaiz(), documento.getNombreDelDocumento(), tFIDFScheme));
			}
		}
	}
	
	
	public void getKeyWordsForCorpus(int cantidadDeKeywords, int TFIDFScheme){
		int verdaderaCantidadDeKeywords = cantidadDeKeywords;
		
		List<String> documentos = this.getDocumentosDistintos();
		class PalabraTFIDF{
			public String palabra;
			public double TFIDF;
			public PalabraTFIDF(String palabra, double TFIDF){
				this.palabra = palabra;
				this.TFIDF = TFIDF;
			}
			
		}
		
		setTFIDFForAllTermsForAllDocuments(TFIDFScheme);
		
		for(String nombreDelDocumentoEnElQueEstoyParado : documentos){
			List<PalabraTFIDF> words = new ArrayList<PalabraTFIDF>();
			for(DiccionarioItem di : listaDeItems){
				for(Documento doc : di.getListaDeDocumentosQueContienenLaPalabraRaiz()){
					if(doc.getNombreDelDocumento().equals(nombreDelDocumentoEnElQueEstoyParado)){
						words.add(new PalabraTFIDF(di.getPalabraRaiz(), doc.getTFIDF()));
						
					}
				}
				
			}
			
			PalabraTFIDF temp;
			for (int i = 0; i < words.size(); i++) {
				for (int j = i; j < words.size() - 1; j++) {
					if (words.get(i).TFIDF < words.get(j + 1).TFIDF) {
						temp = words.get(j + 1);
						words.set(j + 1, words.get(i));
						words.set(i, temp);
					}
				}
			}
			
			String line = "Las keywords de "+nombreDelDocumentoEnElQueEstoyParado+" son: ";
			
			//System.out.println("Para el documento "+nombreDelDocumentoEnElQueEstoyParado+" hay "+words.size()+" words");
			
			if(cantidadDeKeywords>=words.size()){
				cantidadDeKeywords = words.size()-1;
			}
			
			for(int i = 0; i<cantidadDeKeywords; i++){
				line = line + words.get(i).palabra+" ";
				
				
			}
			System.out.println(line);
			cantidadDeKeywords = verdaderaCantidadDeKeywords;
		
		}
		
	}

	public void imprimirPalabrasRaizConCuantasVecesApareceEnCadaDocumento(int cantidadDePalabras) {
		if(cantidadDePalabras>=listaDeItems.size()){
			cantidadDePalabras = listaDeItems.size()-1;
		}
		
		
		
		for (int i = 0 ; i<cantidadDePalabras; i++) {
			DiccionarioItem item = listaDeItems.get(i);
			String lineaAImprimir = "La palabra raiz " + item.getPalabraRaiz() + " se repite "
					+ item.getCantidadDeVecesQueSeRepiteEnTodosLosDocumentos() + " veces: ";
			for (Documento d : item.getListaDeDocumentosQueContienenLaPalabraRaiz()) {
				lineaAImprimir = lineaAImprimir + d.cantidadDeVecesQueSeRepiteLaPalabraRaiz() + " veces en el documento " + d.getNombreDelDocumento()+", ";

			}
			lineaAImprimir = lineaAImprimir.substring(0, lineaAImprimir.length() - 2);
			System.out.println(lineaAImprimir);
		}

	}
	
	public double termFrecuency(String term, String document, String weightingScheme, double k){
		
		DiccionarioItem item = this.getDiccionarioItem(term);
		
		if(item == null){
			
			return -1.0;
		}
		
		
		List<Documento> documentos = item.getListaDeDocumentosQueContienenLaPalabraRaiz();
		Documento documento = null;
		for(Documento d : documentos){
			if(d.getNombreDelDocumento().equals(document)){
				documento = d;
				break;
			}
		}
		if(documento == null){
			return 0.0;
			
		}
		
		if(weightingScheme.equals("binary")){
			return 1.0;			
		}else if(weightingScheme.equals("raw frequency")){
			return documento.cantidadDeVecesQueSeRepiteLaPalabraRaiz();			
		}else if(weightingScheme.equals("log normalization")){
			return 1+Math.log(documento.cantidadDeVecesQueSeRepiteLaPalabraRaiz());			
		}else if(weightingScheme.equals("double normalization 0.5")){
			return 0.5+0.5*(documento.cantidadDeVecesQueSeRepiteLaPalabraRaiz()+this.getFrecuenciaDeLaPalabraQueSeRepiteMasVecesEnElDocumento(document).getCantidad());
			
		}else if(weightingScheme.equals("double normalization K")){
			return k+(1-k)*(documento.cantidadDeVecesQueSeRepiteLaPalabraRaiz()+this.getFrecuenciaDeLaPalabraQueSeRepiteMasVecesEnElDocumento(document).getCantidad());
		}else if(weightingScheme.equals("cotelo")){
			return documento.cantidadDeVecesQueSeRepiteLaPalabraRaiz()/this.getCantidadDePalabrasDelDocumento(document);
		}
		
		return -1.0;
	}

	public PalabraCantidad getFrecuenciaDeLaPalabraQueSeRepiteMasVecesEnElDocumento(String document) {
		int mayorCantidadDeRepeticiones = 0;
		String palabra = "";
		for(DiccionarioItem item : listaDeItems){
			for(Documento d : item.getListaDeDocumentosQueContienenLaPalabraRaiz()){
				if(d.getNombreDelDocumento().equals(document)){
					if(d.cantidadDeVecesQueSeRepiteLaPalabraRaiz()>mayorCantidadDeRepeticiones){
						palabra = item.getPalabraRaiz();
						mayorCantidadDeRepeticiones = d.cantidadDeVecesQueSeRepiteLaPalabraRaiz();
					}
				}
				
			}
			
		}
		return new PalabraCantidad(palabra, mayorCantidadDeRepeticiones);
	}

	public void imprimirTermFrecuencysForAllSchemes(String term, String documento) {
		System.out.println("Las estadisticas de la palabra "+term+" en el documento "+documento+" son:");
		System.out.println("binary: "+this.termFrecuency(term, documento, "binary", -1));
		System.out.println("raw frequency: "+this.termFrecuency(term, documento, "raw frequency", -1));
		System.out.println("log normalization: "+this.termFrecuency(term, documento, "log normalization", -1));
		System.out.println("double normalization 0.5: "+this.termFrecuency(term, documento, "double normalization 0.5", -1));
		System.out.println("double normalization K (k=0.1): "+this.termFrecuency(term, documento, "double normalization K", 0.1));
		
	}

	public void imprimirInverseDocumentFrequencyForAllSchemes(String term) {
		System.out.println("Las estadisticas de la palabra "+term+" son:");
		System.out.println("unary: "+this.InverseDocumentFrequency(term, "unary"));
		System.out.println("inverse document frequency: "+this.InverseDocumentFrequency(term, "inverse document frequency"));
		System.out.println("inverse document frequency smooth: "+this.InverseDocumentFrequency(term, "inverse document frequency smooth"));
		System.out.println("inverse document frequency max: "+this.InverseDocumentFrequency(term, "inverse document frequency max"));
		System.out.println("probabilistic inverse document frequency: "+this.InverseDocumentFrequency(term, "probabilistic inverse document frequency"));
		
	}
	
	
	public int getCantidadDeDocumentosQueTienenPalabra(String term){
		DiccionarioItem item = this.getDiccionarioItem(term);
		return item.getListaDeDocumentosQueContienenLaPalabraRaiz().size();
	}
	
	
	public DiccionarioItem getDiccionarioItemQueSeReptireMasEnTodoElCorpus(){
		int cantidad = 0 ;
		DiccionarioItem elMasRepetido = null;
		for(DiccionarioItem di : listaDeItems){
			if(cantidad<di.getCantidadDeVecesQueSeRepiteEnTodosLosDocumentos()){
				cantidad = di.getCantidadDeVecesQueSeRepiteEnTodosLosDocumentos();
				elMasRepetido = di;
			}
			
		}
		return elMasRepetido;
	}
	
	public int getCantidadDeDocumentos(){
		return this.getDocumentosDistintos().size();
		
	}
	
	
	public List<String> getDocumentosDistintos(){
		List<String> documentosDistintos = new ArrayList<String>();
		for (DiccionarioItem item : listaDeItems) {
			List<Documento> documentos = item.getListaDeDocumentosQueContienenLaPalabraRaiz();
			for(Documento d : documentos){
				boolean yaEstaEnLaListaDeDocumentosDistintos = false;
				for(String documentoDistinto : documentosDistintos){
					if(documentoDistinto.equals(d.getNombreDelDocumento())){
						yaEstaEnLaListaDeDocumentosDistintos = true;
						break;
					}
				}
				if(!yaEstaEnLaListaDeDocumentosDistintos){
					documentosDistintos.add(d.getNombreDelDocumento());
				}
				yaEstaEnLaListaDeDocumentosDistintos = false;
			}
			
		}
		return documentosDistintos;
	}

	public double InverseDocumentFrequency(String term, String weightingScheme) {
		
		DiccionarioItem item = this.getDiccionarioItem(term);
		
		if(item == null){
			return -1.0;
			
		}
	//	List<Documento> documentos = item.getListaDeDocumentosQueContienenLaPalabraRaiz();
		
		
		if(weightingScheme.equals("unary")){
			return 1.0;
		}if(weightingScheme.equals("inverse document frequency")){
			
			return Math.log(getCantidadDeDocumentos()/this.getCantidadDeDocumentosQueTienenPalabra(term));
			
		}if(weightingScheme.equals("inverse document frequency smooth")){
			return Math.log(1+getCantidadDeDocumentos()/this.getCantidadDeDocumentosQueTienenPalabra(term));
			
		}if(weightingScheme.equals("inverse document frequency max")){
			return Math.log(1+this.getDiccionarioItemQueSeReptireMasEnTodoElCorpus().getCantidadDeVecesQueSeRepiteEnTodosLosDocumentos()/this.getCantidadDeDocumentosQueTienenPalabra(term));
		}if(weightingScheme.equals("probabilistic inverse document frequency")){
			return Math.log((getCantidadDeDocumentos()-this.getCantidadDeDocumentosQueTienenPalabra(term))/this.getCantidadDeDocumentosQueTienenPalabra(term));
		}		
		return -1.0;
	}

}
