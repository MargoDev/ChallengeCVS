package gomez.mariano;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

public class Main {

	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	public static void main(String[] args) {
		try {
			// utilizo esta library para la lectura del csv
			CSVReader reader = new CSVReader(new FileReader("Melate.csv"));
			Map<String, List<Integer>> fechasNumeros = new HashMap();
			if (reader != null) {

				// aqui leo todo el contenido del CSV
				List<String[]> valores = reader.readAll();
				// aqui itero cada linea obtenida del CSV
				for (int i = 1; i < valores.size(); i++) { // omitiendo cabeceros
					String[] fila = valores.get(i);
					List<Integer> numeros = new ArrayList<>();
					// columnas en el CVS
					// 0:NPRODUCTO 1:CONCURSO 2:R1 3:R2 4:R3 5:R4 6:R5 7:R6 8:R7 9:BOLSA 10:FECHA
					String fecha = fila[10].substring(6, 10);
					for (int ii = 2; ii < 9; ii++) {
						numeros.add(Integer.parseInt(fila[ii]));
					}

					// agrupo los numeros por año
					if (!fechasNumeros.containsKey(fecha)) {
						fechasNumeros.put(fecha, numeros);
					} else {
						numeros.addAll(fechasNumeros.get(fecha));
						fechasNumeros.remove(fecha);
						fechasNumeros.put(fecha, numeros);
					}
				}
				// Se ordenan por fecha asc
				fechasNumeros = sortValues((HashMap) fechasNumeros, false);
				for (Map.Entry<String, List<Integer>> fechasN : fechasNumeros.entrySet()) {
					// ahora hay que determinar el que mas se repite por cada año
					System.out.println("En el año " + fechasN.getKey());
					System.out.println("El numero mas repetido fue :: " + mostrarRecurrente(fechasN.getValue()));
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static HashMap sortValues(HashMap mapaFechas, boolean desc) {

		List list = new LinkedList(mapaFechas.entrySet());
		
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (desc?o1:o2)).getKey())
						.compareTo(((Map.Entry) (desc?o2:o1)).getKey());
			}// solucion estandar para ordenamiento condicionado
		});
		
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

	private static String mostrarRecurrente(List<Integer> numeros) {
		//Se itera la lista de numeros para determinar cual se repite mas
	    int masRepetido= 0;
	    String recurrente= "";

	    for(int i=0; i<numeros.size(); i++)
	    {
	        int seRepite= 0;
	        for(int j=0; j<numeros.size(); j++)
	        {
	            if(numeros.get(i)==numeros.get(j))
	            {
	            	seRepite++;
	            }
	            if(seRepite>masRepetido)
	            {
	                recurrente= numeros.get(i).toString();
	                masRepetido= seRepite;
	            }
	        }
	    }   
	    return recurrente;
	}
}
