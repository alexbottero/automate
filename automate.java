class automate{

	public static int prefSuf(String m1,String m2){
		m1=m1.substring(0,m2.length());
		if(m1.equals(m2)){
			return m2.length();
		}
		if(m2.length()==0){
			return 0;
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
				tabDelta[i][j]=prefSuf(m,motCourant+lettre.charAt(j));
			}
			motCourant=motCourant+m.charAt(i);
		}
		return tabDelta;
	}

	public static void main(String[]args){
		rechMotif("agagacaaaaaa","aa");


	}
	public static void rechMotif(String text,String motif){
		int tab[][]= delta(motif,"acgt");
		for (int i=0;i<tab.length;i++ ) {
			for (int j=0;j<tab[0].length; j++) {
				System.out.print(tab[i][j]);
			}
		System.out.println("");
		}
		int indice=-1;	
		int etat=0;

		for (int i=0;i<text.length() ;i++ ) {

			if(text.charAt(i)=='a'){
				indice=0;
			}
			if(text.charAt(i)=='c'){
				indice=1;
			}
			if(text.charAt(i)=='g'){
				indice=2;
			}
			if(text.charAt(i)=='t'){
				indice=3;
			}
			etat =tab[etat][indice];
			if (etat==motif.length()){
				System.out.println("mot trouvé a l'indice "+(i-(motif.length()-1)));
			}

		}
	}
}