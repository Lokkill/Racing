package our.company;

import java.util.concurrent.*;

public class Car implements Runnable{
    private static int CARS_COUNT;
    private static boolean hasWinner;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.cl.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startRace() {

        ExecutorService ex = Executors.newFixedThreadPool(1);
        for (int i = 0; i < race.getStages().size(); i++) {
            int finalI = i;
            ex.execute(new Thread(() -> {
                race.getStages().get(finalI).go(this);
            }));

        }
        ex.shutdown();
        new Thread(() -> {
            while (true){
                if (ex.isTerminated()){
                    MainClass.cl.countDown();
                    if (!hasWinner){
                        System.out.println(this.name + " - WIN");
                        hasWinner = true;
                    }
                    break;
                }
            }
        }).start();

    }
}
