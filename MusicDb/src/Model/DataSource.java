package Model;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// created a new package and in that a new class for data base instead of main class
public class DataSource {
    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Ishita Sinha\\Desktop\\java\\MusicDb\\" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;


    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "i_d";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3; // in query artist instead of passing the select start from and then the table artists as were doing we build up a query string

    public static final String QUERY_ALBUMS_BY_ARTIST_START =
            "SELECT " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME + " FROM " + TABLE_ALBUMS +
                    " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST +
                    " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
                    " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " =\"";
    public static final String QUERY_ALBUMS_BY_ARTIST_SORT =
            " ORDER BY " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";

    public static final String QUERY_ARTIST_FOR_SONG_START =
            "SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
                    TABLE_ARTISTS + "." + COLUMN_ALBUM_NAME + ", " +
                    TABLE_SONGS + "." + COLUMN_SONG_TRACK + " FROM " + TABLE_SONGS +
                    " INNER JOIN " + "." + TABLE_ALBUMS + " ON " +
                    TABLE_SONGS + "." + COLUMN_SONG_ALBUM + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
                    " INNER JOIN " + TABLE_ARTISTS + " ON " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
                    " WHERE " + TABLE_SONGS + "." + COLUMN_SONG_TITLE + " =\"";
    public static final String QUERY_ARTIST_FOR_SONG_SORT =
            " ORDER BY " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";
    public static final String TABLE_ARTIST_SONG_VIEW = "artist_list";
    //CREATE VIEW OF NOT EXISTS artist_list AS SELECT artists.name, albums.name AS album,
    // songs.track, songs.title FROM songs INNER JOIN albums ON songs.album = album._id
    //INNER JOIN artists ON albums.artist = artists._id ORDER BY artists.name
    // albums.name, songs.track
    public static final String CREATE_ARTIST_FOR_SONG_VIEW = " CREATE VIEW IF NOT EXISTS " +
            TABLE_ARTIST_SONG_VIEW + " AS SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
            TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " AS " + COLUMN_SONG_ALBUM + ", " +
            TABLE_SONGS + "." + COLUMN_SONG_TRACK + ", " + TABLE_SONGS + "." + COLUMN_SONG_TITLE +
            " FROM " + TABLE_SONGS +
            " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS +
            "." + COLUMN_SONG_ALBUM + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
            " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST +
            " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
            " ORDER BY " +
            TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
            TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
            TABLE_SONGS + "." + COLUMN_SONG_TRACK;
    public static final String QUERY_VIEW_SONG_INFO = "SELECT " + COLUMN_ARTIST_NAME + ", " +
            COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + " FROM " + TABLE_ARTIST_SONG_VIEW +
            " WHERE " + COLUMN_SONG_TITLE + " =\"";

    public static final String QUERY_VIEW_SONG_INFO_PREP = "SELECT " + COLUMN_ARTIST_NAME + ", " +
            COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + " FROM " + TABLE_ARTIST_SONG_VIEW +
            " WHERE " + COLUMN_SONG_TITLE + " =?"; // PREPARED STATEMENT
    //SELECT name, album,track FROM artist_list WHERE title =?
    public static final String INSERT_ARTIST = "INSERT INTO " + TABLE_ARTISTS +
            '(' + COLUMN_ARTIST_NAME + ") VALUES(?)"; //TRANSACTIONS
    public static final String INSERT_ALBUMS = "INSERT INTO " + TABLE_ALBUMS +
            '(' + COLUMN_ALBUM_NAME + ", " + COLUMN_ALBUM_ARTIST + ") VALUES(?, ?)";
    public static final String INSERT_SONGS = "INSERT INTO " + TABLE_SONGS +
            '(' + COLUMN_ALBUM_NAME + ", " + COLUMN_SONG_TITLE + ", " + ", " + COLUMN_SONG_ALBUM +
            ") VALUES(?, ?, ?)";

    // we also need to declare an instance variable for the prepared statement and thats we only want to creatte it once we dont want to create every time a query because we only want it ti be pre compiled once
    // you might be tempted to have only one string for name and ine for id that we use for every table but its better to have one string constant per table that wau we can change the name of any column in a table
    // method to connect to data base insted in main we can connect form here in datasource class
    //its better to have one string contant every table that way we cam change the name of any column in a table without worrying about impacting on other tables
    public static final String QUERY_ARTIST = "SELECT " + COLUMN_ARTIST_ID + " FROM " +
            TABLE_ARTISTS + " WHERE " + COLUMN_ARTIST_NAME + " = ?";
    public static final String QUERY_ALBUM = "SELECT " + COLUMN_ALBUM_ID + " FROM " +
            TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME + " = ?";

    private Connection conn;

    private PreparedStatement querySongInfoView;
    private PreparedStatement insertIntoArtists;
    private PreparedStatement insertIntoAlbums;
    private PreparedStatement insertIntoSongs;

    private PreparedStatement queryArtists;
    private PreparedStatement queryAlbum;


    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            querySongInfoView = conn.prepareStatement(QUERY_VIEW_SONG_INFO_PREP);
            insertIntoArtists = conn.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS); // ONCE WE DO THAT WELL BE ABLE TO GET KEYS FROM THE PREPARED STATEMENT OBJECT
            insertIntoAlbums = conn.prepareStatement(INSERT_ALBUMS, Statement.RETURN_GENERATED_KEYS); // ONCE WE DO THAT WELL BE ABLE TO GET KEYS FROM THE PREPARED STATEMENT OBJECT
            insertIntoSongs = conn.prepareStatement(INSERT_SONGS); // ONCE WE DO THAT WELL BE ABLE TO GET KEYS FROM THE PREPARED STATEMENT OBJECT
            queryArtists = conn.prepareStatement(QUERY_ARTIST);
            queryAlbum = conn.prepareStatement(QUERY_ALBUM);

            return true;
        } catch (SQLException e) {
            System.out.println("couldn't connect to database" + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (querySongInfoView != null) { // order is importgant we cant close the connection first because we need to open connection to close statements
                querySongInfoView.close();
            }// we close resources in reverse order in which they are opened
            if (insertIntoArtists != null) {
                insertIntoArtists.close(); // transaction
            }
            if (insertIntoAlbums != null) {
                insertIntoAlbums.close();
            }
            if (insertIntoSongs != null) {
                insertIntoSongs.close();
            }
            if (queryArtists != null) {
                queryArtists.close();
            }
            if (queryAlbum != null) {
                queryAlbum.close();
            }
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            System.out.println("couldn't close connection " + e.getMessage());
        }
    }

    public List<Artist> queryArtists(int sortOrder) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_ARTISTS);
        if (sortOrder != ORDER_BY_NONE) {
            sb.append(" ORDER BY ");
            sb.append(COLUMN_ARTIST_NAME);
            sb.append(" COLLATE NOCASE ");
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }
//        Statement statement = null;
//        ResultSet results = null; remove this declaration after resources in try block
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString()))// change after sb code ("SELECT * FROM " + TABLE_ARTISTS)) // return all the artis records with all column values

        {
//            statement=conn.createStatement(); // remove these after resources in tyr
//            results = statement.executeQuery("SELECT * FROM " + TABLE_ARTISTS); // return all the artis records with all column values
            List<Artist> artists = new ArrayList<>();
            while (results.next()) {
                Artist artist = new Artist(); //and for each record create new artist object
                artist.setId(results.getInt(INDEX_ARTIST_ID));// CHANGE IT AFTER INTRODUCING INDEX IN CODE(COLUMN_ARTIST_ID)); // we used result set getter methods to get the values from the record and set them to the artist instance and then we add the instance to the list once we are done looping we return the list to the caller
                artist.setName(results.getString(INDEX_ARTIST_NAME));//(COLUMN_ARTIST_NAME));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
//        }finally { // we can get rid of entire finally clause after resourses in tyr
//            try{
//                if(results!=null){
//                    results.close();
//                }
//            }catch (SQLException e){
//                System.out.println("error closing resultset " + e.getMessage());
//            }
//            try{
//                if(statement !=null){
//                    statement.close();
//                }
//            }catch(SQLException e){
//                System.out.println("error closing statement "+ e.getMessage());
//            }// we got two catch method because either of those closes could actuallt catch or could throw the sql exception
//        }
        }
    }

    public List<String> queryAlbumsForArtist(String artistName, int sortOrder) {
        //SELECT albums.name FROM albums INNER JOIN artists ON albums.artist = artists._id WHERE artists.name = "Carole King" ORDER BY albums.name COLLATE NOCASE ASC
        StringBuilder sb = new StringBuilder(QUERY_ALBUMS_BY_ARTIST_START);//remove this after creating constants("SELECT ");
//        sb.append(TABLE_ALBUMS);
//        sb.append('.');
//        sb.append(COLUMN_ALBUM_NAME);
//        sb.append(" FROM ");
//        sb.append(TABLE_ALBUMS);
//        sb.append(" INNER JOIN ");
//        sb.append(TABLE_ARTISTS);
//        sb.append(" ON ");
//        sb.append(TABLE_ALBUMS);
//        sb.append('.');
//        sb.append(COLUMN_ALBUM_ARTIST);
//        sb.append(" = ");
//        sb.append(TABLE_ARTISTS);
//        sb.append('.');
//        sb.append(COLUMN_ARTIST_ID);
//        sb.append(" WHERE ");
//        sb.append(TABLE_ARTISTS);
//        sb.append('.');
//        sb.append(COLUMN_ARTIST_NAME);
//        sb.append(" =\"");
        sb.append(artistName);
        sb.append("\"");

        if (sortOrder != ORDER_BY_NONE) {
            sb.append(QUERY_ALBUMS_BY_ARTIST_SORT);
//            sb.append(" ORDER BY "); // CAN REMOVE AFTER ADDING CONTANTS TO CODE
//            sb.append(TABLE_ALBUMS);
//            sb.append('.');
//            sb.append(COLUMN_ALBUM_NAME);
//            sb.append(" COLLATE NOCASE ");
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");

            }

        }
        System.out.println("SQL statement =" + sb.toString());
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<String> albums = new ArrayList<>();
            while (results.next()) {
                albums.add(results.getString(1));
            }
            return albums;
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }


    }

    public List<SongArtist> queryArtistForSong(String songName, int sortOrder) {
        StringBuilder sb = new StringBuilder(QUERY_ARTIST_FOR_SONG_START);
        sb.append(songName);
        sb.append("\"");

        if (sortOrder != ORDER_BY_NONE) {
            sb.append(QUERY_ARTIST_FOR_SONG_SORT);
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }
        System.out.println(" SQL Statement " + sb.toString());
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<SongArtist> songArtists = new ArrayList<>();
            while (results.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(results.getString(1));
                songArtist.setAlbumName(results.getString(2));
                songArtist.setTrack(results.getInt(3));
                songArtist.add(songArtist);
            }
            return songArtists;
        } catch (SQLException e) {
            System.out.println("query failed " + e.getMessage());
            return null;
        }
    }

    public void querySongsMetadata() {
        String sql = "SELECT * FROM " + TABLE_SONGS;
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery()) {
            ResultSetMetaData meta = results.getMetaData();
            int numColumns = meta.getColumnCount();
            for (int i = 1; i <= numColumns; i++) {
                System.out.format(" column %d in the songs table is name %s\n",
                        i.meta.getColumnName(i));
            }
        } catch (SQLException e) {
            System.out.println(" query failed " + e.getMessage());
        } //  using the result set metadata we can get information such as colum name and types and their attributes in other words now whether they are nullable etc
    }

    public int getCount(String table) {
        String sql = "SELECT COUNT(*),As count FROM " + table;
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sql)) {

            int count = results.getInt("count");
            int min = results.getInt("min_id");
            System.out.format("Count = %d\n", count);
            return count;
        } catch (SQLException e) {
            System.out.println("query failed " + e.getMessage());
            return -1;
        }

    }

    public boolean createViewForSongArtists() {
        try (Statement statement = conn.createStatement()) {
            statement.execute(CREATE_ARTIST_FOR_SONG_VIEW));


            return true;
        } catch (SQLException e) {
            System.out.println(" crete view query failed " + e.getMessage());
            return false;
        }
    }

    public List<SongArtist> querySongInfoView(String title) {
//        StringBuilder sb = new StringBuilder(QUERY_VIEW_SONG_INFO);
//        sb.append(title);
//        sb.append("\""); // we dont need string builder after prepared statements
//        System.out.println(sb.toString());

        try //(Statement statement = conn.createStatement();
        // ResultSet results = statement.executeQuery(sb.toString()))  // NO USE OF THIS AFTER THE PREPARED STATEMENTS
        {
            querySongInfoView.setString(1, title);
            ResultSet results = querySongInfoView.executeQuery();
            List<SongArtist> songArtists = new ArrayList<>();
            while (results.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(results.getString(1));
                songArtist.setAlbumName(results.getString(2));
                songArtist.setTrack(results.getInt(3));
                songArtist.add(songArtist);
            }
            return songArtists;
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }
    }

    // insert methods transactions

    private int insertArtist(String name) throws SQLException {
        queryArtists.setString(1, name);
        ResultSet results = queryArtist.executeQuery(); // queryint the artist table if the artist already exists if it does then we return the id that we retrieve from the results set and return the value beacuuse we dont no longer need to insert anything because we found artist on file
        if (results.next()) {
            return results.getInt(1);
        } else {
            // insert the artist cause not in file
            insertIntoArtists.setString(1, name);
            int affectedRows = insertIntoArtists.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldnt insert artist ");
            }

            ResultSet generatedKeys = insertIntoArtists.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("couldnt get _id for the artist"); // since we are inserting a single row we expect only one record to be returned if not then something wrong sql exception
            }
        }
    }

    private int insertAlbum(String name, int artistId) throws SQLException {
        queryAlbum.setString(1, name);
        ResultSet results = queryAlbum.executeQuery(); // queryint the artist table if the artist already exists if it does then we return the id that we retrieve from the results set and return the value beacuuse we dont no longer need to insert anything because we found artist on file
        if (results.next()) {
            return results.getInt(1);// if we give wrong col no this will show error java but the db will be updated
        } else {
            // insert the album cause not in file
            insertIntoAlbums.setString(1, name);
            insertIntoAlbums.setInt(2, artistId);
            int affectedRows = insertIntoAlbums.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldnt insert albums ");
            }

            ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("couldnt get _id for the albums"); // since we are inserting a single row we expect only one record to be returned if not then something wrong sql exception
            }
        }
    }

    public void insertSongs(String title, String artist, String album, int track) {
        try {
            conn.setAutoCommit(false);
            int artistId = insertArtist(artist);
            int albumId = insertAlbum(album, artistId);
            insertIntoSongs.setInt(1, track);
            insertIntoSongs.setString(2, title);
            insertIntoSongs.setInt(3, albumId);
//            So we start off by inserting the artist
//
//            and then we insert the album using the artist id
//
//            that was returned from the insert artist method.
//
//            Once we get to that point, we can now insert the song.
//
//                    Now we do that by setting the values
//
//            and insert into a song's prepared statement, these three lines here.
//
//            And then we actually call the execute update to check that only one row was affected.
//            We've inserted the song and all its associated information.
//
//            So we want to commit the changes, and we're doing that on this line.
//
//            And once we've done that that also ends the transaction.
//
//            Now if something goes wrong and an exception is thrown,
//
//            we're calling this connection dot rollback down here.
//
//            And that's going to back out any changes we've made since starting the transaction.
//
//            And it will also end the transaction.
//
//                    And finally, down here in the finally block,
//
//                    we're actually setting the set order commit method
//
//            or calling it with the value of true
//
//            to return to the default auto commit behavior.
//
//                    Now because using transactions involves getting database locks,
//
//                    it's best practice to only turn off auto commit
//
//                for the duration of a transaction
//
//                and to turn it back on again immediately afterwards.
//
//                        Once it's turned back on unless we turn it off again,
//
//                any insert updates or deletes will be auto committed as soon as they've completed.
//
//                Now resetting the default behavior in the finally block ensures
//
//                that it will be done whether the transaction succeeds but also if it fails.
//

            int affectedRows = insertIntoSongs.executeUpdate();

            if (affectedRows == 1) {
                conn.commit();
//                    throw  new SQLException("Couldnt insert albums ");
            } else {
                throw new SQLException(" the song insert failed")
            }
//                ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();
//                if(generatedKeys.next()){ // we dont need this code after above statements
//                    return generatedKeys.getInt(1); 
//                }else{
//                    throw new SQLException("couldnt get _id for the albums"); // since we are inserting a single row we expect only one record to be returned if not then something wrong sql exception
//                }
//            }
        } catch (SQLException e) {
            System.out.println("insert song exception " + e.getMessage());
            try {
                System.out.println(" performing rollback");
                conn.rollback();

            } catch (SQLException e2) {
                System.out.println(" bad things" + e.getMessage());
            }
        } finally {
            try {
                System.out.println("resetting default commit behavior");
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("couldnt reset auto commit " + e.getMessage());
            }
        }

    }
    // we can query a view just as we query a table
    // SELECT name, album, track FROM artist_list WHERE title ="title"
// when we are closing a prepared statement whichever result set is active will also be closed
}// we cant use placeholder for things like table and column names and thats because in order to precompile the statements the database needs to know which table were querying and which column we want
// we can use prepared statements with insert update and delete
