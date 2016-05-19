package fr.game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface Constants {
	// liste des id des cases qui sont plates ... fait a la main c est long !!!
	static public Set<Integer> flatsIds = new HashSet<Integer>(Arrays.asList(new Integer[]{1,4,5,8,10,14,19,20,21,30,34,40,41,42,43,47,54,63,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,90,91,92,94,95,98,100,104,105,107,109,110,111,114}));
	// liste des id des cases qui sont de l'eau... fait a la main c est long !!!
	static public Set<Integer> waterIds = new HashSet<Integer>(Arrays.asList(new Integer[]{4,10,14,19,20,21,30,34,40,41,42,47,54,63,68,70,72,73,74,77,82,84,85,87,90,95,98,100}));
	// liste des id des cases qui sont des escaliers ... fait a la main c est MOINS long :)
	static public Set<Integer> stairsIds = new HashSet<Integer>(Arrays.asList(new Integer[]{16,45,46,60,113}));
	// liste des id des cases qui sont des arbre ... fait a la main c est MOINS long :)
	static public Set<Integer> treeIds = new HashSet<Integer>(Arrays.asList(new Integer[]{3,6,24}));
}
