//Programme de reconnaissance de genes

import java.io.*;
import java.util.*;
import java.nio.file.*;

class automate{

	/*
	La fonction delta recupere le mot a analyser et l'alphabet existant.
	Avec ces donnees, on est capable de creer le tableau de transition.
	*/
	public static int[][] delta(String m, String lettre){
		//On cree une matrice de la taille du mot x la taille de l'alphabet.
		int tabDelta[][]=new int[m.length()+1][lettre.length()];

		//On cree une chaine vide qu'on vient remplir a chaque tour avec les differentes lettres de l'alphabet
		String motCourant="";
		for (int i=0; i<m.length();i++ ) {
			for (int j=0; j<lettre.length();j++ ) {
				tabDelta[i][j]=prefSuf(m,motCourant+lettre.charAt(j));
			}
			//On ajoute a la chaine de caractere la lettre du mot au rang ou l'on se trouve.
			motCourant=motCourant+m.charAt(i);
		}

		//Pour le derniere etat, on repasse par une autre boucle pour finir de completer le tableau de transition.
		for (int j=0; j<lettre.length();j++ ) {
				tabDelta[m.length()][j]=prefSuf(m,(motCourant.substring(1))+lettre.charAt(j));
			}


		return tabDelta;
	}

	/*
	prefSuf compare 2 chaine de caractere de maniere recursive.
	On coupe d'abord le mot suivant la taille du motif.
	Puis on compare les 2, en supprimant a chaque fois le debut du motif s'il n'y a pas d'egalite pour pouvoir trouver le
	plus grand prefixe du motif qui soit suffixe du mot.
	On a donc la taille du plus grand prefixe-suffixe.
	*/
	public static int prefSuf(String m1,String m2){
		//Permet de couper m1 pour qu'il soit de la meme taille que m2.
		m1=m1.substring(0,m2.length());

		if(m1.equals(m2)){
			return m2.length();
		}

		else {
			return prefSuf(m1,m2.substring(1,m2.length()));
		}
	}


	//La fonction rechMotif permet de retouver un motif dans un texte
	public static void  rechMotif(String text,String motif,int[][]t,BufferedWriter s){
		
		int tab[][]=t;
		int etat=0;
		int it = 0;

		//On parcourt les differentes lettres du textes
		for (int i=0;i<text.length() ; i++ ) {
			int indice=-1;	
			//on attribue l'indice correspondant a la lettre du text pour retrouver l'etat suivant dans le tableau de transition
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
			if(text.charAt(i)!='\n'){
				it++;
			}
			// si la lettre est A,C,G,T 
			if(indice != -1){
				//recupere l'etat suivant 
				etat = tab[etat][indice];
				//si je suis arrive a l'etat finale 
				if (etat==motif.length()){
					try{
						//On ecrit le motif qu'on a trouve et sa place dans le fichier
						s.write( motif + " : "+(it-(motif.length()-1))+"\n");
					}
					catch(IOException e){ System.out.println(e);}
					
				}
			}
		}
	}



	//Lecture du fichier de chromosome et recherche des motifs.
	public static void chromo(String chemin, String sortie ){
		try{
			//Chargement du fichier chromosome
			BufferedWriter out = new BufferedWriter(new FileWriter(sortie));
			String seqEntier = new String(Files.readAllBytes(Paths.get("chr22.fa")));

			//Suppression des premiers caracteres parasites du fichier de chromosome.
			while(seqEntier.charAt(0)!='A'&& seqEntier.charAt(0)!='C'&& seqEntier.charAt(0)!='G'&& seqEntier.charAt(0)!='T'){
				seqEntier=seqEntier.substring(1);
			}
			//Mise en majuscule de tous les caracteres du texte.
			seqEntier=seqEntier.toUpperCase();

			//chargement du fichier des motifs
			BufferedReader motif = new BufferedReader(new FileReader(chemin));
			String lineMotif;

			//Tant qu'on lit encore des lignes de motifs dans le fichier, on continue la recherche.
			while ((lineMotif = motif.readLine()) != null) {
				//On applique la recherche
	   			int tab[][]=delta(lineMotif,"ACGT");
	   			rechMotif(seqEntier,lineMotif,tab,out);
			}
			//On ferme les fichiers qu'on avait ouvert en lecture.
			motif.close();
			out.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}


	public static void main(String[]args){
		chromo("tags.txt","sortie.txt");
	}
}