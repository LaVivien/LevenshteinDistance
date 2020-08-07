
import java.util.*;

public class LevenshteinFinder {
	
	private String start;
	private String end;
	private TreeMap<String, TreeSet<String>> map = new TreeMap<>();
	private int distance=-1;
	private List<String> path= new ArrayList<String>();

	//constructor
	public LevenshteinFinder(String startWord, String stopWord, Set<String> dictionary) {
		if(startWord.length()!= stopWord.length())
			throw new IllegalArgumentException();
		this.start=startWord.toLowerCase();
		this.end=stopWord.toLowerCase();
		createNeighborMap(dictionary);
		distance = findDistance(start, end);
		findPath(start, end);
	}

	//create map of dictionary
	private void createNeighborMap(Set<String> dict) {
		for(String word: dict) {
			if(word.length()==start.length()){		
				TreeSet<String> set = map.getOrDefault(word, new TreeSet<String>()) ;
				for(String x: dict) {
					if(differentLetters(word, x)==1) {											
				    	set.add(x);			    	
					}
				}
				map.put(word, set);				
			}
		}
	}	
	
	private int differentLetters(String a, String b) {
		if(a.length()!=b.length())
			return -1;
		int count=0;
		for(int i=0;i<a.length();i++) {
			if(a.charAt(i)!=b.charAt(i))
				count++;
		}
		return count;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public String getPath() {
		if(path.size()<0)
			return "path is not available";
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<path.size()-1; i++) {
			sb.append(path.get(i)+"->");
		}
		if(path.size()<1)
			return "There is no path";
		sb.append(path.get(path.size()-1));
		return sb.toString();
	}
	
	//BFS
	public int findDistance(String startWord, String endWord) {	
		if(startWord.equals(endWord))
			return 0;
	    Set<String> q1 = new TreeSet<>();
	    Set<String> q2 = new TreeSet<>();
	    q1.add(startWord);
	    q2.add(endWord);
	    int steps = 0;    
	    Set<String> visited = new TreeSet<>();
	    while (!q1.isEmpty() && !q2.isEmpty()) {	       
	      if (q1.size() > q2.size()) {
	        Set<String> tmp = q1;
	        q1 = q2;
	        q2 = tmp;
	      }	      
	      Set<String> q = new TreeSet<>();		  
	      for (String w : q1) {       
	    	  Set<String> ne= map.get(w);
	    	  for(String t: ne) {     
	            if (q2.contains(t)) return steps+1 ; 
	            if (!visited.contains(t) ) {           
	 	           	q.add(t);
                    visited.add(t);
                }	            
	          } 	
	      }	      
	      q1 = q;
	      steps++;  
	    }
	    return -1;
	}
	
	//BFS
	private void findPath(String startWord, String endWord) {
		Queue<String> queue = new LinkedList<String>();
		Set<String> visited = new TreeSet<String>();
		Map<String, String> linkMap = new TreeMap<String, String>();
		queue.add(startWord);
		visited.add(startWord);
		while (!queue.isEmpty()) {
			String str = queue.poll();
			for (String word : map.get(str)) {
				if (word.equals(endWord)) {
					path.add(word);
					while (str != null) {
						path.add(str);
						str = linkMap.get(str);
					}
					return;
				}			
				if (!visited.contains(word)) {
					queue.add(word);
					visited.add(word); 
					linkMap.put(word, str);
				}	
			}
		}		
	}
}
