
public class PalabraCantidad {
	
	
	private String palabra;
	private int cantidad;
	
	
	public PalabraCantidad(){
		palabra = "";
		cantidad = 0;
	}
	
	public PalabraCantidad(String palabra, int cantidad){
		this.palabra = palabra;
		this.cantidad = cantidad;
	}
	
	
	public String getPalabra() {
		return palabra;
	}
	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	

}
