import java.io.*;
import java.util.*;

class Face{

public static void main(String args[]){
	
	FileInputStream in = null;
	File inputFile;
	try {
	inputFile = new File("facedatatrainlabels");
	in = new FileInputStream(inputFile);
	} 
	catch (Exception e) {
		System.err.println( "Unable to open data file: \n" + e);
		return;
	}

	BufferedReader bin = new BufferedReader(new InputStreamReader(in));
	String input;

	int labelstrain[] = new int[451];
	int i = 0, j=0;
	while(true){
		try{
		input = bin.readLine();
		if (input == null) break;
		if(input.equals("1")) labelstrain[i++] = 1;
		else labelstrain[i++] = 0;
		}
		catch(Exception e){}
	}
	
	try {
	inputFile = new File("facedatatrain");
	in = new FileInputStream(inputFile);
	} 
	catch (Exception e) {
		System.err.println( "Unable to open data file: \n" + e);
		return;
	}

	bin = new BufferedReader(new InputStreamReader(in));
	int value = 0;
	int label;
	int spaceface[][] = new int[70][60];
	int spacenoface[][] = new int[70][60];
	int hashface[][] = new int[70][60];
	int hashnoface[][] = new int[70][60];
	double spacefaceprob[][] = new double[70][60];
	double hashfaceprob[][] = new double[70][60];
	double spacenofaceprob[][] = new double[70][60];
	double hashnofaceprob[][] = new double[70][60];
	
	for(i=0;i<70;i++){
		for(j=0;j<60;j++){
			spaceface[i][j] = 0;
			spacenoface[i][j] = 0;
			hashface[i][j] = 0;
			hashnoface[i][j] = 0;
		}
	}
	
	while(value < 451){
		label = labelstrain[value];
		int count = 0;
		while(count < 70){
			try{
			input = bin.readLine();
			if(input == null) break;
			
			int length=0;
			if(label==1){
				while(length < 60){
					if(input.charAt(length) == ' ') spaceface[count][length]++;
					else hashface[count][length]++; 
					length++;
				}
			}
			else {
				while(length < 60){
					if(input.charAt(length) == ' ') spacenoface[count][length]++;
					else hashnoface[count][length]++; 
					length++;
				}
			}
			count++;
			}
			catch(Exception e){}
		}
		value++;
	}
	for(i=0;i<70;i++){
		for(j=0;j<60;j++){
			spacefaceprob[i][j] = Math.log(spaceface[i][j]/(double)(spaceface[i][j]+spacenoface[i][j]));
			spacenofaceprob[i][j] = Math.log(1 - spaceface[i][j]/(double)(spaceface[i][j]+spacenoface[i][j]));
			hashfaceprob[i][j] = Math.log(hashface[i][j]/(double)(hashface[i][j]+hashnoface[i][j]));
			hashnofaceprob[i][j] = Math.log(1 - hashface[i][j]/(double)(hashface[i][j]+hashnoface[i][j]));
			
		}
	}
	int labelstest[] = new int[150];
	int predicted[] = new int[150];
	int tp=0, tn=0, fp=0, fn=0;
	
	try {
	inputFile = new File("facedatatestlabels");
	in = new FileInputStream(inputFile);
	} 
	catch (Exception e) {
		System.err.println( "Unable to open data file: \n" + e);
		return;
	}

	bin = new BufferedReader(new InputStreamReader(in));
	i=0;
	while(true){
		try{
		input = bin.readLine();
		if (input == null) break;
		if(input.equals("1")) labelstest[i++] = 1;
		else labelstest[i++] = 0;
		}
		catch(Exception e){}
	}
	double faceprob = 0, nofaceprob = 0;
	try {
	inputFile = new File("facedatatest");
	in = new FileInputStream(inputFile);
	} 
	catch (Exception e) {
		System.err.println( "Unable to open data file: \n" + e);
		return;
	}

	bin = new BufferedReader(new InputStreamReader(in));
	
	value = 0;
	while(value < 150){
		label = labelstest[value];
		int count = 0;
		faceprob = 0;
		nofaceprob = 0;
		while(count < 70){
			try{
			input = bin.readLine();
			if(input == null) break;
			
			int length=0;
			while(length < 60){
				if(input.charAt(length) == ' '){ 
					faceprob = faceprob + spacefaceprob[count][length];
					nofaceprob = nofaceprob + spacenofaceprob[count][length];
				}
				else{ 
					faceprob = faceprob + hashfaceprob[count][length];
					nofaceprob = nofaceprob + hashnofaceprob[count][length];
				}
				length++;
			}
			count++;
			}
			catch(Exception e){}
		}
		if(faceprob > -3083){ 
			predicted[value] = 1;
			if(labelstest[value]==1) tp++;
			else{ 
				fp++;
				System.out.println("fp" + value);
			}
		}
		else{ 
			predicted[value] = 0;
			if(labelstest[value]==0) tn++;
			else{ 
				fn++;
				System.out.println("fn" + value);
			}
		}
		value++;
	}
	
	System.out.println(tp + " " + tn + " " + fp + " " + fn);

}
}