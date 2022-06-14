package com.example.test;

import Model.Artist;
import Model.DataSource;
import Model.SongArtist;

import java.util.List;
import java.util.Scanner;

public class Main { // added jar files

    public static void main(String[] args) {
        // test by creating instances
        DataSource datasource = new DataSource();
        if (!datasource.open()) {
            System.out.println("can't open datasource");
            return;
        }
        List<Artist> artists = datasource.queryArtists(DataSource.ORDER_BY_ASC); // aftet order sort()
        if (artists == null) {
            System.out.println("no artists");
            return;
        }
        for (Artist artist : artists) {
            System.out.println("ID =" + artist.getName() + ", Name = " + artist.getName());
        } // printing artist in the list or if no data we get nell print no atists
        List<String> albumsForArtist =
                datasource.queryAlbumsForArtist("Iron Maiden", DataSource.ORDER_BY_ASC);
        for (String album : albumsForArtist) {
            System.out.println(album);
        }
        List<SongArtist> songArtists = datasource.queryArtistForSong("Heartless", DataSource.ORDER_BY_ASC);
        if (songArtists == null) {
            System.out.println("couldn't find the artist for the song ");
            return;
        }
        for (SongArtist artist : songArtists) {
            System.out.println("Artist name = " + artist.getArtistName() +
                    " Album name = " + artist.getAlbumName() +
                    " Track = " + artist.getTrack());
        }
        datasource.querySongsMetadata();
        int count = datasource.getCount(DataSource.TABLE_SONGS);
        System.out.println(" number of songs is " + count);
        datasource.createViewForSongArtists();

        Scanner scanner = new Scanner(System.in);
        System.out.println(" enter a song title ");
        String title = scanner.nextLine(); // take input from user


        songArtists = datasource.querySongInfoView("Go Your Own Way");
        if (songArtists.isEmpty()) {
            System.out.println("Couldnt find the artist for the song ");
            return;
        }
        for (SongArtist artist : songArtists) {
            System.out.println("FROM VIEW - Artist name = " + artist.getArtistName() +
                    " Album name = " + artist.getAlbumName() +
                    " TRACK number = " + artist.getTrack());
        }
        datasource.insertSongs("Touch of Grey","Grateful Dead","In The Dark",1);
        datasource.close();
    }
} // when working with real application we don't usually have to acoomodate every possible query that can be made against every table what we usually do is look at the UI for the application and figure out queries
// sqlite doesnt have support for querying the data base schema
//now when we use a statement object to prerform queries the statement actually compiled every time we perform a new query
// when we built a sql statement we blindly concatenating whatever the users typed  so in some string eneters the code built the following sql statement which in case 1=1 so this is sql injection attack because user has injected sql into the statement that we did not intend to run
// since we use jdbc we have protection against hacking when when using sqlitge jdbc driver the execute and execute query methods wont rum more than single sql statement so in other words user cant tack on something like ; drop table songs but if an application written in lan like php was running sql directly based on user input rather than going through api then hacking possible
// so instead of buliding up a query string and using the statement class to execute them we should really be using the prepared statement class now prepared statemtns can protect against sql injection attack beacuse when we use them we dont concatenate user input into the sql statement