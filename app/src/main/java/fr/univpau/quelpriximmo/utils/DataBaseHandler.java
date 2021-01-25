package fr.univpau.quelpriximmo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationManager;

import java.util.ArrayList;
import java.util.List;

import fr.univpau.quelpriximmo.Models.ImmoModel;

public class DataBaseHandler extends SQLiteOpenHelper {
    LocationManager lm = null;
    private static final int VERSION=1;
    private static final String NAME = "DB_Biens";
    private static final String IMMO_TABLE = "immo_table";
    private static final String ID = "id";
    private static final String TYPE_BIEN ="type_bien";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String DISTANCE = "distance";
    private static final String ADRESSE = "adresse";
    private static final String NB_PIECES = "nb_pieces";
    private static final String PRIX = "prix";
    private static final String CREATE_IMMO_TABLE = "CREATE TABLE "+IMMO_TABLE+"("+ID+ "INTEGER PRIMARY KEY AUTOINCREMENT, "+ TYPE_BIEN + " TEXT, "+ADRESSE+ "TEXT, "+NB_PIECES+" INTEGER, "+LONGITUDE+" REAL, "
                                +LATITUDE+" REAL, "+DISTANCE+" REAL, "+PRIX+" REAL)";
    private SQLiteDatabase db;

    public DataBaseHandler(Context ctx){
        super(ctx, NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_IMMO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+IMMO_TABLE);
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void flush(){
        db.execSQL("DROP TABLE IF EXISTS "+IMMO_TABLE);
    }

    public void create(){
        this.onCreate(this.db);
    }

    public void insert(String type_bien, int nb_pieces, double prix, String adresse, double longitude, double latitude, Location usrPos){
        ContentValues cv = new ContentValues();
        cv.put(TYPE_BIEN, type_bien);
        cv.put(NB_PIECES, nb_pieces);
        cv.put(PRIX, prix);
        cv.put(ADRESSE, adresse);
        cv.put(LONGITUDE, longitude);
        cv.put(LATITUDE, latitude);
        cv.put(DISTANCE, Math.abs((usrPos.getLatitude() - latitude)/(usrPos.getLongitude()-longitude)));
        db.insert(IMMO_TABLE, null, cv);
    }

    public List<ImmoModel> getImmo(String type_bien, double distance, int nb_pieces, int prix_min){
        List<ImmoModel> immoList = new ArrayList<>();
        Cursor cur = null;

        String selection = "distance <=? ";
        ArrayList<String> selectionArgs = new ArrayList<String>();
        selectionArgs.add(Double.toString(distance));

        if(!type_bien.equals("Non specifie")){
            selection = selection + "AND type_bien =? ";
            selectionArgs.add(type_bien);
        }

        if(nb_pieces != -1 && nb_pieces  < 4){
            selection = selection + "AND nb_pieces =? ";
            selectionArgs.add(Integer.toString(nb_pieces));
        }

        if(nb_pieces > 4){
            selection = selection+"AND nb_pieces >=? ";
            selectionArgs.add(Integer.toString(4));
        }

        if(prix_min != -1){
            selection = selection+"AND prix >=? ";
            selectionArgs.add(Integer.toString(prix_min));
        }


        db.beginTransaction();
        try{
            cur = db.query(false, IMMO_TABLE, null, selection, selectionArgs.toArray(new String[selectionArgs.size()]), null, null, null, null);
            //si le curseur est pas vide
            if(cur != null){
                //si le curseur ets ouvert on le parcours en entier
                if(cur.moveToFirst()){
                    do{
                        ImmoModel immo = new ImmoModel();
                        immo.setID(cur.getInt(cur.getColumnIndex(ID)));
                        immo.setAdress(cur.getString(cur.getColumnIndex(ADRESSE)));
                        immo.setPrix(cur.getDouble(cur.getColumnIndex(PRIX)));
                        immo.setNb_pieces(cur.getInt(cur.getColumnIndex(NB_PIECES)));
                        immo.setDistance(cur.getDouble(cur.getColumnIndex(DISTANCE)));
                        immo.setCoords(cur.getDouble(cur.getColumnIndex(LONGITUDE)), cur.getDouble(cur.getColumnIndex(LATITUDE)));
                    }while(cur.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cur.close();
        }
        return immoList;
    }
}
