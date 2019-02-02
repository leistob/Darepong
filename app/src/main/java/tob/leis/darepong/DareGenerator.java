package tob.leis.darepong;

import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DareGenerator {

    private static final String LOG_TAG = "DareGenerator";

    private static final String FILEPATH_OFFLINE = "dares_offline.csv";
    private static final String FILEPATH_ONLINE = "dares_online.csv";
    private static final char SEPARATOR_VSC = ';';

    private static final int LEVEL_5 = 5, LEVEL_4 = 4, LEVEL_3 = 3, LEVEL_2 = 2, LEVEL_1 = 1;

    private List<List<String>> dareList;

    DareGenerator(Context context) {
        dareList = new ArrayList<>();
        loadCSVFromAsset(context);
    }

    private void loadCSVFromAsset(Context context) {

        //TODO switch between available up-to-date dare list version and offline file

        try {
            InputStream is = context.getAssets().open(FILEPATH_OFFLINE);

            CSVParser parser = new CSVParserBuilder().withSeparator(SEPARATOR_VSC).build();
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is))
                    .withSkipLines(1)
                    .withCSVParser(parser)
                    .build();

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                dareList.add(Arrays.asList(nextLine));
            }


            for(List<String> l : dareList) {
                System.out.println(l.toString());
                System.out.println("|");
            }

            //List<Dares> dares = new CsvToBeanBuilder(new InputStreamReader(is)).withType(Dares.class).withSeparator(SEPARATOR_VSC).build().parse();
            //Log.d(LOG_TAG, dares.toString());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String loadCSVFromAsset(Context context, String s) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(FILEPATH_OFFLINE);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String getRandomDare() {
        String dare = null;

        int numberOfDares = dareList.size();

        int random = new Random().nextInt(numberOfDares);

        return dareList.get(random).get(0);
    }

    public String getLevelDare(int level) {
        String dare = null;

        switch (level) {
            case LEVEL_1:
                break;
            case LEVEL_2:
                break;
            case LEVEL_3:
                break;
            case LEVEL_4:
                break;
            case LEVEL_5:
                break;
            default:
                break;
        }

        return dare;
    }

    public class Dares {


        private String dare;


        private String level;

        public Dares(String dare, String level) {
            this.dare = dare;
            this.level = level;
        }

        public void setDare(String dare) {
            this.dare = dare;
        }

        public String getDare() {
            return this.dare;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLevel() {
            return this.level;
        }
    }
}
