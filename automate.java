import java.io.*;
import java.util.*;
import java.nio.file.*;

class automate{

	public static int prefSuf(String m1,String m2){
		m1=m1.substring(0,m2.length());

		if(m1.equals(m2)){
			return m2.length();
		}

		else {
			return prefSuf(m1,m2.substring(1,m2.length()));
		}
	}

	public static int[][] delta(String m, String lettre){
		int tabDelta[][]=new int[m.length()+1][lettre.length()];

		String motCourant="";
		for (int i=0; i<m.length();i++ ) {
			for (int j=0; j<lettre.length();j++ ) {
				//System.out.println(motCourant+lettre.charAt(j));
				tabDelta[i][j]=prefSuf(m,motCourant+lettre.charAt(j));
			}
			motCourant=motCourant+m.charAt(i);
			System.out.println(motCourant);	
		}
		for (int j=0; j<lettre.length();j++ ) {
				//System.out.println(motCourant+lettre.charAt(j));
				tabDelta[m.length()][j]=prefSuf(m,(motCourant.substring(1))+lettre.charAt(j));
			}


		return tabDelta;
	}


	public static void chromo(){
		try{
		/*Scanner sc =new Scanner(new File("/Users/alexandre/automate/chr22.fa"));
		while (sc.hasNextLine()) {
   			seqEntier+=sc.nextLine().toUpperCase();
		}*/
		String seqEntier = new String(Files.readAllBytes(Paths.get("/Users/alexandre/automate/chr22.fa")));
		seqEntier=seqEntier.substring(6).toUpperCase();

		//System.out.println(seqEntier);

		BufferedReader motif = new BufferedReader(new FileReader("/Users/alexandre/automate/tags.txt"));
		String lineMotif;
		while ((lineMotif = motif.readLine()) != null) {
			System.out.println(lineMotif);
   			int tab[][]=delta(lineMotif,"ACGT");
			/*for (int i=0;i<tab.length;i++ ) {
				for (int j=0;j<tab[0].length; j++) {
					System.out.print(tab[i][j]);
				}
				System.out.println("");
			}*/				
   			rechMotif(seqEntier,lineMotif,tab);
		}
		motif.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}


	public static void main(String[]args){
		/*int tab[][]= delta("agagaca","acgt");
		for (int i=0;i<tab.length;i++ ) {
			for (int j=0;j<tab[0].length; j++) {
				System.out.print(tab[i][j]);
	
			}
		System.out.println("");
		}
		rechMotif("aaggaaaa","aa");*/
		chromo();
	}



	public static void rechMotif(String text,String motif,int[][]t){
		int tab[][]=t;
		/*for (int i=0;i<tab.length;i++ ) {
			for (int j=0;j<tab[0].length; j++) {
				System.out.print(tab[i][j]);
			}
		System.out.println("");
		}*/
		int indice=-1;	
		int etat=0;

		for (int i=0;i<text.length() ;i++ ) {

			if(text.charAt(i)=='A'){
				indice=0;
			}
			if(text.charAt(i)=='C'){
				indice=1;
			}
			if(text.charAt(i)=='G'){
				indice=2;
			}
			if(text.charAt(i)=='T'){
				indice=3;
			}
			System.out.println(etat==motif.length());
			etat =tab[etat][indice];
			if (etat==motif.length()){

				System.out.println("mot trouvé a l'indice "+(i-(motif.length()-1)));
			}

		}
	}
}