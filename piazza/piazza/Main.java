package piazza;

public class Main {

    public static void main(String[] args) {
    
        PiazzaCtrl piazzaCtrl = new PiazzaCtrl();
        
        piazzaCtrl.logIn("beate.kanutte@gmail.com", "HeiHei");
        piazzaCtrl.createFirstPostInThread("Ny Thread", "Hei", "Question", "Exam");
        
        piazzaCtrl.logIn("hallvar@gmail.com", "HeiHei");
        piazzaCtrl.createReply("Svar", "Dette er et svar", 1);
        
        System.out.println(piazzaCtrl.searchForKeyword("Hei"));
        piazzaCtrl.viewStatistics();
    }
}
