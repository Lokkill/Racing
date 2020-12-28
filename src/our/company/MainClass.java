package our.company;

import java.util.concurrent.*;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static CountDownLatch cl = new CountDownLatch(CARS_COUNT);
    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        ExecutorService es = Executors.newFixedThreadPool(CARS_COUNT);
        for (int i = 0; i < cars.length; i++) {
            es.execute(new Thread(cars[i]));
        }
        try {
            MainClass.cl.await();
        }catch (Exception e){
            e.printStackTrace();
        }
        cl = new CountDownLatch(CARS_COUNT);
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        for (Car car : cars){
            car.startRace();
        }

        try {
            cl.await();
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        es.shutdown();


    }
}
