package pt.ipg.gestortreinos;

import android.content.ContentProvider;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.BreakIterator;


public class TreinoContentProvider extends ContentProvider {
    private static final String AUTHORITY = "pt.ipg.books";

    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri TREINO_URI = Uri.withAppendedPath(BASE_URI, DBTableTreino.DATABASENAME_T);
    public static final Uri DIASSEMANA_URI = Uri.withAppendedPath(BASE_URI, DBTableDiasSemana.DATABASENAME_D);

    public static final int TREINO = 100;
    public static final int TREINO_ID = 101;
    private static final int DIASSEMANAS = 200;
    private static final int DIASSEMANAS_ID = 201;


    private static final String MULTIPLE_ITEMS = "vnd.android.cursor.dir";
    private static final String SINGLE_ITEM = "vnd.android.cursor.item";

    DBTreinoOpenHelper dbTreinoOpenHelper;

    private static UriMatcher getTreinosUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, "treino", TREINO);
        uriMatcher.addURI(AUTHORITY, "treino/#", TREINO_ID);

        uriMatcher.addURI(AUTHORITY, "DiasSemana", DIASSEMANAS);
        uriMatcher.addURI(AUTHORITY, "diasSemana/#", DIASSEMANAS_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        dbTreinoOpenHelper = new DBTreinoOpenHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbTreinoOpenHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        UriMatcher matcher = getTreinosUriMatcher();

        switch (matcher.match(uri)) {
            case TREINO:
                return new DBTableTreino(db).query(projection, selection, selectionArgs, null, null, sortOrder);

            case TREINO_ID:
                return new DBTableTreino(db).query(projection, selection, selectionArgs, null, null, sortOrder);

            case DIASSEMANAS:
                return new DBTableDiasSemana(db).query(projection, selection, selectionArgs, null, null, sortOrder);

            case DIASSEMANAS_ID:
                return new DBTableDiasSemana(db).query(projection, selection, selectionArgs, null, null, sortOrder);

            default:
                throw new UnsupportedOperationException("Uri Inválida" + uri);
        }
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        UriMatcher matcher = getTreinosUriMatcher();

        switch (matcher.match(uri)) {
            case TREINO:
                return MULTIPLE_ITEMS + "/" + AUTHORITY + "/" + DBTableTreino.DATABASENAME_T;

            case DIASSEMANAS:
                return MULTIPLE_ITEMS + "/" + AUTHORITY + "/" + DBTableDiasSemana.DATABASENAME_D;

            case TREINO_ID:
                return SINGLE_ITEM + "/" + AUTHORITY + "/" + DBTableTreino.DATABASENAME_T;

            case DIASSEMANAS_ID:
                return SINGLE_ITEM + "/" + AUTHORITY + "/" + DBTableDiasSemana.DATABASENAME_D;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbTreinoOpenHelper.getWritableDatabase();


        UriMatcher matcher = getTreinosUriMatcher();
        long id = -1;
        switch (matcher.match(uri)) {
            case TREINO:
                id = new DBTableTreino(db).insert(values);
                break;

            case DIASSEMANAS:
                id = new DBTableDiasSemana(db).insert(values);
                break;

            default:
                throw new UnsupportedOperationException("Uri Inválida" + uri);
        }

        if (id > 0) {
            notifyChanges(uri);
            return Uri.withAppendedPath(uri, Long.toString(id));
        } else {
            throw new SQLException("Não consegue inserir os dados");
        }

    }

    private void notifyChanges(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);

    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbTreinoOpenHelper.getWritableDatabase();

        UriMatcher matcher = getTreinosUriMatcher();

        String id = uri.getLastPathSegment();

        int linhas = 0;


        switch (matcher.match(uri)) {
            case TREINO:
                linhas = new DBTableTreino(db).delete(DBTableTreino._ID + "=?", new String[]{id});
                break;

            case DIASSEMANAS:
                linhas = new DBTableDiasSemana(db).delete(DBTableDiasSemana._ID + "=?", new String[]{id});
                break;

            default:
                throw new UnsupportedOperationException("Uri Inválida" + uri);
        }

        if (linhas > 0) {
            notifyChanges(uri);
        }
        return linhas;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbTreinoOpenHelper.getWritableDatabase();

        UriMatcher matcher = getTreinosUriMatcher();

        String id = uri.getLastPathSegment();

        int linhas = 0;
        switch (matcher.match(uri)) {
            case TREINO:
                linhas = new DBTableTreino(db).delete(DBTableTreino._ID + "=?", new String[]{id});
                break;

            case DIASSEMANAS:
                linhas = new DBTableDiasSemana(db).delete(DBTableDiasSemana._ID + "=?", new String[]{id});
                break;

            default:
                throw new UnsupportedOperationException("Uri Inválida" + uri);
        }

        if (linhas > 0) {
            notifyChanges(uri);
        }

        return linhas;
    }
}
