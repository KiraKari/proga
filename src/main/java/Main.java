public class Main {

    public static void main(String[] args){
        Input input = new Input();
        while(!input.getFlag()){
            Thread.yield(); //говорит потоку выполняться дальше
        }
        Algorithm algorithm = new Algorithm();
        algorithm.start();
    }

}
