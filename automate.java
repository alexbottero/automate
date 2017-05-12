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
			//System.out.println(motCourant);	
		}
		for (int j=0; j<lettre.length();j++ ) {
				//System.out.println(motCourant+lettre.charAt(j));
				tabDelta[m.length()][j]=prefSuf(m,(motCourant.substring(1))+lettre.charAt(j));
			}


		return tabDelta;
	}



	public static void chromo(String chemin, String sortie ){
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(sortie));
			String seqEntier = new String(Files.readAllBytes(Paths.get("/Users/alexandre/automate/chr22.fa")));
			while(seqEntier.charAt(0)!='A'&& seqEntier.charAt(0)!='C'&& seqEntier.charAt(0)!='G'&& seqEntier.charAt(0)!='T'){
				seqEntier=seqEntier.substring(1);
			}
			seqEntier=seqEntier.toUpperCase();


			BufferedReader motif = new BufferedReader(new FileReader(chemin));
			String lineMotif;
			while ((lineMotif = motif.readLine()) != null) {
				//System.out.println(lineMotif);
	   			int tab[][]=delta(lineMotif,"ACGT");

	   			rechMotif(seqEntier,lineMotif,tab,out);
			}
			motif.close();
			out.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}


	public static void main(String[]args){
		chromo("/Users/alexandre/automate/tags.txt","/Users/alexandre/automate/sortie.txt");
	}
	public static void  rechMotif(String text,String motif,int[][]t,BufferedWriter s){
		
		int tab[][]=t;
		int etat=0;
		for (int i=0;i<text.length() ; i++ ) {
			int indice=-1;	
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
			if(indice != -1){
				etat = tab[etat][indice];
				if (etat==motif.length()){
					try{
						System.out.println(motif + " : "+(i-(motif.length()-1))+"\n");
						s.write( motif + " : "+(i-(motif.length()-1))+"\n");
					}
					catch(IOException e){ System.out.println(e);}
					
				}
			}
		}
	}
}