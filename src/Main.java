import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hola Mundo!");
		String directorioRaiz = "C:\\Users\\Héctor\\Desktop\\postulaciones\\plano\\falso\\";

		File f = new File(directorioRaiz);
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
		LectorDeDocumentos ldd = new LectorDeDocumentos();
		StopWords sw = new StopWords();
		Diccionario diccionario = new Diccionario();
		for (String name : names) {

			System.out.println(name);

			
			String documento = directorioRaiz + name;
			List<String> palabrasDelDoc = ldd.leer(documento);
			
			palabrasDelDoc = sw.filtarStopWords(palabrasDelDoc);
			
			HalladorDeRaicesDePalabras hdrdp = new HalladorDeRaicesDePalabras();
			for (String palabra : palabrasDelDoc) {
				String raizDeLaPalabra = hdrdp.getRaiz(palabra);
				boolean yaEstaPalabra = diccionario.yaTenes(raizDeLaPalabra);
				if (yaEstaPalabra) {
					// System.out.println("YA ESTA LA PALABRA");
					diccionario.agregaleUnaAparicion(documento, raizDeLaPalabra, palabra);
				} else {
					// System.out.println("LA PALABRA NO ESTA, AGREGUEMOSLA");
					diccionario.agregarItem(documento, raizDeLaPalabra, palabra);
				}

			}

		}
		diccionario.ordernarItemsPorCantidad();
		// diccionario.imprimirEntradasConLaCantidadDeVecesQueSeRepiteEnTodaLaColeccion();
		//diccionario.imprimirPalabrazRaizConSusDerivadas();
		diccionario.imprimirPalabrasRaizConCuantasVecesApareceEnCadaDocumento();
		
		
		diccionario.imprimirTermFrecuencysForAllSchemes("pedro", "C:\\Users\\Héctor\\Desktop\\postulaciones\\plano\\falso\\hector.txt");
			
			
		diccionario.imprimirInverseDocumentFrequencyForAllSchemes("pedro");
		
		System.out.println("Le argegue la siguiente cantidad de Documentos al diccionario "+diccionario.getCantidadDeDocumentos());
		
		
		System.out.println("Chau Mundo!");
	}

}
