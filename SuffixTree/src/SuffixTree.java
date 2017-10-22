import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixTree {
    
    private int lastIndex = 0;
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding 
    // substrings of the text) in any order.
    public List<String> computeSuffixTreeEdges(ArrayList<String> text) {
        ArrayList<String> result = new ArrayList<String>();
        TrieNode rootNode = new TrieNode((char)0, 0, result);
        for(int i=0; i < text.size(); i++){
            String pattern = text.get(i);
            //It's not working because it's starting at the top and 
            //there is nothing there. Have to go to the next one
            //somehow.
            rootNode.checkEdges(pattern, result);
        }
        return result;
    }

    private class TrieNode{
        public char edgeLetter;
        public int nodeIndex;
        public int parentIndex;
        

        private List<TrieNode> edges = new ArrayList<>();
        public TrieNode(char edgeLetter, int parentIndex, ArrayList<String> result){  // add argument result list
            this.edgeLetter = edgeLetter;           
            nodeIndex = lastIndex++;
            if(nodeIndex>0){
                result.add(parentIndex + "->" + nodeIndex + ":" + edgeLetter);
            }
        }
        public TrieNode addEdge(char edgeLetter, ArrayList<String> result){
            edges.add(new TrieNode(edgeLetter, this.nodeIndex, result));
            return this;
        }
        public void checkEdges(String pattern, ArrayList<String> result){
            boolean newEdge = true;
            if(pattern.length()>0){
                for(TrieNode edge : edges){
                    if(edge.edgeLetter == pattern.charAt(0)){
                        edge.checkEdges(pattern.substring(1), result);
                        newEdge = false;
                        break;
                    }
                }

                if(newEdge){
                    TrieNode newNode = addEdge(pattern.charAt(0), result);
                   this.edges.get(edges.size()-1).checkEdges(pattern.substring(1), result);
                }
            }
        }
    }

    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void print(List<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        ArrayList<String> text = new ArrayList<>();
        for(int i=0; i<n; i++){
            text.add(scanner.next());
        }
        List<String> edges = computeSuffixTreeEdges(text);
        print(edges);
    }
}
