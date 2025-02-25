import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strings = new ArrayList<String>();
    ArrayList<String> searched = new ArrayList<String>();

    public String handleRequest(URI url) {
        
        if (url.getPath().equals("/")) {
            return String.format("Search Engine!");
            
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    strings.add(parameters[1]);
                    return String.format("Added: %s", parameters[1]);
                }
            }
            if (url.getPath().contains("/search")) {
                searched.clear();
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    for (int i = 0; i < strings.size(); i++) {
                    if (strings.get(i).contains(parameters[1])) {
                            searched.add(strings.get(i));
                        }
                    }
                    return String.format(Arrays.toString(searched.toArray()));

                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }
        
        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

