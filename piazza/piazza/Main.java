package piazza;

public class Main {

    public static void main(String[] args) {
    
        PiazzaCtrl piazzaCtrl = new PiazzaCtrl();
        
        //user case 1
        piazzaCtrl.logIn("beate.kanutte@gmail.com", "HeiHei");
        
        //user case 2
        piazzaCtrl.createFirstPostInThread("Eksamens 2020", "Kan noen forklare oppgave 2, WAL?", "Question", "Exam");
        
        //user case 3
        piazzaCtrl.logIn("hallvar@gmail.com", "HeiHei");
        piazzaCtrl.createReply("Nei, det maa du finne ut selv", 1);
        
        //user case 4
        piazzaCtrl.searchForKeyword("WAL");
        
        //user case 5
        piazzaCtrl.viewStatistics();
    }
}
