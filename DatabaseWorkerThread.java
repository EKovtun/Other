import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DatabaseWorkerThread extends Thread{
    private static final int NUMBER_OF_THREADS = 5;
    private static final int WORK_TIME_SECS = 10;

    private static final Random random = new Random();
    private static boolean greenLight = false;
    private static long generalNumberOfCompletedRequests = 0;

    private static synchronized void setGreenLight () { greenLight = true; }
    private static synchronized boolean getGreenLight () { return greenLight; }
    private static synchronized void addCompletedRequests(long numberOfRequests) {
        generalNumberOfCompletedRequests += numberOfRequests;
    }

    public static void main(String[] args) {
        Thread[] threadsList = new Thread[NUMBER_OF_THREADS];

        try {
            for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
                var newThread = new DatabaseWorkerThread();
                threadsList[i] = newThread;
                newThread.start();
            }

            setGreenLight();

            for (int i = 0; i < NUMBER_OF_THREADS; ++i)
                threadsList[i].join();

            System.out.printf("Completed requests: %d%n", generalNumberOfCompletedRequests);
            System.out.printf("Requests/Secs: %d%n", generalNumberOfCompletedRequests / WORK_TIME_SECS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Connection connection;
    private final long timeLimitMses = WORK_TIME_SECS * 1000L;
    private long numberOfCompletedRequests = 0;

    private ArrayList<String> namesList = new ArrayList<>(Arrays.asList("Edik", "Vasya", "Stepan", "Anna", "Ksenia", "Jamis", "Bob", "Ludovic"));
    private final int namesListSize;

    public DatabaseWorkerThread() throws SQLException {
        super();
        connection = DriverManager.getConnection("jdbc:postgresql://[::1]:5432/credit", "postgres", "123");
        namesListSize = namesList.size();
    }

    public void run() {
        while(!getGreenLight())
            Thread.yield();

        try {
            long startTime = System.currentTimeMillis();
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM persons WHERE firstName=? AND secondName=?");

            do {
                statement.setString(1, namesList.get(random.nextInt(namesListSize)));
                statement.setString(2, namesList.get(random.nextInt(namesListSize)));
                var result = statement.executeQuery();
                if (result.next()) ++numberOfCompletedRequests;
            } while (System.currentTimeMillis() - startTime < timeLimitMses);

            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        addCompletedRequests(numberOfCompletedRequests);
    }
}
